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
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;

/**
 * Screen im Decks anzuzeigen
 */
public class DeckDisplayScreen extends BorderPane {

    /** deck Daten Model */
    private DeckManager deckManager;

    /** Scene in der dieser Screen angezeigt wird */
    private Scene scene;

    /**
     * Constructor dieses Screens
     */
    public DeckDisplayScreen(DeckManager deckManager) {
        super();
        this.deckManager = deckManager;

        // UI im Konstruktor einmalig für diese Klasse konstruieren
        // Main layout with sidebar and content
        this.setStyle("-fx-background-color: #f5f5f5;");

        // Add sidebar
        var sidebarManager = SidebarManager.getInstance();
        this.setLeft(sidebarManager.showSidebar());

        // Action
        for(Button b : sidebarManager.getButtons()) {

            b.setOnAction(e -> {
                switch (b.getText().trim()){
                    case "Karten":
                        SettingsScreen.showWarning();
                        break;
                    // NEED to be DONE

                    case "Karten-Verwaltung":
                        SettingsScreen.showWarning();
                        break;
                    // NEED to be DONE
                    case "Einstellungen":
                        sidebarManager.updateButton(sidebarManager.getSettingsButton());
                        showSettingsScreen();
                        break;

                    default:
                        break;
                }
            });

        }

        // Create main content area
        VBox content = createMainContent();
        this.setCenter(content);

        // Set up the scene
        scene = new Scene(this, 1000, 700);
    }

    /**
     * diesen Screen anzeigen
     */
    public void show() {
        Main.getStage().setScene(scene);
    }

    private void showSettingsScreen() {
        SettingsScreen settingView = new SettingsScreen(this::show, this.deckManager,this);
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
    /**
     * Erstellt eine einzelne Deckbox. Über die Decks wird dann iteriert und für jedes Deck diese Methode
     * aufgerufen um ein neues UI dafür zu erstellen.
     * @param deck das Deck, zu dem die UI erstellt werden soll
     */
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
        startButton.setOnAction(e -> {
            showCardScreen(deck);
        });

        deckBox.getChildren().addAll(deckName, cardCount, startButton);
        return deckBox;
    }

    /**
     * zum Card Screen wechseln
     * @param deck Deck welches angezeigt wird
     */
    private void showCardScreen(Deck deck) {
        CardViewScreen cardView = new CardViewScreen(deck, this::show);
        cardView.show();
    }

    /**
     * Erstellt eine Deckbox mit einem großen Plus Icon, durch das neue Decks hinzugefügt werden können
     */
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

    private void addNewDeck() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Deck from JSON");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // Set initial directory (optional)
        try {
            File initialDir = Main.getUserdataPath().toFile();
            if (initialDir.exists()) {
                fileChooser.setInitialDirectory(initialDir);
            }
        } catch (Exception e) {
            System.err.println("Couldn't set initial directory: " + e.getMessage());
        }

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(Main.getStage());

        if (selectedFile != null) {
            try {
                Deck importedDeck = Deck.readFile(selectedFile);

                boolean deckExists = deckManager.getDecks().stream()
                        .anyMatch(d -> d.getName().equals(importedDeck.getName()));

                if (deckExists) {
                    System.out.println("Deck with this name already exists!");
                } else {
                    deckManager.addDeck(importedDeck);
                    importedDeck.save();
                    refreshContent();
                    show();
                }
            } catch (IOException e) {
                System.err.println("Error importing deck: " + e.getMessage());

                e.printStackTrace();
            }
        }
    }

    private void refreshContent() {
        this.setCenter(createMainContent()); // Recreate and set the content
    }
}
