package project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
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

    /** ScrollPane, die den deckContainer enthält */
    private ScrollPane deckScroll;   

    private static DeckDisplayScreen instance;

    private FlowPane pinnedDeckContainer;

    private FlowPane userDeckContainer;

    /**
     * Constructor dieses Screens
     * @param deckManager deckmanager mit Decks die angezeigt werden
     */
    public DeckDisplayScreen(DeckManager deckManager) {
        this.deckManager = deckManager;

        // UI im Konstruktor einmalig für diese Klasse konstruieren
        // Main layout with sidebar and content
        this.setStyle(StyleConstants.BACKGROUND_DEFAULT);

        // Create main content area
        this.setCenter(createMainContent());

        // Set up the scene
        instance = this;
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

        // Titel
        Label title = new Label("Deine Decks");
        title.setStyle(StyleConstants.LABEL_TITLE);

        pinnedDeckContainer = new FlowPane(15, 15);
        pinnedDeckContainer.setPrefWrapLength(950);

        userDeckContainer = new FlowPane(15, 15);
        userDeckContainer.setPrefWrapLength(950);

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
            pinnedDeckContainer.getChildren().add(createDeckBox(deck));
        }

        // Dann alle anderen
        for (Deck deck : otherDecks) {
            userDeckContainer.getChildren().add(createDeckBox(deck));
        }

        // Add Deck hinzufügen
        userDeckContainer.getChildren().add(createAddDeckBox());

        Label pinnedLabel = new Label("📌 Gepinnte Decks");
        pinnedLabel.setStyle(StyleConstants.LABEL_SUBTITLE);

        Label ownLabel = new Label("🧠 Eigene Decks");
        ownLabel.setStyle(StyleConstants.LABEL_SUBTITLE);

        Separator separator = new Separator();

        deckLayout.getChildren().addAll(pinnedLabel, pinnedDeckContainer, separator, ownLabel, userDeckContainer);

        // Add "Add Deck" card
        deckScroll = new ScrollPane(deckLayout);    
        deckScroll.setFitToWidth(true);
        deckScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        deckScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        deckScroll.setStyle(StyleConstants.TRANSPARENT);

        // schmale, dezente Scrollbar
        Platform.runLater(() -> {
            var scrollbar = deckScroll.lookup(".scroll-bar:vertical");
            if (scrollbar != null) {
                scrollbar.setStyle(StyleConstants.SCROLLBAR);
            }
        });

        // ScrollPane wächst mit dem VBox-Layout 
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
        startButton.setStyle(StyleConstants.BUTTON_START);

        if (deck.getSourceFileName() != null && deck.getSourceFileName().toLowerCase().contains("food") ||
        deck.getSourceFileName().toLowerCase().contains("advanced") ||
        deck.getSourceFileName().toLowerCase().contains("basic")) {
            deckBox.setStyle(StyleConstants.DECK_BOX_PINNED);
        }

        startButton.setOnMouseEntered(e -> {
            if (startButton.getStyle().equals(StyleConstants.BUTTON_START)) {
                startButton.setStyle(StyleConstants.BUTTON_START_HOVER);
            }
        });

        startButton.setOnMouseExited(e -> {
            if (startButton.getStyle().equals(StyleConstants.BUTTON_START_HOVER)) {
                startButton.setStyle(StyleConstants.BUTTON_START);
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
        CardViewScreen cardView = new CardViewScreen(deck, getBackRunnable());
        cardView.show();
    }


    
    /**
     * Erstellt ein {@link Runnable}, das beim Zurücknavigieren ausgeführt wird.
     * 
     * Das Runnable ruft das zentrale {@link MainLayout} auf und zeigt diesen Screen erneut an.
     * Praktisch, um dem Nutzer z. B. nach einer Aktion wieder zur Übersicht zu springen.
     *
     * @return Ein {@link Runnable}, das diesen Screen erneut ins Layout einfügt
     */
    private Runnable getBackRunnable() {
        return () -> {
            MainLayout layout = MainLayout.getInstance();
            layout.show(); 
            layout.showContent(this); 
        };
    }

    /**
     * Erstellt eine Deckbox mit einem großen Plus Icon, durch das neue Decks hinzugefügt werden können
     */
    private VBox createAddDeckBox() {
        VBox addDeckBox = new VBox();
        addDeckBox.setAlignment(Pos.CENTER);
        addDeckBox.setPadding(new Insets(15));
        addDeckBox.setStyle(StyleConstants.BUTTON_ADD_DECK_BOX);
        addDeckBox.setMinWidth(228);
        addDeckBox.setMinHeight(120);

        // Großes Plus-Symbol
        FontAwesomeIconView plusIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
        plusIcon.setSize("50px");
        plusIcon.setFill(Color.web("#7f8c8d")); 

        // Box clickbar machen
        addDeckBox.setOnMouseClicked(e -> addNewDeck());
        addDeckBox.setOnMouseEntered(e -> {
            addDeckBox.setStyle(StyleConstants.BUTTON_ADD_DECK_BOX_HOVER);
            plusIcon.setFill(Color.web("#3498db")); 
        });
        addDeckBox.setOnMouseExited(e -> {
            addDeckBox.setStyle(StyleConstants.BUTTON_ADD_DECK_BOX);
            plusIcon.setFill(Color.web("#7f8c8d"));
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

        try {
            File initialDir = Main.getUserdataPath().toFile();
            if (initialDir.exists()) {
                fileChooser.setInitialDirectory(initialDir);
            }
        } catch (Exception e) {
            System.err.println("Couldn't set initial directory: " + e.getMessage());
        }

        File selectedFile = fileChooser.showOpenDialog(Main.getStage());

        if (selectedFile != null) {
            try {
                Deck importedDeck = Deck.readFile(selectedFile);
                if (importedDeck == null) {
                    NotificationIO.showWarning("Fehler", "Falsches Deckformat oder beschädigte Datei.");
                    return;
                }

                // Extrahiere Dateinamen ohne Endung und setze als SourceFileName
                String fileBaseName = selectedFile.getName().replaceFirst("[.][^.]+$", "");
                importedDeck.setSourceFileName(fileBaseName);

                // 1. Prüfe auf identischen Inhalt mit anderem Dateinamen
                Deck sameContent = deckManager.getDecks().stream()
                    .filter(d ->
                        d.getName().equalsIgnoreCase(importedDeck.getName()) &&
                        d.getCards().equals(importedDeck.getCards()) &&
                        !d.getSourceFileName().equalsIgnoreCase(importedDeck.getSourceFileName()))
                    .findFirst().orElse(null);
                System.out.println(sameContent);
                if (sameContent != null) {
                    NotificationIO.showWarning(
                        "Import abgebrochen",
                        "Ein identisches Deck existiert bereits als \"" +
                        sameContent.getSourceFileName() + "\"."
                    );
                    return;
                }

                // 2. Prüfe auf gleichen Namen (unabhängig vom Inhalt)
                boolean nameClash = deckManager.getDecks().stream()
                    .anyMatch(d -> d.getName().equalsIgnoreCase(importedDeck.getName()));

                if (nameClash) {
                    NotificationIO.showWarning(
                        "Deck bereits vorhanden",
                        "Ein Deck mit dem Namen \"" + importedDeck.getName() + "\" existiert bereits."
                    );
                    return;
                }

                DeckManager.saveDeck(importedDeck);
                deckManager.addDeck(importedDeck);
                importedDeck.save();
                refreshContent();

                NotificationIO.showInfo("Import erfolgreich", "Das Deck wurde importiert.");

            } catch (IOException e) {
                NotificationIO.showWarning("Fehler", "Die ausgewählte Datei ist ungültig oder konnte nicht gelesen werden.");
                e.printStackTrace();
            }
        }
    }


    /**
     * Aktualisiert den Hauptinhalt des Screens durch Neuerstellen und Setzen des Contents
     */
    public void refreshContent() {
         this.setCenter(createMainContent());
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


    /**
     * Gibt das Haupt-UI-Element (Root-Node) dieses Screens zurück.
     * 
     * Diese Methode wird von außen aufgerufen, wenn der Screen im Hauptlayout
     * angezeigt werden soll.
     *
     * @return Das komplette UI-Element dieses Screens (dieses Objekt selbst)
     */
    public Parent getContent() {
        return this;
    }
}


