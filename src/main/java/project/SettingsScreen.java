package project;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Einstellungsbildschirm der Anwendung
 */
public class SettingsScreen extends BorderPane {
    /** Manager für die Verwaltung von Kartendecks */
    private DeckManager deckManager;
    /** Bildschirm zur Anzeige von Decks */
    private DeckDisplayScreen deckDisplayScreen;
    /** Hauptszene des Einstellungsbildschirms */
    private Scene scene;

    private AppSettings appSettings;

    /** ComboBox für die Auswahl des Themes (Light/Dark Mode) */
    private static ComboBox<String> themeSelector;
    /** Button zur Dateiauswahl für den Import */
    private  Button fileSelectButton;
    /** Label für den Import-Bereich */
    private  Label importLabel;

    /**
     * Konstruktor für den Einstellungsbildschirm.
     *
     * @param deckManager Manager für die Deck-Verwaltung
     * @param deckDisplayScreen Bildschirm zur Anzeige von Decks
     */
    public SettingsScreen( DeckManager deckManager, DeckDisplayScreen deckDisplayScreen) {
        this.deckManager = deckManager;
        this.deckDisplayScreen = deckDisplayScreen;

        var sidebarManager = SidebarManager.getInstance();
        //Add Sidebar
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
                        SidebarManager.showWarning();
                        break;
                    // NEED to be DONE
                    case "Decks":
                        sidebarManager.updateButton(sidebarManager.getDecksButton());
                        SidebarManager.showDeckScreen();
                        break;

                    default:
                        break;
                }
            });

        }

        this.setCenter(createSettingsScene());

        scene = new Scene(this, 1000, 700);
    }

    /**
     * Zeigt den Einstellungsbildschirm im Hauptfenster an.
     */
    public void show() {
        Main.getStage().setScene(scene);
    }

    /**
     * Erstellt das Layout für den Einstellungsbildschirm.
     *
     * @return VBox Container mit allen UI-Elementen der Einstellungen
     */
    public VBox createSettingsScene() {
        appSettings = SettingsIO.loadSettings();

        // Root-Layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f9f9f9;");

        // Title
        Label title = new Label("Einstellungen");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Light-/Dark-Mode Auswahl
        Label themeLabel = new Label("Light-/Dark-Mode");
        themeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        themeSelector = new ComboBox<>();
        themeSelector.getItems().addAll("Light-Mode", "Dark-Mode");
        themeSelector.setValue("Light-Mode");

        HBox themeBox = new HBox(495, themeLabel, themeSelector);
        themeBox.setPadding(new Insets(10));
        themeBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5;");
        themeBox.setPrefHeight(50);
        themeBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Datei-Import Bereich
        importLabel = new Label("Decks importieren");
        importLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        fileSelectButton = new Button("Datei auswählen (.json)");
        fileSelectButton.setOnAction(e -> chooseFile());

        HBox importBox = new HBox(465, importLabel, fileSelectButton);
        importBox.setPadding(new Insets(10));
        importBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5;");
        importBox.setPrefHeight(50);
        importBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

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

        var aboutButton = new Button("About");
        aboutButton.setOnAction(e -> AboutDialog.show());

        // Alles zusammensetzen
        root.getChildren().addAll(title, themeBox, importBox, toggleBox, aboutButton);

        return root;
    }

    /**
     * Öffnet einen FileChooser-Dialog zur Auswahl einer JSON-Datei für den Deck-Import.
     */
    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("JSON-Datei auswählen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Dateien", "*.json"));

        File selectedFile = fileChooser.showOpenDialog(Main.getStage());
        if (selectedFile != null) {
            System.out.println("Ausgewählte Datei: " + selectedFile.getAbsolutePath());
        }
    }
}
