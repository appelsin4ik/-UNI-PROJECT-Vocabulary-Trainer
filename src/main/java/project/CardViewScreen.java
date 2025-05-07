package project;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CardViewScreen {
    private Deck deck;
    private Stage stage;
    private Runnable onBack;

    public CardViewScreen(Deck deck, Stage stage, Runnable onBack) {
        this.deck = deck;
        this.stage = stage;
        this.onBack = onBack;
    }

    public void show() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Button backButton = new Button("← Back to Decks");
        backButton.setOnAction(e -> onBack.run());

        Label title = new Label("Cards in " + deck.getName());
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        VBox cardList = new VBox(5);
        for (Card card : deck.getCards()) {
            Label cardLabel = new Label(card.vocabulary + " - " + card.translation);
            cardList.getChildren().add(cardLabel);
        }

        layout.getChildren().addAll(backButton, title, cardList);
        stage.setScene(new Scene(layout, 800, 600));
    }
}