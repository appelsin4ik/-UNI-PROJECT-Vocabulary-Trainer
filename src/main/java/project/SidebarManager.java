package project;

public class SidebarManager {
    private static DeckSidebar instance;

    public static DeckSidebar getInstance() {
        if (instance == null) {
            instance = new DeckSidebar();
        }
        return instance;
    }
}
