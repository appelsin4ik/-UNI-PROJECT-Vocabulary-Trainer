package project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
    
    private ScrollPane scrollPane;

    private VBox createCollection;
    /**
     * Die Hauptszene dieser Ansicht
     */
    private Scene scene;
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

        var sidebarManager = SidebarManager.getInstance();
        //Add Sidebar
        this.setLeft(sidebarManager.showSidebar());

        // Action
        for(Button b : sidebarManager.getButtons()) {
            b.setOnAction(e -> {
                switch (b.getText().trim()){
                    case "Einstellungen":
                        sidebarManager.updateButton(sidebarManager.getSettingsButton());
                        SidebarManager.showSettingsScreen();
                        break;
                    case "Karten-Verwaltung":
                        sidebarManager.updateButton(sidebarManager.getManagementButton());
                        SidebarManager.showManagementScreen();
                        break;
                    case "Decks":
                        sidebarManager.updateButton(sidebarManager.getDecksButton());
                        SidebarManager.showDeckScreen();
                        break;
                    case "About":
                        AboutDialog.show();
                        break;
                    default:
                        break;
                }
            });
        }

        this.setStyle("-fx-background-color: #f5f5f5;");
        this.setCenter(createScene());
        scene = new Scene(this, 1000, 700);
    }

    private VBox createScene() {
        createCollection = new VBox(20);
        createCollection.setPadding(new Insets(40));

        Label title = new Label("Neues Deck erstellen");
        title.setStyle(StyleConstants.TITLE);

        deckNameField = new TextField();
        deckNameField.setPromptText("Deck-Name eingeben");
        deckNameField.setPrefWidth(500);
        deckNameField.setStyle(StyleConstants.DECK_TITLE_FIELD);
        VBox.setMargin(deckNameField, new Insets(0, 0, 8, 0)); 


        cardListContainer = new VBox(20);
        cardListContainer.setStyle("-fx-background-color: transparent; ");

        scrollPane = new ScrollPane(cardListContainer);
        scrollPane.setFitToWidth(true);               // Inhalt nutzt volle Breite
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle(StyleConstants.TRANSPARENT);

        // optional: schmale, dezente Scrollbar
        Platform.runLater(() -> {
            scrollPane.lookup(".scroll-bar:vertical")
                    .setStyle(StyleConstants.SCROLLBAR);
        });

        /* ScrollPane wächst mit dem VBox-Layout */
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        cardFields = new ArrayList<>();
        addCardRow();

        Button saveDeckButton = new Button("Deck speichern und fortführen");
        saveDeckButton.setStyle(StyleConstants.DECK_SAVE_BUTTON);
        saveDeckButton.setOnMouseEntered(e -> saveDeckButton.setStyle(StyleConstants.DECK_SAVE_BUTTON_HOVER));
        saveDeckButton.setOnMouseExited(e -> saveDeckButton.setStyle(StyleConstants.DECK_SAVE_BUTTON));
        saveDeckButton.setOnAction(e -> saveDeckToFile(Main.getStage()));

        createCollection.getChildren().addAll(title, deckNameField, scrollPane, saveDeckButton);
        return createCollection;
    }

    public void show() {
        Main.getStage().setScene(scene);
    }

    /**
     * Speichert das erstellte Deck als JSON-Datei
     * @param stage Das Hauptfenster der Anwendung
     */
    private void saveDeckToFile(Stage stage) {
        String deckName = deckNameField.getText().trim();
        if (deckName.isEmpty()) {
            NotificationIO.showWarning("Fehler", "Bitte einen Deck-Namen eingeben.");
            return;
        }

        List<Card> cards = new ArrayList<>();
        for (Pair<TextField, TextField> pair : cardFields) {
            String term = pair.getKey().getText().trim();
            String translation = pair.getValue().getText().trim();
            if (!term.isEmpty() && !translation.isEmpty()) {
                cards.add(new Card(term, translation));
            }
        }

        if (cards.isEmpty()) {
            NotificationIO.showWarning("Fehler", "Bitte mindestens eine Karte mit Begriff und Übersetzung eingeben.");
            return;
        }

        Deck deck = new Deck(deckName, cards);
        deck.setSourceFileName(deckName); // wichtig für Vergleich & Dateiname

        List<Deck> decks = DeckManager.getInstance().getDecks();

        boolean exists = decks.stream().anyMatch(d ->
            d.getName().equalsIgnoreCase(deck.getName()) ||
            d.getSourceFileName().equalsIgnoreCase(deck.getSourceFileName())
        );

        if (exists) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deck existiert bereits");
            alert.setHeaderText("Ein Deck mit dem Namen \"" + deck.getName() + "\" existiert bereits.");
            alert.setContentText("Möchtest du es überschreiben?");

            ButtonType yes = new ButtonType("Überschreiben");
            ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(cancel,yes);

            // Button-Referenzen
            Button overwriteBtn = (Button) alert.getDialogPane().lookupButton(yes);
            overwriteBtn.setStyle("""
                -fx-background-color: #2196F3;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-padding: 10 20;
                -fx-background-radius: 6;
            """);

            overwriteBtn.setOnMouseEntered(e -> overwriteBtn.setStyle("""
                -fx-background-color: #1976D2;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-padding: 10 20;
                -fx-background-radius: 6;
            """));
            overwriteBtn.setOnMouseExited(e -> overwriteBtn.setStyle("""
                -fx-background-color: #2196F3;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-padding: 10 20;
                -fx-background-radius: 6;
            """));

            Button cancelBtn = (Button) alert.getDialogPane().lookupButton(cancel);
            cancelBtn.setStyle("""
                -fx-background-color: #e0e0e0;
                -fx-text-fill: #333;
                -fx-font-size: 14px;
                -fx-padding: 10 20;
                -fx-background-radius: 6;
            """);

            cancelBtn.setOnMouseEntered(e -> cancelBtn.setStyle("""
                -fx-background-color: #cfcfcf;
                -fx-text-fill: #333;
                -fx-font-size: 14px;
                -fx-padding: 10 20;
                -fx-background-radius: 6;
            """));
            cancelBtn.setOnMouseExited(e -> cancelBtn.setStyle("""
                -fx-background-color: #e0e0e0;
                -fx-text-fill: #333;
                -fx-font-size: 14px;
                -fx-padding: 10 20;
                -fx-background-radius: 6;
            """));
            
            

            var result = alert.showAndWait();
 

            if (result.isEmpty() || result.get() == cancel) {
                return; // ❌ nicht überschreiben
            }

            // ✅ Entferne das alte Deck bei Zustimmung
            decks.removeIf(d ->
                d.getName().equalsIgnoreCase(deck.getName()) ||
                d.getSourceFileName().equalsIgnoreCase(deck.getSourceFileName())
            );
        }

        decks.add(deck); // neues Deck hinzufügen

        File saveFolder = new File("saves");
        if (!saveFolder.exists()) {
            saveFolder.mkdirs();
        }

        File file = new File(saveFolder, deckName + ".json");
        try (FileWriter writer = new FileWriter(file)) {
            // Gson gson = new Gson();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(deck, writer);
            DeckDisplayScreen.getInstance().refreshContent();
            NotificationIO.showInfo("Erfolg", "Deck wurde erfolgreich gespeichert!");
            // Eingaben zurücksetzen
            deckNameField.clear();
            cardListContainer.getChildren().clear();
            cardFields.clear();
            addCardRow();
        } catch (IOException ex) {
            NotificationIO.showWarning("Fehler", "Fehler beim Speichern: " + ex.getMessage());
        }
    }

    private void addCardRow() {
        TextField begriff = new TextField();
        begriff.setPromptText("Begriff");
        begriff.setPrefWidth(400);
        begriff.setStyle(StyleConstants.CONTENT_TEXTFIELD);

        TextField uebersetzung = new TextField();
        uebersetzung.setPromptText("Übersetzung");
        uebersetzung.setPrefWidth(400);
        uebersetzung.setStyle(StyleConstants.CONTENT_TEXTFIELD);

        FontAwesomeIconView plus_icon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
        FontAwesomeIconView minus_icon = new FontAwesomeIconView(FontAwesomeIcon.MINUS);

        Button actionBtn = new Button();
        actionBtn.setGraphic(plus_icon);
        actionBtn.setStyle(StyleConstants.CARD_ADD_BUTTON);

        HBox row = new HBox(12, begriff, uebersetzung, actionBtn);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle(StyleConstants.CARD_ROW);

        final boolean[] isPlus = {true};

        actionBtn.setOnMouseEntered(e -> {
            if (isPlus[0]) actionBtn.setStyle(StyleConstants.CARD_ADD_BUTTON_HOVER);
            else actionBtn.setStyle(StyleConstants.CARD_REMOVE_BUTTON_HOVER);
        });

        actionBtn.setOnMouseExited(e -> {
            if (isPlus[0]) actionBtn.setStyle(StyleConstants.CARD_ADD_BUTTON);
            else actionBtn.setStyle(StyleConstants.CARD_REMOVE_BUTTON);
        });

        actionBtn.setOnAction(e -> {
            if (isPlus[0]) {
                isPlus[0] = false;
                actionBtn.setGraphic(minus_icon);
                actionBtn.setStyle(StyleConstants.CARD_REMOVE_BUTTON);
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
        smoothScroll.setCycleCount(15); // 10 * 10ms = ~100ms Dauer
        smoothScroll.play();
    }
} 
