package project;

import com.google.gson.Gson;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeckCreationScreen extends BorderPane{
    private VBox createCollection;
    private Scene scene;
    private TextField deckNameField;
    private VBox cardListContainer;
    private List<Pair<TextField, TextField>> cardFields;

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
                        SidebarManager.showWarning();
                        break;
                    // NEED to be DONE
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

        this.setCenter(createScene());

        scene = new Scene(this,1000,700);
    }

    public VBox createScene() {
        createCollection = new VBox(10);
        createCollection.setPadding(new Insets(20));

        Label title = new Label("Neues Deck erstellen");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        deckNameField = new TextField();
        deckNameField.setPromptText("Deck-Name eingeben");

        cardListContainer = new VBox(5);
        cardFields = new ArrayList<>();
        addCardInputRow();

        Button addCardButton = new Button("+ Weitere Karte hinzufügen");
        addCardButton.setOnAction(e -> addCardInputRow());

        Button saveDeckButton = new Button("Deck speichern und exportieren");
        saveDeckButton.setOnAction(e -> saveDeckToFile(Main.getStage()));

        createCollection.getChildren().addAll(title, deckNameField, cardListContainer, addCardButton, saveDeckButton);

        return createCollection;
    }

    public void show() {Main.getStage().setScene(scene);}

    private void addCardInputRow() {
        HBox row = new HBox(5);
        TextField termField = new TextField();
        termField.setPromptText("Begriff");
        termField.setPrefWidth(400);

        TextField translationField = new TextField();
        translationField.setPromptText("Übersetzung");
        translationField.setPrefWidth(400);

        row.getChildren().addAll(termField, translationField);
        cardListContainer.getChildren().add(row);
        cardFields.add(new Pair<>(termField, translationField));
    }

    private void saveDeckToFile(Stage stage) {

        String deckName = deckNameField.getText().trim();
        if (deckName.isEmpty()) {
            showAlert("Fehler", "Bitte einen Deck-Namen eingeben.");
            return;
        }

        List<project.Card> cards = new ArrayList<>();
        for (Pair<TextField, TextField> pair : cardFields) {
            String term = pair.getKey().getText().trim();
            String translation = pair.getValue().getText().trim();
            if (!term.isEmpty() && !translation.isEmpty()) {
                cards.add(new project.Card(term, translation));
            }
        }

        if (cards.isEmpty()) {
            showAlert("Fehler", "Bitte mindestens eine Karte mit Begriff und Übersetzung eingeben.");
            return;
        }

        Deck deck = new Deck(deckName, cards);
        DeckManager.getInstance().addDeck(deck);

        File saveFolder = new File("saves");
        if (!saveFolder.exists()) {
            saveFolder.mkdirs();
        }

        File file = new File(saveFolder, deckName + ".json");
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new Gson();
            gson.toJson(deck, writer);
            DeckDisplayScreen.getInstance().updateDecks();
            showAlert("Erfolg", "Deck wurde erfolgreich gespeichert!");
        } catch (IOException ex) {
            showAlert("Fehler", "Fehler beim Speichern: " + ex.getMessage());
        }

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
