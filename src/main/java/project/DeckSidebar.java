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
import javafx.stage.Stage;

import java.util.*;

public class DeckSidebar extends VBox{

    private VBox sidebar;

    private final List<Button> buttons = new ArrayList<>();

    //Navigation buttons
    private Button cardsButton ;
    private Button decksButton ;
    private Button managementButton ;
    private Button settingsButton ;

    private Button pressed;

    public enum Icons{
        TABLE_ICON(FontAwesomeIcon.TABLE),
        BARS_ICON(FontAwesomeIcon.BARS),
        SLIDERS_ICON(FontAwesomeIcon.SLIDERS),
        CARDS_ICON(FontAwesomeIcon.STICKY_NOTE);

        private final FontAwesomeIcon icon;

        Icons(FontAwesomeIcon icon) {
            this.icon = icon;
        }

        public FontAwesomeIconView getIconView() {
            FontAwesomeIconView view = new FontAwesomeIconView(icon);
            view.setSize("25px");
            view.setFill(Color.WHITE);
            return view;
        }
    }

    public DeckSidebar() {
        System.out.println("DeckSidebar");

        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20, 0, 0, 0));
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setMinWidth(200);
        sidebar.setPrefWidth(200);

        // Sidebar title
        Label sidebarTitle = new Label("Navigation");
        sidebarTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
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

    public VBox showSidebar() {
        return sidebar;
    }

    //Erstellt die einzelnen Elemente der Sidebar (also z.B. Einstellungen, Karten etc.)
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

    private List<Button> childrenToButtons(ObservableList<Node> children) {
        List<Button> buttons = new ArrayList<>();
        for (Node child : children) {
            if (child instanceof Button) {
                buttons.add((Button) child);
            }
        }
        return buttons;
    }

    public void updateButton(Button selectedButton) {
        for (Button btn : buttons) {
            btn.setStyle(defaultStyle());  // reset all
        }
        selectedButton.setStyle(selectedStyle());// highlight selected
        pressed = selectedButton;
    }

    private String defaultStyle() {
        return "-fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-color: transparent; -fx-padding: 10px;";
    }

    private String selectedStyle() {
        return "-fx-text-fill: white; -fx-font-size: 16px; " +
                    "-fx-background-color: #1e2c3b; -fx-padding: 10px;";
    }

    private String hoveredStyle() {
        return "-fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-color: #34495e; -fx-padding: 10px;";
    }

    public Button getCardsButton() {
        return cardsButton;
    }

    public Button getDecksButton() {
        return decksButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getManagementButton() {
        return managementButton;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public Button getPressed() {
        return pressed;
    }
}
