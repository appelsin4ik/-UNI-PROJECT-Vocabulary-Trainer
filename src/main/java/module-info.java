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
    

    exports project;
    exports project.util.settings;

    

     opens project to javafx.graphics,com.google.gson;
     opens project.util.settings to com.google.gson;
    // opens project.util.io to com.google.gson;

}