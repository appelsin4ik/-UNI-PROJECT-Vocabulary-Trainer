package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class DeckDisplayScreen extends Application {

    private DeckManager deckManager;
    private Stage stage;

    public DeckDisplayScreen () {
    }

    public DeckDisplayScreen(DeckManager deckManager, Stage stage) {
        this.deckManager = deckManager;
        this.stage = stage;
    }

    @Override
    public void start(Stage stage) {
        // Main layout with sidebar and content
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Add sidebar
        root.setLeft(SidebarManager.getInstance().showSidebar());
        SidebarManager.getInstance().getSettingsButton().setOnAction(e -> {
            SidebarManager.getInstance().updateButton(SidebarManager.getInstance().getSettingsButton());
            showSettingsScreen(stage);
        });

        // Create main content area
        VBox content = createMainContent();
        root.setCenter(content);

        // Set up the scene
        stage.setScene(new Scene(root, 1000, 700)); // Increased width for sidebar
        stage.setTitle("Vocabulary Learner");
        stage.show();
    }

    private void showSettingsScreen(Stage stage) {
        SettingsScreen settingView = new SettingsScreen(stage, this::show,this.deckManager,this);
        settingView.show();
    }

    private VBox createMainContent() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        // Title
        Label title = new Label("Deine Decks");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Deck container
        FlowPane deckContainer = new FlowPane();
        deckContainer.setHgap(15);
        deckContainer.setVgap(15);
        deckContainer.setPadding(new Insets(10));

        for (Deck deck : deckManager.getDecks()) {
            VBox deckBox = createDeckBox(deck);
            deckContainer.getChildren().add(deckBox);
        }

        content.getChildren().addAll(title, deckContainer);
        return content;
    }

    private VBox createDeckBox(Deck deck) {
        VBox deckBox = new VBox(10);
        deckBox.setAlignment(Pos.CENTER);
        deckBox.setPadding(new Insets(15));
        deckBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");
        deckBox.setMinWidth(235);

        Label deckName = new Label(deck.getName());
        deckName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label cardCount = new Label(deck.getCards().size() + " cards");
        cardCount.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        Button startButton = new Button("Start");
        startButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; " +
                "-fx-text-fill: white; -fx-padding: 8px 16px;");
        startButton.setOnAction(e -> showCardScreen(deck));

        deckBox.getChildren().addAll(deckName, cardCount, startButton);
        return deckBox;
    }

    private void showCardScreen(Deck deck) {
        CardViewScreen cardView = new CardViewScreen(deck, stage, this::show);
        cardView.show();
    }

    public void show() {
        start(stage);
    }
}