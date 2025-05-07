module project.main {
    requires javafx.controls;
    requires javafx.graphics;
    requires de.jensd.fx.glyphs.fontawesome;

    opens project to javafx.graphics;
    exports project;
}