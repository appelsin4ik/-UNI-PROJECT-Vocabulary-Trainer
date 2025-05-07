package project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CardViewScreen {
    // Screen dimensions as global constants
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int CARD_WIDTH = 500;
    private static final int CARD_HEIGHT = 300;

    private Deck deck;
    private Stage stage;
    private Runnable onBack;
    private int currentCardIndex = 0;
    private boolean showTranslation = false;
    private Label cardContentLabel;

    public CardViewScreen(Deck deck, Stage stage, Runnable onBack) {
        this.deck = deck;
        this.stage = stage;
        this.onBack = onBack;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Back button (top left)
        Button backButton = new Button("← Back to Decks");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(e -> onBack.run());
        root.setTop(backButton);

        // Center area with card and navigation
        HBox centerBox = new HBox(20);
        centerBox.setAlignment(Pos.CENTER);

        // Previous button (left of card)
        Button prevButton = new Button("←");
        prevButton.setStyle("-fx-font-size: 20px; -fx-min-width: 60px; -fx-min-height: 60px;");
        prevButton.setOnAction(e -> navigateCard(-1));

        // Card display
        StackPane cardBox = new StackPane();
        cardBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-border-radius: 10; -fx-border-color: #ddd; -fx-border-width: 2px; " +
                "-fx-padding: 30px;");
        cardBox.setMinSize(CARD_WIDTH, CARD_HEIGHT);
        cardBox.setMaxSize(CARD_WIDTH, CARD_HEIGHT);

        cardContentLabel = new Label();
        cardContentLabel.setStyle("-fx-font-size: 28px; -fx-text-alignment: center;");
        cardContentLabel.setAlignment(Pos.CENTER);
        cardBox.getChildren().add(cardContentLabel);
        updateCardDisplay();

        // Next button (right of card)
        Button nextButton = new Button("→");
        nextButton.setStyle("-fx-font-size: 20px; -fx-min-width: 60px; -fx-min-height: 60px;");
        nextButton.setOnAction(e -> navigateCard(1));

        centerBox.getChildren().addAll(prevButton, cardBox, nextButton);
        root.setCenter(centerBox);

        // Bottom area with toggle and difficulty labels
        VBox bottomBox = new VBox(20);
        bottomBox.setAlignment(Pos.CENTER);

        // Toggle button
        Button toggleButton = new Button("Toggle Translation");
        toggleButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px;");
        toggleButton.setOnAction(e -> {
            showTranslation = !showTranslation;
            updateCardDisplay();
        });

        // Difficulty labels
        HBox difficultyBox = new HBox(20);
        difficultyBox.setAlignment(Pos.CENTER);

        Label leichtLabel = createDifficultyLabel("Leicht", "#4CAF50");
        Label mittelLabel = createDifficultyLabel("Mittel", "#FFC107");
        Label schwerLabel = createDifficultyLabel("Schwer", "#F44336");

        difficultyBox.getChildren().addAll(leichtLabel, mittelLabel, schwerLabel);

        bottomBox.getChildren().addAll(toggleButton, difficultyBox);
        root.setBottom(bottomBox);

        stage.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
    }

    private void navigateCard(int direction) {
        showTranslation = false;
        currentCardIndex += direction;
        if (currentCardIndex < 0) currentCardIndex = 0;
        if (currentCardIndex >= deck.getCards().size()) currentCardIndex = deck.getCards().size() - 1;
        updateCardDisplay();
    }

    private Label createDifficultyLabel(String text, String color) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; " +
                "-fx-text-fill: white; -fx-padding: 5px 15px;");
        label.setBackground(new Background(new BackgroundFill(
                Color.web(color), new CornerRadii(20), Insets.EMPTY)));
        return label;
    }

    private void updateCardDisplay() {
        if (deck.getCards().isEmpty()) return;

        Card currentCard = deck.getCards().get(currentCardIndex);
        if (showTranslation) {
            cardContentLabel.setText(currentCard.vocabulary + "\n────────────\n" + currentCard.translation);
        } else {
            cardContentLabel.setText(currentCard.vocabulary + "\n────────────\n                ");
        }
    }
}