package project.items;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.control.SidebarManager;
import project.screens.AboutDialog;
import project.util.style.StyleConstants;

import java.util.*;

/**
 * Repräsentiert die Seitenleiste der App
 * mit navigierbarer Seitenleiste mit Buttons zum Wechseln zwischen den Screens.
 */
public class DeckSidebar extends VBox {
    /**
     * Enumeration für die verschiedenen Icons, die in der Seitenleiste verwendet werden.
     * Jedes Icon wird mit einem entsprechenden FontAwesome-Icon initialisiert.
     */
    public enum Icons{
        TABLE_ICON(FontAwesomeIcon.TABLE),
        BARS_ICON(FontAwesomeIcon.BARS),
        SLIDERS_ICON(FontAwesomeIcon.SLIDERS),
        CARDS_ICON(FontAwesomeIcon.STICKY_NOTE),
        INFO_CIRCLE(FontAwesomeIcon.INFO_CIRCLE);

        private final FontAwesomeIcon icon;

        Icons(FontAwesomeIcon icon) {
            this.icon = icon;
        }

        /**
         * Erstellt eine neue FontAwesomeIconView mit standardisierten Eigenschaften.
         * @return Eine konfigurierte FontAwesomeIconView-Instanz
         */
        private FontAwesomeIconView getIconView() {
            FontAwesomeIconView view = new FontAwesomeIconView(icon);
            view.setSize("25px");
            view.setFill(Color.WHITE);
            return view;
        }
    }

    /**
     * Liste aller Navigationsbuttons in der Seitenleiste.
     * Wird verwendet, um den Zustand aller Buttons gemeinsam zu verwalten.
     */
    private final List<Button> buttons = new ArrayList<>();

    //Navigation buttons
    
    /**
     * Button für den Zugriff auf die Karten-Ansicht.
     * Ermöglicht die Navigation zur Übersicht aller Karten.
     */
    private Button cardsButton;
    /**
     * Button für den Zugriff auf die Decks-Ansicht.
     * Ermöglicht die Navigation zur Übersicht aller Kartendecks.
     */
    private Button decksButton;
    /**
     * Button für den Zugriff auf die Verwaltungsansicht.
     * Ermöglicht die Navigation zu den Karten-Verwaltungsfunktionen.
     */
    private Button managementButton;
    /**
     * Button für den Zugriff auf die Einstellungen.
     * Ermöglicht die Navigation zu den Anwendungseinstellungen.
     */
    private Button settingsButton;
    /**
     * Button für das Öffnen des About Dialogs.
     * Ermöglicht die Navigation zu den Anwendungseinstellungen.
     */
    private Button aboutButton;

    /**
     * Speichert den aktuell ausgewählten/gedrückten Button.
     * Wird verwendet, um den aktiven Navigationszustand zu verfolgen.
     */
    private Button pressed;

    /**
     * Konstruktor für die Seitenleiste. Initialisiert und konfiguriert alle UI-Elemente
     * einschließlich der Navigationsbuttons und des Layouts.
     */
    public DeckSidebar() {
        super(10);
        setPadding(new Insets(20, 0, 0, 0));
        setStyle("-fx-background-color: #2c3e50;");
        setMinWidth(200);
        setPrefWidth(200);

        // Sidebar title
        Label sidebarTitle = new Label("P e n t a l i n g o");
        sidebarTitle.setFont(Font.font("K2D"));
        sidebarTitle.setStyle(StyleConstants.LABEL_SIDEBAR);
        sidebarTitle.setAlignment(Pos.CENTER);
        sidebarTitle.setPadding(new Insets(0, 0, 20, 20));

        cardsButton = createNavButton( "Karten", Icons.CARDS_ICON.getIconView());
        decksButton = createNavButton("Decks", Icons.BARS_ICON.getIconView());
        managementButton = createNavButton("Karten-Verwaltung", Icons.TABLE_ICON.getIconView());
        settingsButton = createNavButton("Einstellungen", Icons.SLIDERS_ICON.getIconView());
        aboutButton = createNavButton("About", Icons.INFO_CIRCLE.getIconView());
        aboutButton.setOnAction(e -> AboutDialog.show());

        cardsButton.setOnAction(e -> SidebarManager.showCreationScreen());
        decksButton.setOnAction(e -> SidebarManager.showDeckScreen());
        managementButton.setOnAction(e -> SidebarManager.showManagementScreen());
        settingsButton.setOnAction(e -> SidebarManager.showSettingsScreen());
        aboutButton.setOnAction(e -> AboutDialog.show());

        // Add all elements to sidebar
        getChildren().addAll(
                sidebarTitle,
                cardsButton,
                decksButton,
                managementButton,
                settingsButton,
                aboutButton
        );
    }

