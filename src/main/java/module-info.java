/**
 * Hauptmodule der Anwendung
 */
module project.main {
    requires javafx.controls;
    requires javafx.graphics;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires java.prefs;
    requires net.harawata.appdirs;
    requires com.google.gson;
    requires com.sun.jna.platform;

    opens project to javafx.graphics;
    exports project;
}