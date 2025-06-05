package project;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
        CARDS_ICON(FontAwesomeIcon.STICKY_NOTE);

        private final FontAwesomeIcon icon;

        Icons(FontAwesomeIcon icon) {
            this.icon = icon;
        }

        /**
         * Erstellt eine neue FontAwesomeIconView mit standardisierten Eigenschaften.
         * @return Eine konfigurierte FontAwesomeIconView-Instanz
         */
        public FontAwesomeIconView getIconView() {
            FontAwesomeIconView view = new FontAwesomeIconView(icon);
            view.setSize("25px");
            view.setFill(Color.WHITE);
            return view;
        }
    }

    /**
     * Die Hauptcontainer-VBox für die Seitenleiste.
     * Enthält alle UI-Elemente wie Titel und Navigationsbuttons.
     */
    private VBox sidebar;

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
     * Speichert den aktuell ausgewählten/gedrückten Button.
     * Wird verwendet, um den aktiven Navigationszustand zu verfolgen.
     */
    private Button pressed;

    /**
     * Konstruktor für die Seitenleiste. Initialisiert und konfiguriert alle UI-Elemente
     * einschließlich der Navigationsbuttons und des Layouts.
     */
    public DeckSidebar() {
        System.out.println("DeckSidebar");

        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20, 0, 0, 0));
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setMinWidth(200);
        sidebar.setPrefWidth(200);

        // Sidebar title
        Label sidebarTitle = new Label("P e n t a l i n g o");
        sidebarTitle.setFont(Font.font("K2D"));
        sidebarTitle.setStyle("-fx-text-fill: white; -fx-font-size: 25px; -fx-font-weight: bold;");
        sidebarTitle.setPadding(new Insets(0, 0, 20, 20));


        cardsButton = createNavButton( "Karten", Icons.CARDS_ICON.getIconView());
        decksButton = createNavButton("Decks", Icons.BARS_ICON.getIconView());
        managementButton = createNavButton("Karten-Verwaltung", Icons.TABLE_ICON.getIconView());
        settingsButton = createNavButton("Einstellungen", Icons.SLIDERS_ICON.getIconView());


        // Add all elements to sidebar
        sidebar.getChildren().addAll(
                sidebarTitle,
                cardsButton,
                decksButton,
                managementButton,
                settingsButton
        );

        buttons.addAll(childrenToButtons(sidebar.getChildren()));


        this.sidebar = sidebar;
    }

    /**
     * gibt die Sidebar zurück
     * @return Sidebar
     */
    public VBox showSidebar() {
        return sidebar;
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
        button.setStyle(defaultStyle());

        button.setOnMouseEntered(e -> {
            if (button.getStyle().equals(defaultStyle())) {
                button.setStyle(hoveredStyle());
            }
        });

        button.setOnMouseExited(e -> {
            if (button.getStyle().equals(hoveredStyle())) {
                button.setStyle(defaultStyle());
            }
        });

        button.setOnAction(e ->updateButton(button));

        return button;
    }

    /**
     * Konvertiert eine ObservableList von Nodes in eine Liste von Buttons.
     * @param children Die Liste der Kindknoten
     * @return Eine Liste, die nur die Button-Elemente enthält
     */
    private List<Button> childrenToButtons(ObservableList<Node> children) {
        List<Button> buttons = new ArrayList<>();
        for (Node child : children) {
            if (child instanceof Button) {
                buttons.add((Button) child);
            }
        }
        return buttons;
    }

    /**
     * Aktualisiert den Stil des ausgewählten Buttons und setzt alle anderen zurück.
     * @param selectedButton Der Button, der als ausgewählt markiert werden soll
     */
    public void updateButton(Button selectedButton) {
        for (Button btn : buttons) {
            btn.setStyle(defaultStyle());  // reset all
        }
        selectedButton.setStyle(selectedStyle());// highlight selected
        pressed = selectedButton;
    }

    /**
     * Gibt den Standard-Stil für einen nicht ausgewählten Button zurück.
     * @return CSS-Stil als String
     */
    private String defaultStyle() {
        return "-fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-color: transparent; -fx-padding: 10px;";
    }

    /**
     * Gibt den Standard-Stil für einen ausgewählten Button zurück.
     * @return CSS-Stil als String
     */
    private String selectedStyle() {
        return "-fx-text-fill: white; -fx-font-size: 16px; " +
                    "-fx-background-color: #1e2c3b; -fx-padding: 10px;";
    }

    /**
     * Gibt den Standard-Stil für einen Button über dem die Maus hovert zurück.
     * @return CSS-Stil als String
     */
    private String hoveredStyle() {
        return "-fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-color: #34495e; -fx-padding: 10px;";
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
     * Gibt den zuletzt gedrückten Button zurück.
     * @return Der aktuell ausgewählte Button
     */
    public Button getPressed() {
        return pressed;
    }
}
