import org.beryx.jlink.JlinkTask
import org.beryx.jlink.PrepareModulesDirTask
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform
import java.nio.file.Paths

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.7.0")
    }
}

plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "3.1.1"
    //id("org.graalvm.buildtools.native") version "0.10.6"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("de.jensd:fontawesomefx-fontawesome:4.7.0-9.1.2")
}

application {
    mainClass = "project.Main"
}

group = "project"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

tasks.test {
    useJUnitPlatform()
}

// javadoc generierung Task
tasks.javadoc {
    source = sourceSets["main"].allJava.srcDirs()
    options {
        title = "Our Java Project"
        version = "1.0"
    }
}

jlink {
    imageName = "Projekt"

    addExtraDependencies("javafx")

    // Point to the deobfuscated JAR instead of the regular build output
//    mergedModule {
//        enabled = false
//        additive = true
//        requires("javafx.controls")
//
//        //jarFiles.from(file("${layout.buildDirectory}/deobfuscated/app-deobf.jar"))
//    }

    addOptions("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    launcher {
        name = "projekt"
        noConsole = true
    }

    tasks.named("jlink").configure {
        dependsOn("clean")
    }

    tasks.named<PrepareModulesDirTask>("prepareModulesDir").configure {
        dependsOn("proguard")

        doLast {
            val proguardTask = tasks.named<proguard.gradle.ProGuardTask>("proguard")
            val jarTask = tasks.named<Jar>("jar")
            //proguardTask.get().outJarFiles

            val obfuscatedJar = proguardTask.get().outputs.files.files.find { it.extension == "jar" }
                ?: throw GradleException("No JAR file found in Task outputs.")

            val normalJar = jarTask.get().outputs.files.files.find { it.extension == "jar" }
                ?: throw GradleException("No JAR file found in Task outputs.")

            copy {
                from(obfuscatedJar)
                into(jlinkJarsDir)
                rename { normalJar.name }
            }
        }
    }
}

// jar build Task benötigt für ProGuardTask
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
}

// https://github.com/Guardsquare/proguard/blob/ef6a8352bdbbf233e21a9dee5e5f9ac394cb7fc3/examples/gradle-kotlin-dsl/build.gradle.kts
tasks.register<proguard.gradle.ProGuardTask>("proguard") {
    verbose()
    ignorewarnings()

    // Use the jar task output as a input jar. This will automatically add the necessary task dependency.
    injars(tasks.named("jar"))

    outjars("build/proguard-obfuscated.jar")

    val javaHome = System.getProperty("java.home")
    // Automatically handle the Java version of this build.
    if (System.getProperty("java.version").startsWith("1.")) {
        // Before Java 9, the runtime classes were packaged in a single jar file.
        libraryjars("$javaHome/lib/rt.jar")
    } else {
        // As of Java 9, the runtime classes are packaged in modular jmod files.
        libraryjars(
            // filters must be specified first, as a map
            mapOf("jarfilter" to "!**.jar",
                "filter"    to "!module-info.class"),
            "$javaHome/jmods/java.base.jmod"
        )
    }

    //allowaccessmodification()
    repackageclasses("")

    printmapping("build/proguard-mapping.txt")

    adaptresourcefilecontents("**.fxml,**.properties,META-INF/MANIFEST.MF")

    keeppackagenames()
    keep("class module-info")
    keep("""
        class ${application.mainClass.get()} {
            public static void main(java.lang.String[]);
            public void start(javafx.stage.Stage);
        }
    """)
//    keep("""
//        class javafx.** { *; }
//        class ** extends javafx.** {  }
//    """)
//    keepclassmembers("""
//        class * {
//            @java.lang.Overwrite *;
//            @java.lang.Overwrite public *** *(**);
//        }
//    """)
    keepattributes("Module*")
    keepattributes("Override")
    keepattributes("""
        Signature,Exceptions,*Annotation*,
        InnerClasses,PermittedSubclasses,EnclosingMethod,
        Deprecated,SourceFile,LineNumberTable,
    """)

    // output Dateien an andere Tasks übergeben
    outputs.file(outJarFiles)
}

// obfuscated and packaged run Task
tasks.register<Exec>("run-production") {
    val jlinkTask = tasks.named<JlinkTask>("jlink")
    dependsOn(jlinkTask)

    doFirst {
        //jlinkTask.get().outputs.files.forEach { logger.lifecycle(" - ${it.absolutePath}") }

        var cwd = Paths.get(jlinkTask.get().outputs.files.first().toString(), "bin")
        logger.lifecycle("cwd: $cwd")
        var launcher = jlinkTask.get().launcherData.name

        workingDir = cwd.toFile()
        val os = DefaultNativePlatform.getCurrentOperatingSystem()
        if (os.isWindows) {
            commandLine = listOf("cmd.exe", "/d", "/c", launcher.toString())
        } else {
            // noch nicht getestet!
            commandLine = listOf(launcher.toString())
        }
    }
}