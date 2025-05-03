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
    //id("org.graalvm.buildtools.native") version "0.10.6"
}

group = "project"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "project.Main"
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

// jar build Task
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
}

// obfuscated jar run Task
tasks.register<JavaExec>("run-obfuscated") {
    val proguardTask = tasks.named("proguard")
    dependsOn(proguardTask)

    doFirst {
//        logger.lifecycle("ProGuard output files:")
//        proguardTask.get().outputs.files.forEach { logger.lifecycle(" - ${it.absolutePath}") }

        val outputJar = proguardTask.get().outputs.files.files.find { it.extension == "jar" }
            ?: throw GradleException("No JAR file found in ProGuard outputs.")
        mainClass.set("-jar")
        args = listOf(outputJar.absolutePath)
    }
}

// https://github.com/Guardsquare/proguard/blob/ef6a8352bdbbf233e21a9dee5e5f9ac394cb7fc3/examples/gradle-kotlin-dsl/build.gradle.kts
tasks.register<proguard.gradle.ProGuardTask>("proguard") {
    verbose()

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

    allowaccessmodification()

    repackageclasses("")

    printmapping("build/proguard-mapping.txt")

    keep("""class ${application.mainClass.get()} {
                public static void main(java.lang.String[]);
            }
    """)

    // output Dateien an andere Tasks übergeben
    outputs.file(outJarFiles)
}