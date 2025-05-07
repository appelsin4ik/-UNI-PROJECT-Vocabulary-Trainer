package project;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
        // Main layout with sidebar and content
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Create sidebar
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        // Create main content area
        VBox content = createMainContent();
        root.setCenter(content);

        // Set up the scene
        stage.setScene(new Scene(root, 1000, 700)); // Increased width for sidebar
        stage.setTitle("Vocabulary Learner");
        stage.show();
    }

    //Enthält die Funktionalität, um die Sidebar zu rendern
    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20, 0, 0, 0));
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setMinWidth(200);
        sidebar.setPrefWidth(200);

        // Sidebar title
        Label sidebarTitle = new Label("Navigation");
        sidebarTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        sidebarTitle.setPadding(new Insets(0, 0, 20, 20));

        // Navigation icons
        FontAwesomeIconView tableIcon = new FontAwesomeIconView(FontAwesomeIcon.TABLE);
        tableIcon.setSize("25px");
        tableIcon.setFill(Color.WHITE);

        FontAwesomeIconView barsIcon = new FontAwesomeIconView(FontAwesomeIcon.BARS);
        barsIcon.setSize("25px");
        barsIcon.setFill(Color.WHITE);

        FontAwesomeIconView slidersIcon = new FontAwesomeIconView(FontAwesomeIcon.SLIDERS);
        slidersIcon.setSize("25px");
        slidersIcon.setFill(Color.WHITE);

        FontAwesomeIconView cardsIcon = new FontAwesomeIconView(FontAwesomeIcon.STICKY_NOTE);
        cardsIcon.setSize("25px");
        cardsIcon.setFill(Color.WHITE);

        //Navigation buttons
        Button cardsButton = createNavButton( "Karten", cardsIcon);
        Button decksButton = createNavButton("Decks", barsIcon);
        Button managementButton = createNavButton("Karten-Verwaltung", tableIcon);
        Button settingsButton = createNavButton("Einstellungen", slidersIcon);

        // Add all elements to sidebar
        sidebar.getChildren().addAll(
                sidebarTitle,
                cardsButton,
                decksButton,
                managementButton,
                settingsButton
        );

        return sidebar;
    }

    //Erstellt die einzelnen Elemente der Sidebar (also z.B. Einstellungen, Karten etc.)
    private Button createNavButton(String text, FontAwesomeIconView icon) {
        Button button = new Button("  " + text, icon);
        button.setPadding(new Insets(0, 0, 0, 0));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.BASELINE_LEFT);
        button.setStyle("-fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-color: transparent; -fx-padding: 10px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-color: #34495e; -fx-padding: 10px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-color: transparent; -fx-padding: 10px;"));
        return button;
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