    /**
     * gibt die Sidebar zurück
     * @return Sidebar
     */
    public VBox showSidebar() {
        return this;
    }

    /**
     * Erstellt die einzelnen Elemente der Sidebar (also z.B. Einstellungen, Karten etc.)
     * @param text Der anzuzeigende Text des Buttons
     * @param icon Das FontAwesome-Icon für den Button
     * @return Ein konfigurierter Button mit Hover- und Klick-Effekten
     */
    private Button createNavButton(String text, FontAwesomeIconView icon) {
        Button button = new Button("  " + text, icon);

        button.setPadding(new Insets(0, 0, 0, 0));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.BASELINE_LEFT);
        button.setStyle(StyleConstants.BUTTON_SIDEBAR_DEFAULT);

        button.setOnMouseEntered(e -> {
            if (button.getStyle().equals(StyleConstants.BUTTON_SIDEBAR_DEFAULT)) {
                button.setStyle(StyleConstants.BUTTON_SIDEBAR_HOVER);
            }
        });

        button.setOnMouseExited(e -> {
            if (button.getStyle().equals(StyleConstants.BUTTON_SIDEBAR_HOVER)) {
                button.setStyle(StyleConstants.BUTTON_SIDEBAR_DEFAULT);
            }
        });

        button.setOnAction(e ->updateButton(button));

        return button;
    }

    /**
     * Aktualisiert den Stil des ausgewählten Buttons und setzt alle anderen zurück.
     * @param selectedButton Der Button, der als ausgewählt markiert werden soll
     */
    public void updateButton(Button selectedButton) {
        if (pressed != null && pressed != selectedButton) {
            pressed.setStyle(StyleConstants.BUTTON_SIDEBAR_DEFAULT);
        }

        selectedButton.setStyle(StyleConstants.BUTTON_SIDEBAR_SELECTED);
        pressed = selectedButton;
    }

    /**
     * Gibt den Karten-Button zurück.
     * @return Button für die Karten-Ansicht
     */
    public Button getCardsButton() {
        return cardsButton;
    }

    /**
     * Gibt den Decks-Button zurück.
     * @return Button für die Decks-Ansicht
     */
    public Button getDecksButton() {
        return decksButton;
    }

    /**
     * Gibt den Einstellungen-Button zurück.
     * @return Button für die Einstellungen
     */
    public Button getSettingsButton() {
        return settingsButton;
    }

    /**
     * Gibt den Verwaltungs-Button zurück.
     * @return Button für die Karten-Verwaltung
     */
    public Button getManagementButton() {
        return managementButton;
    }

    /**
     * Gibt die Liste aller Navigations-Buttons zurück.
     * @return Liste aller Buttons in der Seitenleiste
     */
    public List<Button> getButtons() {
        return buttons;
    }

    /**
     * Gibt den About-Button zurück.
     * @return Button für den About-Dialog
     */
    public Button getAboutButton() {
        return aboutButton;
    }

    /**
     * Gibt den zuletzt gedrückten Button zurück.
     * @return Der aktuell ausgewählte Button
     */
    public Button getPressed() {
        return pressed;
    }

    public DeckSidebar getSidebar() {
        return this;
    }
}
