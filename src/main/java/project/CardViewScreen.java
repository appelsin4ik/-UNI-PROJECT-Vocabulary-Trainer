package project;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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

        FontAwesomeIconView backIcon = new FontAwesomeIconView(FontAwesomeIcon.ARROW_LEFT);
        backIcon.setSize("16px");

        // Back button (top left)
        Button backButton = new Button("Back to Decks", backIcon);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(e -> onBack.run());
        root.setTop(backButton);

        // Center area with card and navigation
        HBox centerBox = new HBox(20);
        centerBox.setAlignment(Pos.CENTER);

        FontAwesomeIconView prevIcon = new FontAwesomeIconView(FontAwesomeIcon.CHEVRON_LEFT);
        prevIcon.setSize("24px");
        Button prevButton = new Button("", prevIcon);
        prevButton.setStyle("-fx-min-width: 60px; -fx-min-height: 60px;");
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

        // Next button with icon
        FontAwesomeIconView nextIcon = new FontAwesomeIconView(FontAwesomeIcon.CHEVRON_RIGHT);
        nextIcon.setSize("24px");
        Button nextButton = new Button("", nextIcon);
        nextButton.setStyle("-fx-min-width: 60px; -fx-min-height: 60px;");
        nextButton.setOnAction(e -> navigateCard(1));

        centerBox.getChildren().addAll(prevButton, cardBox, nextButton);
        root.setCenter(centerBox);

        // Bottom area with toggle and difficulty buttons
        VBox bottomBox = new VBox(20);
        bottomBox.setAlignment(Pos.CENTER);

        FontAwesomeIconView eyeIcon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
        eyeIcon.setSize("50px");
        // Toggle button
        Button toggleButton = new Button("", eyeIcon);
        toggleButton.setStyle("-fx-font-size: 24px; -fx-padding: 12px 16px;");
        toggleButton.setOnAction(e -> {
            showTranslation = !showTranslation;
            updateCardDisplay();
        });

        // Difficulty buttons
        HBox difficultyBox = new HBox(20);
        difficultyBox.setAlignment(Pos.CENTER);

        Button leichtButton = createDifficultyButton("Leicht", "#4CAF50");
        leichtButton.setOnAction(e -> adjustCardWeight(-1));

        Button mittelButton = createDifficultyButton("Mittel", "#FFC107");
        mittelButton.setOnAction(e -> adjustCardWeight(1));

        Button schwerButton = createDifficultyButton("Schwer", "#F44336");
        schwerButton.setOnAction(e -> adjustCardWeight(2));

        difficultyBox.getChildren().addAll(leichtButton, mittelButton, schwerButton);
        bottomBox.getChildren().addAll(toggleButton, difficultyBox);
        root.setBottom(bottomBox);

        stage.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
    }

    private Button createDifficultyButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; " +
                "-fx-text-fill: white; -fx-padding: 8px 16px; " +
                "-fx-background-radius: 20px;");
        button.setBackground(new Background(new BackgroundFill(
                Color.web(color), new CornerRadii(20), Insets.EMPTY)));

        // Hover effects
        button.setOnMouseEntered(e -> button.setOpacity(0.8));
        button.setOnMouseExited(e -> button.setOpacity(1.0));

        return button;
    }

    private void adjustCardWeight(int change) {
        if (deck.getCards().isEmpty()) return;

        Card currentCard = deck.getCards().get(currentCardIndex);
        currentCard.weight += change;

        // Ensure weight doesn't go below 0
        if (currentCard.weight < 0) {
            currentCard.weight = 0;
        }

        // You could add visual feedback here
        System.out.println("Card weight updated to: " + currentCard.weight);
    }

    private void navigateCard(int direction) {
        showTranslation = false;
        currentCardIndex += direction;
        if (currentCardIndex < 0) currentCardIndex = 0;
        if (currentCardIndex >= deck.getCards().size()) currentCardIndex = deck.getCards().size() - 1;
        updateCardDisplay();
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