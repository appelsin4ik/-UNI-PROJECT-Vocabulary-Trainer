package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class DeckDisplayScreen extends Application {

    private DeckManager deckManager;
    private Stage stage;

    public DeckDisplayScreen(DeckManager deckManager, Stage stage) {
        this.deckManager = deckManager;
        this.stage = stage;
    }

    @Override
    public void start(Stage stage) {
        // Create the main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setBackground(new Background(new BackgroundFill(
                Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        // Add title
        Label title = new Label("Your Decks");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        //mainLayout.getChildren().add(title);

        FlowPane deckContainer = new FlowPane();
        deckContainer.setHgap(15);
        deckContainer.setVgap(15);
        deckContainer.setPadding(new Insets(10));

        // Add deck buttons to the grid
        int column = 0;
        int row = 0;
        int columns = 3; // Number of columns in the grid

        for (Deck deck : deckManager.getDecks()) {
            // Create container for each deck
            VBox deckBox = new VBox(10);
            deckBox.setAlignment(Pos.CENTER);
            deckBox.setPadding(new Insets(10));
            deckBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

            // Deck name label
            Label deckName = new Label(deck.getName());
            deckName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            // Card count label
            Label cardCount = new Label(deck.getCards().size() + " cards");
            cardCount.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

            // Start button
            Button startButton = new Button("Start");
            startButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
            startButton.setOnAction(e -> showCardScreen(deck));

            deckBox.getChildren().addAll(deckName, cardCount, startButton);
            deckContainer.getChildren().add(deckBox);
        }

        // Set up the scene
        mainLayout.getChildren().addAll(title, deckContainer);
        stage.setScene(new Scene(mainLayout, 800, 600));
        stage.setTitle("Vocabulary Learner");
        stage.show();
    }

    private void showCardScreen(Deck deck) {
        CardViewScreen cardView = new CardViewScreen(deck, stage, this::show);
        cardView.show();
    }

    public void show() {
//        VBox mainLayout = createDeckDisplay();
//        stage.setScene(new Scene(mainLayout, 800, 600));
        start(stage);
    }

}
