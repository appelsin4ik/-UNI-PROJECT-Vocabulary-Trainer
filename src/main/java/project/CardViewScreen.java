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
import javafx.scene.shape.Rectangle;

/**
 * View für Karten eines Decks
 */
public class CardViewScreen extends BorderPane {
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int CARD_WIDTH = 500;
    private static final int CARD_HEIGHT = 300;

    private final Deck deck;
    private int currentCardIndex = 0;
    private boolean showTranslation = false;

    private Button leichtButton;
    private Button mittelButton;
    private Button schwerButton;

    private Label cardTermLabel;
    private Label cardTranslationLabel;

    private final WeightingAlgorithm weighting;

    /**
     * View Constructor
     * @param deck Das Deck, das abgefragt werden soll
     * @param onBack Runnable, mit dem zurück zum Deck Screen navigiert wird
     */
    public CardViewScreen(Deck deck, Runnable onBack) {
        this.weighting = new WeightingAlgorithm(deck.getCards());
        this.deck = deck;
        this.setPadding(new Insets(20));
        this.setStyle(StyleConstants.BACKGROUND_DEFAULT);

        setupTopBar(onBack);
        setupCenterCardView();
        setupBottomControls();
        updateDifficultyButtons();
    }

    public void show() {
        Main.getStage().setScene(new Scene(this, SCREEN_WIDTH, SCREEN_HEIGHT));
    }

    private void setupTopBar(Runnable onBack) {
        FontAwesomeIconView backIcon = new FontAwesomeIconView(FontAwesomeIcon.ARROW_LEFT);

        backIcon.setSize("16px");


        Button backButton = new Button("Zurück", backIcon);

        backButton.setStyle(StyleConstants.BUTTON_BACK);
        backButton.setOnMouseEntered(e -> backButton.setStyle(StyleConstants.BUTTON_BACK_HOVER));
        backButton.setOnMouseExited(e -> backButton.setStyle(StyleConstants.BUTTON_BACK));
        backButton.setOnAction(e -> onBack.run());


        HBox topBar = new HBox(backButton);

        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setPadding(new Insets(10));


        this.setTop(topBar);
    }

    private void setupCenterCardView() {
        HBox centerBox = new HBox(20);

        centerBox.setAlignment(Pos.CENTER);


        Button prevButton = createIconButton(FontAwesomeIcon.CHEVRON_LEFT, e -> navigateCard());
        Button nextButton = createIconButton(FontAwesomeIcon.CHEVRON_RIGHT, e -> navigateCard());


        StackPane cardBox = new StackPane();

        cardBox.setStyle(StyleConstants.CARD_BOX_VIEW);
        cardBox.setMinSize(CARD_WIDTH, CARD_HEIGHT);
        cardBox.setMaxSize(CARD_WIDTH, CARD_HEIGHT);


        cardTermLabel = new Label();

        cardTermLabel.setStyle(StyleConstants.LABEL_TERM);


        cardTranslationLabel = new Label();

        cardTranslationLabel.setStyle(StyleConstants.LABEL_TRANSLATION);


        Rectangle sep = new Rectangle(CARD_WIDTH * 0.6, 2);

        sep.setArcHeight(6);
        sep.setArcWidth(6);
        sep.setOpacity(0.8);
        sep.setFill(Color.web("#bbbbbb"));


        VBox cardContentBox = new VBox(10, cardTermLabel, sep, cardTranslationLabel);

        cardContentBox.setAlignment(Pos.CENTER);

        cardBox.getChildren().add(cardContentBox);

        centerBox.getChildren().addAll(prevButton, cardBox, nextButton);

        this.setCenter(centerBox);

        updateCardDisplay();
    }

    private void setupBottomControls() {
        VBox bottomBox = new VBox(20);

        bottomBox.setAlignment(Pos.CENTER);


        Button toggleButton = createIconButton(FontAwesomeIcon.EYE, e -> {
            showTranslation = !showTranslation;
            updateCardDisplay();
        });


        HBox difficultyBox = new HBox(20);

        difficultyBox.setAlignment(Pos.CENTER);

        leichtButton = createDifficultyButton("Leicht", "#4CAF50", 1);
        mittelButton = createDifficultyButton("Mittel", "#FFC107", 2);
        schwerButton = createDifficultyButton("Schwer", "#F44336", 3);

        difficultyBox.getChildren().addAll(leichtButton, mittelButton, schwerButton);

        bottomBox.getChildren().addAll(toggleButton, difficultyBox);

        this.setBottom(bottomBox);
    }

