package project.screens;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import project.control.DeckManager;
import project.items.Card;
import project.items.Deck;
import project.util.io.NotificationIO;
import project.util.style.StyleConstants;

public class DeckManagementScreen extends BorderPane {


    private VBox cardListBox;   
    private Deck selectedDeck;    

    private Button exportDeckButton;

    private VBox deckListBox;

    private ScrollPane cardScroll;

    private ObservableList<Deck> deckObservableList;
    private TextField deckNameField;
    private Button deleteDeckButton;

    private TextField searchField;
    private HBox searchFieldContainer;

    /** Konstruktor – Initialisiert das Deck-Management-Layout
     *  mit Standard-Hintergrund und Hauptinhalt 
     */
    public DeckManagementScreen() {
        this.setStyle(StyleConstants.BACKGROUND_DEFAULT);
        this.setCenter(buildMainContent());
    }

    // Haupt-Funktion

    /** Baut die Hauptstruktur: Titel, linke Deck-Liste mit Scroll,
     *  rechter Editor-Bereich mit Deck-Details 
     */
    private VBox buildMainContent() {
        Label title = new Label("Deck-Verwaltung");

        title.setStyle(StyleConstants.LABEL_TITLE);
        VBox.setMargin(title, new Insets(0, 0, 10, 0));

        HBox mainContent = new HBox(20);
        mainContent.setPadding(new Insets(20));

        deckObservableList = FXCollections.observableArrayList(DeckManager.getInstance().getDecks());

        deckListBox = new VBox(15); 

        deckListBox.setPadding(new Insets(0, 10, 10, 0));

        ScrollPane deckScroll = new ScrollPane();

        deckScroll.setContent(deckListBox);
        deckScroll.setFitToWidth(true);
        deckScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        deckScroll.setStyle(StyleConstants.TRANSPARENT);
        deckScroll.setPrefWidth(450);

        // schmale, dezente Scrollbar
        Platform.runLater(() -> {
            var scrollbar = deckScroll.lookup(".scroll-bar:vertical");
            if (scrollbar != null) {
                scrollbar.setStyle(StyleConstants.SCROLLBAR);
            }
        });

        fillDecks();

        VBox editorPane = new VBox(15);

        editorPane.setPadding(new Insets(10));
        editorPane.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 20;");
        editorPane.setPrefWidth(680);
        editorPane.setPrefHeight(600);

        deckNameField = new TextField();

        deckNameField.setPromptText("Deck-Name");
        deckNameField.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #ddd; -fx-border-radius: 6; -fx-padding: 8;");
        deckNameField.setDisable(true);

        deleteDeckButton = new Button("Deck löschen");

        deleteDeckButton.setStyle(StyleConstants.BUTTON_DELETE_DECK);
        deleteDeckButton.setDisable(true);

        deleteDeckButton.setOnMouseEntered(e -> {
            if (!deleteDeckButton.isDisabled()) {
                deleteDeckButton.setStyle(StyleConstants.BUTTON_DELETE_DECK_HOVER);
            }
        });

        deleteDeckButton.setOnMouseExited(e -> {
            if (!deleteDeckButton.isDisabled()) {
                deleteDeckButton.setStyle(StyleConstants.BUTTON_DELETE_DECK);
            }
        });

        exportDeckButton = new Button("Deck exportieren");

        exportDeckButton.setDisable(true);
        exportDeckButton.setStyle(StyleConstants.BUTTON_EXPORT_DECK);
        exportDeckButton.setOnMouseEntered(e -> {
            if (!exportDeckButton.isDisabled()) {
                exportDeckButton.setStyle(StyleConstants.BUTTON_EXPORT_DECK_HOVER);
            }
        });
        exportDeckButton.setOnMouseExited(e -> {
            if (!exportDeckButton.isDisabled()) {
                exportDeckButton.setStyle(StyleConstants.BUTTON_EXPORT_DECK);
            }
        });
        exportDeckButton.setOnAction(e -> exportConfirmation());

        // Cntainer für Buttons
        HBox deckButtons = new HBox(10, exportDeckButton,deleteDeckButton);

        cardListBox = new VBox(10);

        cardListBox.setStyle(StyleConstants.TRANSPARENT);
        cardListBox.setPadding(new Insets(10));

        cardScroll = new ScrollPane(cardListBox);

        cardScroll.setStyle(StyleConstants.TRANSPARENT);
        cardScroll.setFitToWidth(true);
        cardScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // schmale, dezente Scrollbar
        Platform.runLater(() -> {
            var scrollbar = cardScroll.lookup(".scroll-bar:vertical");
            if (scrollbar != null) {
                scrollbar.setStyle(StyleConstants.SCROLLBAR);
            }
        });

        Platform.runLater(() -> {
            Node scrollBar = deckScroll.lookup(".scroll-bar:vertical");
            if (scrollBar != null) {
                scrollBar.setStyle(StyleConstants.SCROLLBAR);
            }
        });

        VBox.setVgrow(cardScroll, Priority.ALWAYS);

        editorPane.getChildren().addAll(deckNameField, deckButtons, cardScroll);

        VBox deckColumn = new VBox(10);
        deckColumn.getChildren().addAll(createSearchField(deckObservableList), deckScroll);
        HBox.setMargin(deckColumn, new Insets(0, 0, 0, -20));

        mainContent.getChildren().addAll(deckColumn, editorPane);
        VBox.setMargin(mainContent, new Insets(-20, 0, 0, 0));

        deckNameField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (selectedDeck != null) {
                selectedDeck.setName(newVal.trim());
                saveCurrentDeck();
                fillDecks();
            }
        });

        deleteDeckButton.setOnAction(e -> {
            // Möglicherweise verschieben
            deleteConfirmation();
            deleteDeckButton.setDisable(true);
            deckNameField.setDisable(true);
            exportDeckButton.setDisable(true);

        });


        VBox result = new VBox(10);
        result.setPadding(new Insets(20));

        result.getChildren().addAll(title,mainContent);

        return result;
    }

    // Confirmation-Fnktionen

    /** Öffnet ein Dialogfenster zum Exportieren des 
     * aktuell ausgewählten Decks als JSON-Datei 
     */
    private void exportConfirmation() {
            if (selectedDeck == null) return;

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Deck exportieren unter...");
            fileChooser.setInitialFileName(selectedDeck.getSourceFileName() != null ? selectedDeck.getSourceFileName() : selectedDeck.getName().toLowerCase().replaceAll("\\s+", "_") + ".json");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Dateien", "*.json"));

            File file = fileChooser.showSaveDialog(getScene().getWindow());
            if (file != null) {
                try (FileWriter writer = new FileWriter(file)) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    gson.toJson(selectedDeck, writer);

                    NotificationIO.showInfo("Export erfolgreich", "Deck wurde gespeichert unter:\n" + file.getAbsolutePath());
                } catch (IOException ex) {
                    NotificationIO.showWarning("Fehler beim Export", "Konnte Deck nicht exportieren: " + ex.getMessage());
                }
            }
        }

    /** Zeigt eine Bestätigungsabfrage zum Löschen des
     *  aktuell ausgewählten Decks 
     */
    private void deleteConfirmation() {
        if (selectedDeck != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Deck löschen");
            confirm.setHeaderText("Möchtest du das Deck wirklich löschen?");
            confirm.setContentText("Deck: \"" + selectedDeck.getName() + "\"");

            ButtonType yes = new ButtonType("Löschen", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirm.getButtonTypes().setAll(yes, no);

            Button yesStyle = (Button) confirm.getDialogPane().lookupButton(yes);
            yesStyle.setStyle(StyleConstants.BUTTON_DELETE_DIALOG);

            yesStyle.setOnMouseEntered(ev -> yesStyle.setStyle(StyleConstants.BUTTON_DELETE_DIALOG_HOVER));

            yesStyle.setOnMouseExited(ev -> yesStyle.setStyle(StyleConstants.BUTTON_DELETE_DIALOG));

            Button noStyle = (Button) confirm.getDialogPane().lookupButton(no);
            noStyle.setStyle(StyleConstants.BUTTON_CANCEL_DELETE_DIALOG);

            noStyle.setOnMouseEntered(ev -> noStyle.setStyle(StyleConstants.BUTTON_CANCEL_DELETE_DIALOG_HOVER));

            noStyle.setOnMouseExited(ev -> noStyle.setStyle(StyleConstants.BUTTON_CANCEL_DELETE_DIALOG));

            confirm.showAndWait().ifPresent(response -> {
                if (response == yes) {
                    DeckManager.getInstance().getDecks().remove(selectedDeck);
                    deckObservableList.remove(selectedDeck);

                    File file = new File("saves", selectedDeck.getSourceFileName());
                    if (file.exists()) file.delete();

                    selectedDeck = null;
                    deckNameField.clear();
                    cardListBox.getChildren().clear();
                    fillDecks();
                }
            });
        }
    }
    
    // Create-Funktionen

    /** Erstellt die obere Suchleiste mit Icon,
     *  Suchfeld und Clear-Button 
     */
    private HBox createSearchField(ObservableList<Deck> deckList) {
        searchField = new TextField();
        searchField.setPromptText("Decks durchsuchen...");
        searchField.setStyle(StyleConstants.TEXTFIELD_SEARCH);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        FontAwesomeIconView searchIcon = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
        searchIcon.setSize("16px");
        searchIcon.setFill(Color.GRAY);

        Button clearButton = new Button("✕");
        clearButton.setVisible(false);
        clearButton.setStyle(StyleConstants.BUTTON_CLEAR);

        searchField.textProperty().addListener((obs, oldText, newText) -> {
            clearButton.setVisible(!newText.isBlank());
            filterDecks(newText.trim().toLowerCase());
        });

        clearButton.setOnAction(e -> searchField.clear());

        searchFieldContainer = new HBox(6, searchIcon, searchField, clearButton);
        searchFieldContainer.setAlignment(Pos.CENTER_LEFT);
        searchFieldContainer.setPadding(new Insets(8));
        searchFieldContainer.setStyle(StyleConstants.CONTAINER_SEARCH);

        updateSearchFieldWidth();

        return searchFieldContainer;
    }


    /** Erstellt eine visuelle Box zur Darstellung eines einzelnen
     *  Decks (für die Stapelansicht links) 
     */
    VBox createDeckBox(Deck d) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER); 
        box.setPadding(new Insets(15)); 

        // Standardstil abhängig vom ausgewählten Deck
        if (d.equals(selectedDeck)) {
            box.setStyle(StyleConstants.DECK_BOX_SELECTED);
        } else {
            box.setStyle(StyleConstants.DECK_BOX_DEFAULT);
        }
        
        box.setOnMouseEntered(ev -> {
            if (!d.equals(selectedDeck)) {
                box.setStyle(StyleConstants.DECK_BOX_HOVER);
            }
        });

        box.setOnMouseExited(ev -> {
            if (!d.equals(selectedDeck)) {
                box.setStyle(StyleConstants.DECK_BOX_DEFAULT);
            }
        });

        box.setSpacing(5);
        box.setPrefSize(230, 100); 

        Label name = new Label(d.getName());
        name.setStyle(StyleConstants.LABEL_HEADER);

        Label count = new Label(d.getCards().size() + " Karten");
        count.setStyle(StyleConstants.DECK_BOX_COUNT);

        box.getChildren().addAll(name, count);

        box.setOnMouseClicked(e -> {
            selectedDeck = d;
            deckNameField.setText(d.getName());
            cardListBox.getChildren().setAll(createCardList(d.getCards()));
            deleteDeckButton.setDisable(false);
            deckNameField.setDisable(false);
            exportDeckButton.setDisable(false);

            fillDecks();

            Platform.runLater(() -> {
                Node viewport = cardScroll.lookup(".viewport");
                if (viewport != null) {
                    viewport.setStyle(StyleConstants.SCROLLBAR);
                }
            });
        });

        return box;
    }


    /** Erstellt eine spezielle Box zum Hinzufügen
     *  einer neuen Karte ans aktuelle Deck 
     */
    private VBox createAddCardBox() {
        VBox addBox = new VBox();
        addBox.setAlignment(Pos.CENTER);
        addBox.setPadding(new Insets(15));
        addBox.setSpacing(5);
        addBox.setStyle(StyleConstants.CARD_BOX_VIEW);
        addBox.setPrefSize(230, 100);

        Label plus = new Label("+");
        plus.setStyle(StyleConstants.ICON_PLUS);

        Label label = new Label("Karte hinzufügen");
        label.setStyle("""
            -fx-font-size: 12px;
            -fx-text-fill: #888;
        """);

        addBox.getChildren().addAll(plus, label);
        addBox.setOnMouseEntered(e ->{
            addBox.setStyle(StyleConstants.CARD_BOX_VIEW_HOVER);
            plus.setStyle(StyleConstants.ICON_PLUS_HOVER);
        });

        addBox.setOnMouseExited(e ->{
            addBox.setStyle(StyleConstants.CARD_BOX_VIEW);
            plus.setStyle(StyleConstants.ICON_PLUS);
        });
        addBox.setOnMouseClicked(e -> openAddCardDialog());

        return addBox;
    }


    /** Baut eine Liste von Karten-Editor-Boxen
     *  für das rechte Editor-Panel 
     */
    private VBox createCardList(java.util.List<Card> cards) {
        VBox list = new VBox(20); 
        list.setPadding(new Insets(10));

        for (Card c : new ArrayList<>(cards)) { 
            VBox cardBox = new VBox(10);
            cardBox.setPadding(new Insets(20));
            cardBox.setStyle(StyleConstants.CARD_BOX_VIEW);

            TextField vocabField = new TextField(c.getTerm());
            vocabField.setPromptText("Begriff");
            vocabField.setStyle(StyleConstants.TEXTFIELD_EDITABLE);

            TextField transField = new TextField(c.getTranslation());
            transField.setPromptText("Übersetzung");
            transField.setStyle(StyleConstants.TEXTFIELD_EDITABLE);

            Button deleteButton = new Button("Löschen");
            deleteButton.setStyle(StyleConstants.BUTTON_DELETE_CARD);
            deleteButton.setOnMouseEntered(ev -> deleteButton.setStyle(StyleConstants.BUTTON_DELETE_CARD_HOVER));
            deleteButton.setOnMouseExited(ev -> deleteButton.setStyle(StyleConstants.BUTTON_DELETE_CARD));

            deleteButton.setOnAction(e -> {
                cards.remove(c);
                cardListBox.getChildren().setAll(createCardList(cards));
                DeckDisplayScreen.saveDeckToFile(selectedDeck);
                fillDecks();
            });

            vocabField.textProperty().addListener((obs, oldVal, newVal) -> {
                c.setTerm(newVal);
                saveCurrentDeck();
            });
            transField.textProperty().addListener((obs, oldVal, newVal) -> {
                c.setTranslation(newVal);
                saveCurrentDeck();
            });

            Separator sep = new Separator();

            HBox buttonRow = new HBox(deleteButton);
            buttonRow.setAlignment(Pos.BOTTOM_RIGHT);

            cardBox.getChildren().addAll(vocabField, sep, transField, buttonRow);
            list.getChildren().add(cardBox);
        }

        list.getChildren().add(createAddCardBox());
        return list;
    }

    // Hilfsfunktionen

    /** Passt die Breite der Suchleiste dynamisch
     *  an die Anzahl an Decks an 
     */
    private void updateSearchFieldWidth() {
        if (searchFieldContainer != null) {
            int deckCount = deckObservableList.size();
            double containerWidth = deckCount <= 4 ? 235 : 400;
            searchFieldContainer.setPrefWidth(containerWidth);
            searchFieldContainer.setMaxWidth(containerWidth);
        }
    }


    /** Filtert Decks basierend auf dem Suchtext
     *  im Suchfeld 
     */
    void filterDecks(String query) {
        deckListBox.getChildren().clear();
        for (Deck d : deckObservableList) {
            if (d.getName().toLowerCase().contains(query)) {
                VBox box = createDeckBox(d);
                deckListBox.getChildren().add(box);
            }
        }
    }


    /** Speichert das aktuell bearbeitete Deck
     *  ins Dateisystem 
     */
    void saveCurrentDeck() {
        if (selectedDeck == null) return;

        String filename = selectedDeck.getSourceFileName();
        if (filename == null || filename.isBlank() || !filename.endsWith(".json")) {
            System.err.println("[WARN] Speichern abgebrochen: kein gültiger Dateiname");
            return;
        }

        File file = new File("saves", filename);

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(selectedDeck, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** Befüllt die Deck-Liste neu mit allen
     *  aktuellen Decks 
     */
    void fillDecks() {
        if (deckListBox != null) {
            deckListBox.getChildren().clear();
        }

        for (Deck d : deckObservableList) {
            deckListBox.getChildren().add(createDeckBox(d));
        }

        updateSearchFieldWidth();
        
    }


    /** Öffnet ein Eingabe-Dialogfenster zum Hinzufügen
     *  einer neuen Karte ins Deck 
     */
    private void openAddCardDialog() {
        Dialog<Card> dialog = new Dialog<>();
        dialog.setTitle("Neue Karte erstellen");

        TextField vocabField = new TextField();
        vocabField.setPromptText("Begriff");
        vocabField.setStyle(StyleConstants.TEXTFIELD_DIALOG);

        TextField transField = new TextField();
        transField.setPromptText("Übersetzung");
        transField.setStyle(StyleConstants.TEXTFIELD_DIALOG);

        VBox content = new VBox(10, vocabField, transField);
        content.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(content);

        ButtonType okButtonType = new ButtonType("Hinzufügen", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setStyle(StyleConstants.BUTTON_OK_DIALOG);

        okButton.setOnMouseEntered(e -> okButton.setStyle(StyleConstants.BUTTON_OK_DIALOG_HOVER));

        okButton.setOnMouseExited(e -> okButton.setStyle(StyleConstants.BUTTON_OK_DIALOG));

        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle(StyleConstants.BUTTON_CANCEL_DIALOG);

        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle(StyleConstants.BUTTON_CANCEL_DIALOG_HOVER));

        cancelButton.setOnMouseExited(e -> cancelButton.setStyle(StyleConstants.BUTTON_CANCEL_DIALOG));

        okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if (vocabField.getText().isBlank() || transField.getText().isBlank()) {
                event.consume();
                NotificationIO.showWarning("Ungültige Eingabe", "Beide Felder müssen ausgefüllt sein.");
            }
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Card(vocabField.getText().trim(), transField.getText().trim());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(newCard -> {
            if (selectedDeck != null) {
                selectedDeck.getCards().add(newCard);
                cardListBox.getChildren().setAll(createCardList(selectedDeck.getCards()));
                DeckDisplayScreen.saveDeckToFile(selectedDeck);

                fillDecks();
            }
        });
    }


    // Getter-Setter-Funktionen

    public VBox getDeckListBox() {
        return deckListBox; 
    }

    public void setSelectedDeck(Deck d) {
        this.selectedDeck = d; 
    }

    public Deck getSelectedDeck() {
        return selectedDeck; 
    }

    public HBox getSearchFieldContainer() {
        return searchFieldContainer; 
    }

    public ObservableList<Deck> getDeckObservableList() {
        return deckObservableList;
    }
}
