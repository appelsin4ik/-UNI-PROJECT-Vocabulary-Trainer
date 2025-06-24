/**
 * Hauptmodule der Anwendung
 */
module project.main {
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires java.prefs;
    requires net.harawata.appdirs;
    requires com.google.gson;
    requires com.sun.jna.platform;
    requires java.desktop;
    

    

    opens project to javafx.graphics,com.google.gson;
    exports project;
}