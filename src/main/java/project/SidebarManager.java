package project;

import javafx.scene.control.Alert;
/**
 * Manager für die Sidebar
 */
public class SidebarManager {
    /** globale Instanz der DeckSidebar */
    private static DeckSidebar instance;

    /**
     * globale Instanz erhalten, wenn nötig Lazy Initialisierung
     * @return DeckSidebar
     */
    public static DeckSidebar getInstance() {
        if (instance == null) {
            instance = new DeckSidebar();
        }
        return instance;
    }

    /**
     * Zeigt eine Warnung für nicht implementierte Funktionen an.
     */
    public static void showWarning() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Das Menu ist noch in Arbeit");
        alert.setContentText("Wartet auf updates :=)");
        alert.showAndWait();
    }

    /**
     * Wechselt zur Settings-Anzeige.
     */
    public static void showSettingsScreen() {
        SettingsScreen settingView = new SettingsScreen(DeckManager.getInstance(),DeckDisplayScreen.getInstance());
        settingView.show();
    }

    /**
     * Wechselt zur Deck-Anzeige.
     */
    public static void showDeckScreen() {
        DeckDisplayScreen deckView = new DeckDisplayScreen(DeckManager.getInstance());

        deckView.show();
    }

    /**
     * Wechselt zur Deck-Creation-Anzeige.
     */
    public static void showCreationScreen() {
        DeckCreationScreen creatorView = new DeckCreationScreen();
        creatorView.show();
    }
}
