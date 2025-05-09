package project;

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
}
