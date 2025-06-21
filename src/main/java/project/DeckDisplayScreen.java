package project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Separator;

/**
 * Screen im Decks anzuzeigen
 */
public class DeckDisplayScreen extends BorderPane {

    /** deck Daten Model */
    private DeckManager deckManager;

    /** Scene in der dieser Screen angezeigt wird */
    private Scene scene;

    /** ScrollPane, die den deckContainer enthält */
    private ScrollPane deckScroll;   

    private static DeckDisplayScreen instance;

    // private static FlowPane deckContainer;

    private FlowPane pinnedDeckContainer;

    private FlowPane userDeckContainer;

    /**
     * Constructor dieses Screens
     * @param deckManager deckmanager mit Decks die angezeigt werden
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
                        sidebarManager.updateButton(sidebarManager.getCardsButton());
                        SidebarManager.showCreationScreen();
                        break;

                    case "Karten-Verwaltung":
                        sidebarManager.updateButton(sidebarManager.getManagementButton());
                        SidebarManager.showManagementScreen();
                        break;
                    case "Einstellungen":
                        sidebarManager.updateButton(sidebarManager.getSettingsButton());
                        SidebarManager.showSettingsScreen();
                        break;

                    case "About":
                        AboutDialog.show();
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
        instance = this;
        scene = new Scene(this, 1000, 700);
    }

    /**
     * diesen Screen anzeigen
     */
    public void show() {
        Main.getStage().setScene(scene);
    }

    /**
     * Ein einzelnes Deck-Container zurückgeben
     * @return FlowPane Container mit allen Deck-Boxen
     */
    private VBox createMainContent() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        VBox deckLayout = new VBox(20);
        deckLayout.setPadding(new Insets(10));

        // Title
        Label title = new Label("Deine Decks");
        title.setStyle(StyleConstants.TITLE);

        // Deck container
        // deckContainer = new FlowPane();
        // deckContainer.setHgap(15);
        // deckContainer.setVgap(15);
        // deckContainer.setPadding(new Insets(10));

        pinnedDeckContainer = new FlowPane(15, 15);
        // pinnedDeckContainer.setPadding(new Insets(10));
        pinnedDeckContainer.setPrefWrapLength(950);

        userDeckContainer = new FlowPane(15, 15);
        // userDeckContainer.setPadding(new Insets(10));
        userDeckContainer.setPrefWrapLength(950);


        // for (Deck deck : this.deckManager.getDecks()) {
        //     VBox deckBox = createDeckBox(deck);
        //     deckContainer.getChildren().add(deckBox);
        // }

        // deckContainer.getChildren().clear();

        List<Deck> pinnedDecks = new ArrayList<>();
        List<Deck> otherDecks = new ArrayList<>();

        for (Deck deck : deckManager.getDecks()) {
            String source = deck.getSourceFileName();
            if (source != null && (
                    source.toLowerCase().contains("advanced") ||
                    source.toLowerCase().contains("basic") ||
                    source.toLowerCase().contains("food")
            )) {
                pinnedDecks.add(deck);
            } else {
                otherDecks.add(deck);
            }
        }

        // Zuerst gepinnte Decks hinzufügen
        for (Deck deck : pinnedDecks) {
            // deckContainer.getChildren().add(createDeckBox(deck));
            pinnedDeckContainer.getChildren().add(createDeckBox(deck));
        }

        // Dann alle anderen
        for (Deck deck : otherDecks) {
            // deckContainer.getChildren().add(createDeckBox(deck));
            userDeckContainer.getChildren().add(createDeckBox(deck));
        }

        // Add Deck hinzufügen
        // deckContainer.getChildren().add(createAddDeckBox());
        userDeckContainer.getChildren().add(createAddDeckBox());

        Label pinnedLabel = new Label("📌 Gepinnte Decks");
        pinnedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label ownLabel = new Label("🧠 Eigene Decks");
        ownLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Separator separator = new Separator();

        deckLayout.getChildren().addAll(pinnedLabel, pinnedDeckContainer, separator, ownLabel, userDeckContainer);

        // Add "Add Deck" card
        // VBox addDeckBox = createAddDeckBox();
        // deckContainer.getChildren().add(addDeckBox);

        // deckScroll = new ScrollPane(deckContainer);
        deckScroll = new ScrollPane(deckLayout);    
        deckScroll.setFitToWidth(true);               // Inhalt nutzt volle Breite
        deckScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        deckScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        deckScroll.setStyle(StyleConstants.TRANSPARENT);

        // schmale, dezente Scrollbar
        Platform.runLater(() -> {
            var scrollbar = deckScroll.lookup(".scroll-bar:vertical");
            if (scrollbar != null) {
                scrollbar.setStyle("-fx-pref-width: 8px; -fx-background-color: transparent;");
            }
        });

        /* ScrollPane wächst mit dem VBox-Layout */
        VBox.setVgrow(deckScroll, Priority.ALWAYS);

