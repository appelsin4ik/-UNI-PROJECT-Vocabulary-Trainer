package project.control;

import project.MainLayout;
import project.items.DeckSidebar;
import project.screens.DeckCreationScreen;
import project.screens.DeckDisplayScreen;
import project.screens.DeckManagementScreen;
import project.screens.SettingsScreen;

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
     * Wechselt zur Settings-Anzeige.
     */
    public static void showSettingsScreen() {
        SettingsScreen settingView = new SettingsScreen(DeckManager.getInstance(),DeckDisplayScreen.getInstance());
        MainLayout.getInstance().showContent(settingView);
        getInstance().updateButton(getInstance().getSettingsButton());
    }

    /**
     * Wechselt zur Deck-Anzeige.
     */
    public static void showDeckScreen() {
        DeckDisplayScreen deckView = new DeckDisplayScreen(DeckManager.getInstance());
        MainLayout.getInstance().showContent(deckView);
        getInstance().updateButton(getInstance().getDecksButton());
    }

    /**
     * Wechselt zur Deck-Creation-Anzeige.
     */
    public static void showCreationScreen() {
        DeckCreationScreen creatorView = new DeckCreationScreen();
        MainLayout.getInstance().showContent(creatorView);
        getInstance().updateButton(getInstance().getCardsButton());
    }

    /**
     * Wechselt zur Deck-Creation-Anzeige.
     */
    public static void showManagementScreen() {
        DeckManagementScreen managementView = new DeckManagementScreen();
        MainLayout.getInstance().showContent(managementView);
        getInstance().updateButton(getInstance().getManagementButton());
    }
}
