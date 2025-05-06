package project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main Application Class
 */
public class Main extends Application {
    private DeckManager deckManager = new DeckManager();

    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        launch();
    }

    @Override
    public void start(Stage stage) {
//        String javaVersion = System.getProperty("java.version");
//        String javafxVersion = System.getProperty("javafx.version");
//        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
//        Scene scene = new Scene(new StackPane(l), 640, 480);
//        stage.setScene(scene);
//        stage.show();
        DeckDisplayScreen deckDisplay = new DeckDisplayScreen(deckManager);
        deckDisplay.start(stage);
    }
}