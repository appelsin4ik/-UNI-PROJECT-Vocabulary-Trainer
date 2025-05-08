package project;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
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
        // Add "Add Deck" card
        VBox addDeckBox = createAddDeckBox();
        deckContainer.getChildren().add(addDeckBox);

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

    private VBox createAddDeckBox() {
        VBox addDeckBox = new VBox();
        addDeckBox.setAlignment(Pos.CENTER);
        addDeckBox.setPadding(new Insets(15));
        addDeckBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");
        addDeckBox.setMinWidth(235);
        addDeckBox.setMinHeight(150); // Match height of other deck boxes

        // Create big plus icon
        FontAwesomeIconView plusIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
        plusIcon.setSize("50px");
        plusIcon.setFill(Color.web("#7f8c8d")); // Gray color

        // Make the whole box clickable
        addDeckBox.setOnMouseClicked(e -> addNewDeck());
        addDeckBox.setOnMouseEntered(e -> {
            addDeckBox.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");
            plusIcon.setFill(Color.web("#3498db")); // Blue color on hover
        });
        addDeckBox.setOnMouseExited(e -> {
            addDeckBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");
            plusIcon.setFill(Color.web("#7f8c8d")); // Gray color
        });

        addDeckBox.getChildren().add(plusIcon);
        return addDeckBox;
    }

    private void addNewDeck() {}

    public void show() {
        start(stage);
    }
}