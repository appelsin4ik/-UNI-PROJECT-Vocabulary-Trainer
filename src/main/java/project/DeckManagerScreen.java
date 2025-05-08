package project;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DeckManagerScreen {
    private Stage stage;

    public DeckManagerScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane root = new BorderPane();

        //Add Sidebar
        root.setLeft(SidebarManager.getInstance().showSidebar());


        stage.setScene(new Scene(root,1000,700));
    }
}
