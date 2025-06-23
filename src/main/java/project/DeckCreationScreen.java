package project;
import com.google.gson.GsonBuilder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeckCreationScreen extends BorderPane { 

    /**
     * ScrollPane für überfällige Karten-Rows
     */
    private ScrollPane scrollPane;

    /** Container für den gesamte UI
     */
    private VBox createCollection;

    /**
     * Textfeld für den Namen des zu erstellenden Decks
     */
    private TextField deckNameField;

    /**
     * Container für die Liste der Karteneingabefelder
     */
    private VBox cardListContainer;

    /**
     * Liste der Eingabefeld-Paare (Begriff und Übersetzung) für die Karteikarten
     */
    private List<Pair<TextField, TextField>> cardFields;


    /**
     * Konstruktor, der die Benutzeroberfläche initialisiert und die Sidebar einrichtet
     */
    public DeckCreationScreen() {
        this.setStyle(StyleConstants.BACKGROUND_DEFAULT);
        this.setCenter(buildMainContent());
    }

    private VBox buildMainContent() {
        createCollection = new VBox(20);
        createCollection.setPadding(new Insets(20));

        Label title = new Label("Neues Deck erstellen");
        title.setStyle(StyleConstants.LABEL_TITLE);

        deckNameField = new TextField();
        deckNameField.setPromptText("Deck-Name eingeben");
        deckNameField.setPrefWidth(500);
        deckNameField.setStyle(StyleConstants.TEXTFIELD_HEADER);

        cardListContainer = new VBox(20);
        cardListContainer.setStyle(StyleConstants.TRANSPARENT);

        scrollPane = new ScrollPane(cardListContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle(StyleConstants.TRANSPARENT);

        // optional: schmale, dezente Scrollbar
        Platform.runLater(() -> {
            Node viewport = scrollPane.lookup(".viewport");
            if (viewport != null) {
                viewport.setStyle(StyleConstants.SCROLLBAR);
            }
        });

        /* ScrollPane wächst mit dem VBox-Layout */
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        cardFields = new ArrayList<>();
        addCardRow();

        Button saveDeckButton = new Button("Deck speichern und fortführen");

        saveDeckButton.setStyle(StyleConstants.BUTTON_SAVE_DECK);
        saveDeckButton.setOnMouseEntered(e -> saveDeckButton.setStyle(StyleConstants.BUTTON_SAVE_DECK_HOVER));
        saveDeckButton.setOnMouseExited(e -> saveDeckButton.setStyle(StyleConstants.BUTTON_SAVE_DECK));
        saveDeckButton.setOnAction(e -> saveDeckToFile(Main.getStage()));

        createCollection.getChildren().addAll(title, deckNameField, scrollPane, saveDeckButton);

        return createCollection;
    }

    /**
     * speichert das erstellte Deck als JSON-Datei
     * @param stage Das Hauptfenster der Anwendung
     */
    private void saveDeckToFile(Stage stage) {

        /* 1) Grund-Validierung */
        String deckName = deckNameField.getText().trim();
        if (deckName.isBlank()) { NotificationIO.showWarning("Fehler", "Bitte einen Deck-Namen eingeben."); return; }

        List<Card> cards = collectCards();                    
        if (cards.isEmpty())  { NotificationIO.showWarning("Fehler", "Bitte mindestens eine Karte eingeben."); return; }

        /* 2) Deck-Objekt erstellen */
        String fileName = deckName.endsWith(".json") ? deckName : deckName + ".json";
        Deck deck = new Deck(deckName, cards);
        deck.setSourceFileName(fileName);

        /* 3) Existenz prüfen + evtl. Dialog */
        List<Deck> all = DeckManager.getInstance().getDecks();
        boolean duplicate = all.stream().anyMatch(d ->
            d.getName().equalsIgnoreCase(deck.getName()) ||
            d.getSourceFileName().equalsIgnoreCase(deck.getSourceFileName())
        );

        if (duplicate && !confirmOverwrite(deck)) return;
        all.removeIf(d -> d.getName().equalsIgnoreCase(deck.getName()));  // alte Version löschen
        all.add(deck);

        /* 4) Datei schreiben */
        File dir = new File("saves");
        if (!dir.exists()) dir.mkdirs();
        try (FileWriter w = new FileWriter(new File(dir, fileName))) {
            new GsonBuilder().setPrettyPrinting().create().toJson(deck, w);
            DeckDisplayScreen.getInstance().refreshContent();
            NotificationIO.showInfo("Erfolg", "Deck wurde erfolgreich gespeichert!");
            resetForm();
        } catch (IOException ex) {
            NotificationIO.showWarning("Fehler", "Fehler beim Speichern: " + ex.getMessage());
        }
    }

    /**
     * fügt dem Edit-Bereich eine neue Zeile (Begriff + Übersetzung + +|– Button) hinzu.  
     *  – Ein „+“ erzeugt eine neue, leere Zeile darunter.  
     *  – Ein „–“ entfernt die aktuelle Zeile wieder.  
     * Die Methode sorgt dabei auch für ein sanftes Nach-unten-Scrollen.
     */
    private void addCardRow() {
        TextField begriff = new TextField();
        begriff.setPromptText("Begriff");
        begriff.setPrefWidth(400);
        begriff.setStyle(StyleConstants.TEXTFIELD_CONTENT);

        TextField uebersetzung = new TextField();
        uebersetzung.setPromptText("Übersetzung");
        uebersetzung.setPrefWidth(400);
        uebersetzung.setStyle(StyleConstants.TEXTFIELD_CONTENT);

        FontAwesomeIconView plus_icon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
        FontAwesomeIconView minus_icon = new FontAwesomeIconView(FontAwesomeIcon.MINUS);

        Button actionBtn = new Button();
        actionBtn.setGraphic(plus_icon);
        actionBtn.setStyle(StyleConstants.BUTTON_ADD_CARD);

        HBox row = new HBox(12, begriff, uebersetzung, actionBtn);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle(StyleConstants.CARD_BOX_EDIT);

        final boolean[] isPlus = {true};

        actionBtn.setOnMouseEntered(e -> {
            if (isPlus[0]) actionBtn.setStyle(StyleConstants.BUTTON_ADD_CARD_HOVER);
            else actionBtn.setStyle(StyleConstants.BUTTON_REMOVE_CARD_HOVER);
        });

        actionBtn.setOnMouseExited(e -> {
            if (isPlus[0]) actionBtn.setStyle(StyleConstants.BUTTON_ADD_CARD);
            else actionBtn.setStyle(StyleConstants.BUTTON_REMOVE_CARD);
        });

        actionBtn.setOnAction(e -> {
            if (isPlus[0]) {
                isPlus[0] = false;
                actionBtn.setGraphic(minus_icon);
                actionBtn.setStyle(StyleConstants.BUTTON_REMOVE_CARD);
                addCardRow();
            } else {
                cardListContainer.getChildren().remove(row);
                cardFields.removeIf(p -> p.getKey() == begriff && p.getValue() == uebersetzung);
            }
        });

        cardListContainer.getChildren().add(row);
        cardFields.add(new Pair<>(begriff, uebersetzung));

        // Smooth scroll to bottom
        Timeline smoothScroll = new Timeline();
        KeyFrame kf = new KeyFrame(Duration.millis(10), event -> {
            double current = scrollPane.getVvalue();
            double target = 1;
            double step = 0.03;

            // nur scrollen, wenn nicht schon ganz unten
            if (current < target - 0.001) {
                scrollPane.setVvalue(Math.min(current + step, target));
            }
        });
        smoothScroll.getKeyFrames().add(kf);
        smoothScroll.setCycleCount(15);
        smoothScroll.play();
    }

    /** zeigt den Überschreib-Dialog und gibt true zurück, falls der User „Überschreiben“ wählt */
    private boolean confirmOverwrite(Deck deck) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deck existiert bereits");
        alert.setHeaderText("Ein Deck mit dem Namen \"" + deck.getName() + "\" existiert bereits.");
        alert.setContentText("Möchtest du es überschreiben?");

        ButtonType yes     = new ButtonType("Überschreiben");
        ButtonType cancel  = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(cancel, yes);

        Button overwrite = (Button) alert.getDialogPane().lookupButton(yes);
        overwrite.setStyle(StyleConstants.BUTTON_OVERWRITE_DIALOG);
        overwrite.setOnMouseEntered(e -> overwrite.setStyle(StyleConstants.BUTTON_OVERWRITE_DIALOG_HOVER));
        overwrite.setOnMouseExited (e -> overwrite.setStyle(StyleConstants.BUTTON_OVERWRITE_DIALOG));

        Button abort = (Button) alert.getDialogPane().lookupButton(cancel);
        abort.setStyle(StyleConstants.BUTTON_CANCEL_DELETE_DIALOG);
        abort.setOnMouseEntered(e -> abort.setStyle(StyleConstants.BUTTON_CANCEL_DELETE_DIALOG_HOVER));
        abort.setOnMouseExited (e -> abort.setStyle(StyleConstants.BUTTON_CANCEL_DELETE_DIALOG));

        return alert.showAndWait().orElse(cancel) == yes;
    }

    /** liest alle Eingabefelder aus und erzeugt eine Liste von Card-Objekten */
    private List<Card> collectCards() {
        List<Card> list = new ArrayList<>();
        for (Pair<TextField,TextField> p : cardFields) {
            String term = p.getKey().getText().trim();
            String trans = p.getValue().getText().trim();
            if (!term.isEmpty() && !trans.isEmpty()) list.add(new Card(term, trans));
        }
        return list;
    }

    /** setzt das Formular auf Anfang zurück */
    private void resetForm() {
        deckNameField.clear();
        cardListContainer.getChildren().clear();
        cardFields.clear();
        addCardRow();
    }

} 