    private Button createIconButton(FontAwesomeIcon icon, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        FontAwesomeIconView iconView = new FontAwesomeIconView(icon);

        iconView.setSize("20px");

        // Manuelle Korrektur: leicht nach links und nach unten verschieben
        if (icon == FontAwesomeIcon.CHEVRON_LEFT) {
            iconView.setTranslateX(-2); 
            iconView.setTranslateY(1); 
        } else if (icon == FontAwesomeIcon.CHEVRON_RIGHT) {
            iconView.setTranslateX(2); 
            iconView.setTranslateY(1); 
        }

        StackPane iconContainer = new StackPane(iconView);

        iconContainer.setPrefSize(30, 30);
        iconContainer.setAlignment(Pos.CENTER);

        Button button = new Button();

        button.setGraphic(iconContainer);
        button.setStyle(StyleConstants.BUTTON_CIRCLE_ICON);
        button.setPrefSize(60, 60);
        button.setMinSize(60, 60);
        button.setMaxSize(60, 60);

        button.setOnMouseEntered(e -> {
            button.setScaleX(1.05);
            button.setScaleY(1.05);
            button.setStyle(StyleConstants.BUTTON_CIRCLE_ICON_HOVER);
        });

        button.setOnMouseExited(e -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
            button.setStyle(StyleConstants.BUTTON_CIRCLE_ICON);
        });
        
        button.setOnAction(handler);


        return button;
    }

    private Button createDifficultyButton(String text, String color, int weightValue) {
        Button button = new Button(text);

        button.setStyle(getBaseButtonStyle(text, color));
        button.setOnAction(e -> setCardWeight(weightValue));
        button.setOnMouseEntered(e -> button.setOpacity(0.7));
        button.setOnMouseExited(e -> button.setOpacity(1.0));

        return button;
    }

    /**
     * Erstellt den Basis-Stil für einen Schwierigkeitsgrad-Button
     * @param text Der Buttontext ("Leicht", "Mittel" oder "Schwer")
     * @param color Die Hintergrundfarbe des Buttons als Hex-Code
     * @return Der komplette CSS-Stil als String
     */
    private String getBaseButtonStyle(String text, String color) {
        String style = StyleConstants.BUTTON_DIFFICULTY + "-fx-background-color: " + color + ";";

        if ((text.equals("Leicht") && deck.getCards().get(currentCardIndex).weight == 1) ||
            (text.equals("Mittel") && deck.getCards().get(currentCardIndex).weight == 2) ||
            (text.equals("Schwer") && deck.getCards().get(currentCardIndex).weight == 3)) {

            // Selektierter Button: etwas dunkler + sanfter Schatten für Feedback
            style += """
                -fx-border-color: #33333320; -fx-border-width: 2px; -fx-border-radius: 20px;
                -fx-background-color: derive(""" 
                + color + ", -15%);" 
                +"-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 4, 0, 0, 2);";

        }
        return style;
    }

    /**
     * Aktualisiert den visuellen Zustand der Schwierigkeitsgrad-Buttons
     * basierend auf dem aktuellen Gewicht der Karte
     */
    private void updateDifficultyButtons() {
        leichtButton.setStyle(getBaseButtonStyle("Leicht", "#4CAF50"));
        mittelButton.setStyle(getBaseButtonStyle("Mittel", "#FFC107"));
        schwerButton.setStyle(getBaseButtonStyle("Schwer", "#F44336"));
    }

    /**
     * Setzt das Gewicht (Schwierigkeitsgrad) der aktuellen Karte
     * und sortiert das Deck entsprechend neu
     * @param newWeight Das neue Gewicht (1=leicht, 2=mittel, 3=schwer)
     */
    private void setCardWeight(int newWeight) {
        if (deck.getCards().isEmpty()) return;

        Card currentCard = deck.getCards().get(currentCardIndex);
        currentCard.weight = newWeight;

        deck.getCards().sort(Card::compareTo);

        currentCardIndex = deck.getCards().indexOf(currentCard);

        updateDifficultyButtons();
        updateCardDisplay();

        DeckDisplayScreen.saveDeckToFile(this.deck);

        printDeckDebugInfo();
    }

    /**
     * Navigiert zur nächsten oder vorherigen Karte mit dem WeightingAlgorithmus
     */

    private void navigateCard() {

        showTranslation = false;
        Card next = weighting.getNextCard();

        if (next == null) return;

        currentCardIndex = deck.getCards().indexOf(next);

        updateCardDisplay();
        updateDifficultyButtons();
        printDeckDebugInfo();
    }

    /**
     * Aktualisiert die Anzeige der aktuellen Karte.
     * Zeigt entweder nur das Vokabular oder auch die Übersetzung an,
     * abhängig vom showTranslation-Status
     */
    private void updateCardDisplay() {
        if (deck.getCards().isEmpty()) return;

        Card currentCard = deck.getCards().get(currentCardIndex);

        cardTermLabel.setText(currentCard.vocabulary);
        cardTranslationLabel.setText(showTranslation ? currentCard.translation : "");
    }

    /**
     * Gibt Debug-Informationen über den aktuellen Zustand des Decks
     * in der Konsole aus
     */
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
            System.out.printf("%s%-17s %6d%n", currentIndicator, card.vocabulary, card.weight);
        }
        System.out.println("=====================\n");
    }
}