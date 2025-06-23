package project;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Einstellungsbildschirm der Anwendung
 */
public class SettingsScreen extends BorderPane {

    private AppSettings appSettings;

    /** ComboBox für die Auswahl des Themes (Light/Dark Mode) */
    private static ComboBox<String> themeSelector;


    /**
     * Konstruktor für den Einstellungsbildschirm.
     *
     * @param deckManager Manager für die Deck-Verwaltung
     * @param deckDisplayScreen Bildschirm zur Anzeige von Decks
     */
    public SettingsScreen( DeckManager deckManager, DeckDisplayScreen deckDisplayScreen) {
        this.setStyle(StyleConstants.BACKGROUND_DEFAULT);
        this.setCenter(createSettingsScene());
    }

    /**
     * Erstellt das Layout für den Einstellungsbildschirm.
     *
     * @return VBox Container mit allen UI-Elementen der Einstellungen
     */
    private  VBox createSettingsScene() {
        appSettings = SettingsIO.loadSettings();

        // Root-Layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));

        // Title
        Label title = new Label("Einstellungen");
        title.setStyle(StyleConstants.LABEL_TITLE);

        // Light-/Dark-Mode Auswahl
        Label themeLabel = new Label("Light-/Dark-Mode");
        themeLabel.setStyle(StyleConstants.LABEL_SETTINGS);

        themeSelector = new ComboBox<>();
        themeSelector.getItems().addAll("Light-Mode", "Dark-Mode");
        themeSelector.setValue("Light-Mode");

        HBox themeBox = new HBox(495, themeLabel, themeSelector);
        themeBox.setPadding(new Insets(10));
        themeBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5;");
        themeBox.setPrefHeight(50);
        themeBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label toggleLabel = new Label("Beispiel-Decks beim Start automatisch laden:");

        CheckBox toggle = new CheckBox();
        toggle.setSelected(appSettings.isGenerateDefaultDecks());

        toggle.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            appSettings.setGenerateDefaultDecks(isSelected);
            SettingsIO.saveSettings(appSettings);
        });

        HBox toggleBox = new HBox(450, toggleLabel, toggle);
        toggleBox.setPadding(new Insets(10));
        toggleBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5;");
        toggleBox.setPrefHeight(50);
        toggleBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Alles zusammensetzen
        root.getChildren().addAll(title, themeBox, toggleBox);

        return root;
    }

}
