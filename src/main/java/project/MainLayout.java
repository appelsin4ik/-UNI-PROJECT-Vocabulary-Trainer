
package project;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import project.control.SidebarManager;
import project.util.style.StyleConstants;

/**
 * Hauptlayout der Anwendung mit fester Sidebar und dynamischem Mittelteil.
 */
public class MainLayout extends BorderPane {

    private static MainLayout instance;
    
    private Scene scene;

    /**
     * Singleton-Zugriff auf das MainLayout
     */
    public static MainLayout getInstance() {
        if (instance == null) instance = new MainLayout();
        return instance;
    }

    private MainLayout() {
        this.setStyle(StyleConstants.BACKGROUND_DEFAULT);
        this.setLeft(SidebarManager.getInstance().getSidebar());
    }

    /**
     * Zeigt den gewünschten Screen im Hauptbereich der App.
     * @param screen Das JavaFX-Node-Element, das als Content angezeigt werden soll
     */
    public void showContent(javafx.scene.Node screen) {
        this.setCenter(screen);
    }

    public void show() {
        if (scene == null) {
            scene = new Scene(this, 1000, 700);
        }
        Main.getStage().setScene(scene);
        Main.getStage().show();
    }
}
