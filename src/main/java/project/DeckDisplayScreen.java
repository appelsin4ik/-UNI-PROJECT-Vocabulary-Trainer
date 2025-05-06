package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DeckDisplayScreen extends Application {

    private DeckManager deckManager;

    public DeckDisplayScreen(DeckManager deckManager) {
        this.deckManager = deckManager;
    }

    @Override
    public void start(Stage primaryStage) {
        // Create the main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setBackground(new Background(new BackgroundFill(
                Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        // Add title
        Label title = new Label("Your Card Decks");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        mainLayout.getChildren().add(title);

        // Create a grid for the deck buttons
        GridPane deckGrid = new GridPane();
        deckGrid.setHgap(10);
        deckGrid.setVgap(10);
        deckGrid.setPadding(new Insets(10));

        // Add deck buttons to the grid
        int column = 0;
        int row = 0;
        int columns = 3; // Number of columns in the grid

        for (Deck deck : deckManager.getDecks()) {
            Button deckButton = createDeckButton(deck);
            deckGrid.add(deckButton, column, row);

            column++;
            if (column >= columns) {
                column = 0;
                row++;
            }
        }

        mainLayout.getChildren().add(deckGrid);

        // Set up the scene
        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setTitle("Card Deck Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createDeckButton(Deck deck) {
        Button button = new Button(deck.getName());
        button.setStyle("-fx-font-size: 14px;");
        button.setPrefSize(150, 100);
        button.setOnAction(e -> {
            System.out.println("Selected deck: " + deck.getName());
            // Here you would open the deck details view
        });
        return button;
    }
}
