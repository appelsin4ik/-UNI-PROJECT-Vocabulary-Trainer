package project;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;

/**
 * View für Karten eines Decks
 */
public class CardViewScreen extends BorderPane {
    // Screen dimensions as global constants
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int CARD_WIDTH = 500;
    private static final int CARD_HEIGHT = 300;

    private Deck deck;
    private Runnable onBack;
    private int currentCardIndex = 0;
    private boolean showTranslation = false;
    private Label cardContentLabel;

    private Button leichtButton;
    private Button mittelButton;
    private Button schwerButton;

    /**
     * View Constructor
     * @param deck Das Deck, das abgefragt werden soll
     * @param onBack Runnable, mit dem zurück zum Deck Screen navigiert wird
     */
    public CardViewScreen(Deck deck, Runnable onBack) {
        super();

        this.deck = deck;
        this.onBack = onBack;

        // UI im Konstruktor einmalig für diese Klasse konstruieren
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #f5f5f5;");

        FontAwesomeIconView backIcon = new FontAwesomeIconView(FontAwesomeIcon.ARROW_LEFT);
        backIcon.setSize("16px");

        // Back button (top left)
        Button backButton = new Button("Back to Decks", backIcon);
        backButton.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-font-size: 14px;");
        backButton.setOnAction(e -> onBack.run());
        this.setTop(backButton);

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
        this.setCenter(centerBox);

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

        leichtButton = createDifficultyButton("Leicht", "#4CAF50");
        mittelButton = createDifficultyButton("Mittel", "#FFC107");
        schwerButton = createDifficultyButton("Schwer", "#F44336");

        leichtButton.setOnAction(e -> setCardWeight(1));
        mittelButton.setOnAction(e -> setCardWeight(2));
        schwerButton.setOnAction(e -> setCardWeight(3));

        difficultyBox.getChildren().addAll(leichtButton, mittelButton, schwerButton);
        bottomBox.getChildren().addAll(toggleButton, difficultyBox);
        this.setBottom(bottomBox);

        updateDifficultyButtons(); // Initialize button states
    }

    /**
     * View anzeigen
     */
    public void show() {
        Main.getStage().setScene(new Scene(this, SCREEN_WIDTH, SCREEN_HEIGHT));
    }

    /**
     * Erstellt einen der leicht, mittel oder schwer buttons, mit denen die Schwierigkeit der Karten angepasst
     * werden kann
     */
    private Button createDifficultyButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 8px 16px; " +
                        "-fx-background-radius: 20px; " +
                        "-fx-background-color: " + color + ";"
        );

        // Hover effects
        button.setOnMouseEntered(
            e -> button.setOpacity(0.7)
        );
        button.setOnMouseExited(e -> button.setOpacity(1.0));

        return button;
    }

    private String getBaseButtonStyle(String text, String color) {
        String style = "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 8px 16px; " +
                "-fx-background-radius: 20px; " +
                "-fx-background-color: " + color + ";";

        // Add border if this is the selected button
        if ((text.equals("Leicht") && deck.getCards().get(currentCardIndex).weight == 1) ||
                (text.equals("Mittel") && deck.getCards().get(currentCardIndex).weight == 2) ||
                (text.equals("Schwer") && deck.getCards().get(currentCardIndex).weight == 3)) {
            style += "-fx-border-color: white; -fx-border-width: 2px; -fx-border-radius: 20px;";
        }

        return style;
    }

    private void updateDifficultyButtons() {
        int weight = deck.getCards().get(currentCardIndex).weight;

        leichtButton.setStyle(getBaseButtonStyle("Leicht", "#4CAF50"));
        mittelButton.setStyle(getBaseButtonStyle("Mittel", "#FFC107"));
        schwerButton.setStyle(getBaseButtonStyle("Schwer", "#F44336"));
    }

    private void setCardWeight(int newWeight) {
        if (deck.getCards().isEmpty()) return;

        Card currentCard = deck.getCards().get(currentCardIndex);
        currentCard.weight = newWeight; // Directly set the weight

        // Sort the deck by weight (using Card's compareTo method)
        deck.getCards().sort(Card::compareTo);

        // Find the new index of our current card after sorting
        currentCardIndex = deck.getCards().indexOf(currentCard);

        System.out.println("Card weight set to: " + currentCard.weight);
        updateDifficultyButtons();
        updateCardDisplay();
        printDeckDebugInfo();
        DeckDisplayScreen.saveDeckToFile(this.deck);
    }

    private void navigateCard(int direction) {
        showTranslation = false;

        if (deck.getCards().isEmpty()) return;

        // Find next card with weight > 0 when reaching list boundaries
        if (direction > 0 && currentCardIndex >= deck.getCards().size() - 1) {
            // Reached end - find first card with weight > 0
            currentCardIndex = findNextWeightedCard(0);
        }
        else if (direction < 0 && currentCardIndex <= 0) {
            // Reached beginning - find last card with weight > 0
            currentCardIndex = findPreviousWeightedCard(deck.getCards().size() - 1);
        }
        else {
            // Normal navigation
            currentCardIndex += direction;
        }

        updateCardDisplay();
        updateDifficultyButtons();

        printDeckDebugInfo();
    }

    private int findNextWeightedCard(int startIndex) {
        List<Card> cards = deck.getCards();
        for (int i = startIndex; i < cards.size(); i++) {
            if (cards.get(i).weight > 0) {
                return i;
            }
        }
        return 0; // Fallback to first card if none found
    }

    private int findPreviousWeightedCard(int startIndex) {
        List<Card> cards = deck.getCards();
        for (int i = startIndex; i >= 0; i--) {
            if (cards.get(i).weight > 0) {
                return i;
            }
        }
        return cards.size() - 1; // Fallback to last card if none found
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

    public void printDeckDebugInfo() {
        if (deck == null || deck.getCards().isEmpty()) {
            System.out.println("Deck is empty or not initialized");
            return;
        }

        System.out.println("\n=== Deck Debug Info ===");
        System.out.printf("%-20s %s%n", "Card Name", "Weight");
        System.out.println("--------------------- ------");

        for (int i = 0; i < deck.getCards().size(); i++) {
            Card card = deck.getCards().get(i);
            String currentIndicator = (i == currentCardIndex) ? "-> " : "   ";
            System.out.printf("%s%-17s %6d%n",
                    currentIndicator,
                    card.vocabulary,
                    card.weight);
        }

        System.out.println("=====================\n");
    }
}