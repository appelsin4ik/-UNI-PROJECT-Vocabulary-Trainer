package project.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.util.Duration;
import project.control.DeckManager;
import project.util.io.SettingsIO;
import project.util.settings.AppSettings;
import project.util.style.StyleConstants;

/**
 * Einstellungsbildschirm der Anwendung, gegliedert nach Themenbereichen.
 */
public class SettingsScreen extends BorderPane {

    private AppSettings appSettings;

    public SettingsScreen(DeckManager deckManager, DeckDisplayScreen deckDisplayScreen) {
        this.setStyle(StyleConstants.BACKGROUND_DEFAULT);
        this.setCenter(createMainContent());
    }
    /** Settings-Menu anzeigen */
    private VBox createMainContent() {
        appSettings = SettingsIO.loadSettings();

        VBox root = new VBox(30);
        root.setPadding(new Insets(20));

        Label title = new Label("Einstellungen");
        title.setStyle(StyleConstants.LABEL_TITLE);

        VBox generalSection = createGeneralSettingsSection();
        VBox cardSection = createCardSettingsSection();

        root.getChildren().addAll(title, generalSection, cardSection);
        return root;
    }

    /** Allgemeine Einstellungen (z. B. Beispieldecks laden) */
    private VBox createGeneralSettingsSection() {
        Label sectionTitle = new Label("Allgemein");
        sectionTitle.setStyle(StyleConstants.LABEL_SUBTITLE_BOLD);

        // DEFAULT
        Label toggleLabel = new Label("Default-Decks laden");
        toggleLabel.setStyle(StyleConstants.LABEL_SETTINGS);

        CheckBox toggle = new CheckBox();
        toggle.setSelected(appSettings.isGenerateDefaultDecks());
        toggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            appSettings.setGenerateDefaultDecks(newVal);
            SettingsIO.saveSettings(appSettings);
        });

        Tooltip tipToggle = new Tooltip("Wenn aktiviert, werden beim Start automatisch Beispiel-Decks geladen.");
        tipToggle.setStyle(StyleConstants.TOOLTIP);
        tipToggle.setWrapText(true);
        tipToggle.setMaxWidth(220);
        tipToggle.setShowDelay(Duration.millis(1));
        Tooltip.install(toggle, tipToggle);

        HBox toggleBox = new HBox(590, toggleLabel, toggle);
        toggleBox.setAlignment(Pos.CENTER_LEFT);
        toggleBox.setPadding(new Insets(10));
        toggleBox.setStyle(StyleConstants.SETTINGS_CONTAINER);
        toggleBox.setPrefHeight(50);

        VBox section = new VBox(10, sectionTitle, toggleBox);
        return section;
    }

    /** Karteneinstellungen (z. B. Mischverhalten) */
    private VBox createCardSettingsSection() {

        Label sectionTitle = new Label("Karteneinstellungen");
        sectionTitle.setStyle(StyleConstants.LABEL_SUBTITLE_BOLD);

        // AUTOMATISCH-MISCHEN:
        Label shuffleLabel = new Label("Automatisch mischen");
        shuffleLabel.setStyle(StyleConstants.LABEL_SETTINGS);

        CheckBox shuffleToggle = new CheckBox();
        shuffleToggle.setSelected(appSettings.isShuffleOnSessionStart());

        Tooltip tipShuffle = new Tooltip("Wenn aktiviert, wird die Kartenreihenfolge bei jedem Start zufällig bestimmt.");
        tipShuffle.setStyle(StyleConstants.TOOLTIP);
        tipShuffle.setWrapText(true);
        tipShuffle.setMaxWidth(220);
        tipShuffle.setShowDelay(Duration.millis(1));
        Tooltip.install(shuffleToggle, tipShuffle);

        shuffleToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            appSettings.setShuffleOnSessionStart(newVal);
            SettingsIO.saveSettings(appSettings);
        });

        HBox shuffleBox = new HBox(580, shuffleLabel, shuffleToggle);
        shuffleBox.setAlignment(Pos.CENTER_LEFT);
        shuffleBox.setPadding(new Insets(10));
        shuffleBox.setStyle(StyleConstants.SETTINGS_CONTAINER);
        shuffleBox.setPrefHeight(50);

        // PREFERIERUNG:

        Label hardPrefLabel = new Label("Schwierige Karten bevorzugen");
        hardPrefLabel.setStyle(StyleConstants.LABEL_SETTINGS);

        CheckBox hardPrefToggle = new CheckBox();
        hardPrefToggle.setSelected(appSettings.isPreferHardCards());

        Tooltip tipHardPref = new Tooltip("Wenn aktiviert, erscheinen schwierige Karten häufiger bei der Abfrage.");
        tipHardPref.setStyle(StyleConstants.TOOLTIP);
        tipHardPref.setWrapText(true);
        tipHardPref.setMaxWidth(220);
        tipHardPref.setShowDelay(Duration.millis(1));
        Tooltip.install(hardPrefToggle, tipHardPref);

        hardPrefToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            appSettings.setPreferHardCards(newVal);
            SettingsIO.saveSettings(appSettings);
        });

        HBox hardPrefBox = new HBox(530, hardPrefLabel, hardPrefToggle);
        hardPrefBox.setAlignment(Pos.CENTER_LEFT);
        hardPrefBox.setPadding(new Insets(10));
        hardPrefBox.setStyle(StyleConstants.SETTINGS_CONTAINER);
        hardPrefBox.setPrefHeight(50);

        // AUTO

        Label autoAdvanceLabel = new Label("Automatisch zur nächsten Karte:");
        autoAdvanceLabel.setStyle(StyleConstants.LABEL_SETTINGS);

        CheckBox autoAdvanceToggle = new CheckBox();
        autoAdvanceToggle.setSelected(appSettings.isAutoAdvanceEnabled());

        Tooltip tipAutoAdvance = new Tooltip("Wenn aktiviert, wird automatisch zur nächsten Karte gewechselt – nach Ablauf der eingestellten Zeit in Sekunden.");
        tipAutoAdvance.setStyle(StyleConstants.TOOLTIP);
        tipAutoAdvance.setWrapText(true);
        tipAutoAdvance.setMaxWidth(220);
        tipAutoAdvance.setShowDelay(Duration.millis(1));
        Tooltip.install(autoAdvanceToggle, tipAutoAdvance);

        Spinner<Integer> secondsSpinner = new Spinner<>(1, 30, appSettings.getAutoAdvanceSeconds());
        secondsSpinner.setEditable(true);
        secondsSpinner.setDisable(!appSettings.isAutoAdvanceEnabled());

        autoAdvanceToggle.selectedProperty().addListener((obs, o, n) -> {
            appSettings.setAutoAdvanceEnabled(n);
            secondsSpinner.setDisable(!n);
            SettingsIO.saveSettings(appSettings);
        });

        secondsSpinner.valueProperty().addListener((obs, o, n) -> {
            appSettings.setAutoAdvanceSeconds(n);
            SettingsIO.saveSettings(appSettings);
        });

        HBox output = new HBox(20,autoAdvanceToggle, new Label("alle"), secondsSpinner, new Label("Sek."));
        output.setAlignment(Pos.CENTER_LEFT);
        output.setStyle(StyleConstants.TRANSPARENT);

        HBox autoBox = new HBox(250, autoAdvanceLabel, output);
        autoBox.setAlignment(Pos.CENTER_LEFT);
        autoBox.setPadding(new Insets(10));
        autoBox.setStyle(StyleConstants.SETTINGS_CONTAINER);
        autoBox.setPrefHeight(50);

        // Objekte initialisieren:
        VBox section = new VBox(10, sectionTitle, shuffleBox, hardPrefBox,autoBox);
        return section;
    }
}
