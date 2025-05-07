package project;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * View für Karten eines Decks
 */
public class CardViewScreen extends VBox {
    private Deck deck;
    private Runnable onBack;

    /**
     * View Constructor
     */
    public CardViewScreen(Deck deck, Runnable onBack) {
        super(10);

        this.deck = deck;
        this.onBack = onBack;
    }

    /**
     * View anzeigen
     */
    public void show() {
        this.setPadding(new Insets(15));

        Button backButton = new Button("← Back to Decks");
        backButton.setOnAction(e -> onBack.run());

        Label title = new Label("Cards in " + deck.getName());
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        VBox cardList = new VBox(5);
        for (Card card : deck.getCards()) {
            Label cardLabel = new Label(card.vocabulary + " - " + card.translation);
            cardList.getChildren().add(cardLabel);
        }

        this.getChildren().addAll(backButton, title, cardList);
        Main.getStage().setScene(new Scene(this, 800, 600));
    }
}