        content.getChildren().addAll(title, deckScroll);
        return content;
    }


    /**
     * Erstellt eine einzelne Deckbox. Über die Decks wird dann iteriert und für jedes Deck diese Methode
     * aufgerufen, um ein neues UI dafür zu erstellen.
     * @param deck das Deck, zu dem die UI erstellt werden soll
     */
    private VBox createDeckBox(Deck deck) {
        VBox deckBox = new VBox(10);
        deckBox.setAlignment(Pos.CENTER);
        deckBox.setPadding(new Insets(15));
        deckBox.setStyle(StyleConstants.DECK_BOX);
        deckBox.setMinWidth(228);

        Label deckName = new Label(deck.getName());
        deckName.setStyle(StyleConstants.DECK_BOX_NAME);

        Label cardCount = new Label(deck.getCards().size() + " cards");
        cardCount.setStyle(StyleConstants.DECK_BOX_COUNT);

        Button startButton = new Button("Start");
        startButton.setStyle(StyleConstants.START_BUTTON);

        if (deck.getSourceFileName() != null && deck.getSourceFileName().toLowerCase().contains("food") ||
        deck.getSourceFileName().toLowerCase().contains("advanced") ||
        deck.getSourceFileName().toLowerCase().contains("basic")) {
            // Label pin = new Label("📌");
            // deckBox.getChildren().add(0, pin);
            // deckBox.setStyle("-fx-background-color:rgb(211, 209, 209); -fx-background-radius: 10; " +
            //     "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");
            deckBox.setStyle(StyleConstants.DECK_BOX_PINNED);
        }

        startButton.setOnMouseEntered(e -> {
            if (startButton.getStyle().equals(StyleConstants.START_BUTTON)) {
                startButton.setStyle(StyleConstants.START_BUTTON_HOVER);
            }
        });

        startButton.setOnMouseExited(e -> {
            if (startButton.getStyle().equals(StyleConstants.START_BUTTON_HOVER)) {
                startButton.setStyle(StyleConstants.START_BUTTON);
            }
        });


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
        addDeckBox.setStyle(StyleConstants.ADD_DECK_BOX_BUTTTON);
        addDeckBox.setMinWidth(228);
        addDeckBox.setMinHeight(120); // Match height of other deck boxes

        // Create big plus icon
        FontAwesomeIconView plusIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
        plusIcon.setSize("50px");
        plusIcon.setFill(Color.web("#7f8c8d")); // Gray color

        // Make the whole box clickable
        addDeckBox.setOnMouseClicked(e -> addNewDeck());
        addDeckBox.setOnMouseEntered(e -> {
            addDeckBox.setStyle(StyleConstants.ADD_DECK_BOX_BUTTTON_HOVER);
            plusIcon.setFill(Color.web("#3498db")); // Blue color on hover
        });
        addDeckBox.setOnMouseExited(e -> {
            addDeckBox.setStyle(StyleConstants.ADD_DECK_BOX_BUTTTON);
            plusIcon.setFill(Color.web("#7f8c8d")); // Gray color
        });

        addDeckBox.getChildren().add(plusIcon);
        return addDeckBox;
    }

    /**
     * Fügt ein neues Deck durch Importieren einer JSON-Datei hinzu.
     * Öffnet einen FileChooser-Dialog und lässt den Benutzer eine JSON-Datei auswählen.
     * Das importierte Deck wird zum DeckManager hinzugefügt und gespeichert.
     */
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
                if (importedDeck == null) {
                    NotificationIO.showWarning("Fehler", "Falsches Deck");
                    return;
                }

                boolean deckExists = this.deckManager.getDecks().stream()
                        .anyMatch(d -> d.getName().equals(importedDeck.getName()));

                if (deckExists) {
                    System.out.println("Deck with this name already exists!");
                } else {
                    DeckManager.saveDeck(importedDeck);
                    this.deckManager.addDeck(importedDeck);
                    importedDeck.save();
                    //DeckManager.saveDeck(importedDeck);
                    refreshContent();
                    show();
                }
            } catch (IOException e) {
                //System.err.println("Error importing deck: " + e.getMessage());
                NotificationIO.showWarning("Fehler","Folgende Json beitzt ungültigen Format!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Aktualisiert den Hauptinhalt des Screens durch Neuerstellen und Setzen des Contents
     */
    public void refreshContent() {
         this.setCenter(createMainContent()); // Recreate and set the content
    }

    /**
     * Gibt die einzige Instanz dieser Klasse zurück (Singleton-Pattern)
     * @return Die Instanz von DeckDisplayScreen
     */
    public static DeckDisplayScreen getInstance() {
        if(instance == null) {
            instance = new DeckDisplayScreen(DeckManager.getInstance());
        }

        return instance;
    }

    /**
     * Aktualisiert die Anzeige aller Decks.
     * Löscht alle bestehenden Deck-Anzeigen und erstellt sie neu
     * basierend auf den aktuellen Daten aus dem DeckManager.
     */
    // public void updateDecks() {
    //     deckContainer.getChildren().clear();
    //     for (Deck deck : DeckManager.getInstance().getDecks()) {
    //         Label deckLabel = new Label(deck.getName());
    //         deckContainer.getChildren().add(deckLabel);
    //     }
    // }

    /**
     * Speichert ein Deck in eine JSON-Datei
     * @param deck Das zu speichernde Deck
     */
    public static void saveDeckToFile(Deck deck) {
        if (deck.getSourceFileName() == null) {
            System.err.println("❌ Fehler: Kein Dateiname im Deck gesetzt.");
            return;
        }

        File file = new File("saves", deck.getSourceFileName());
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(deck, writer);
            System.out.println("✅ Änderungen gespeichert in: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("❌ Fehler beim Speichern des Decks: " + e.getMessage());
        }
    }
}


