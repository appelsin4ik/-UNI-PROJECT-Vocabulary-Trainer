package project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Application Class mit application Eintsiegspunkt
 */
public class Main extends Application {

    private static Stage stage;

    /**
     * main Einstiegspunkt
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return Main.stage;
    }

    /**
     * JavaFX Application beginn
     */
    @Override
    public void start(Stage stage) {
        Main.stage = stage;
        stage.setTitle("Vocabulary Learner");

        DeckManager deckManager = new DeckManager();
        DeckDisplayScreen deckDisplay = new DeckDisplayScreen(deckManager);
        deckDisplay.show();
        stage.show();
    }
}