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


    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        launch();
    }

    @Override
    public void start(Stage stage) {
        DeckManager deckManager = new DeckManager();
        DeckDisplayScreen deckDisplay = new DeckDisplayScreen(deckManager, stage);
        deckDisplay.start(stage);

//        stage.setTitle("Vocabulary Learner");
//        stage.show();
    }
}