package project;

/**
 * Diese Klasse verwaltet die Anwendungseinstellungen.
 * Sie speichert verschiedene Konfigurationsoptionen für die Anwendung.
 */
public class AppSettings {
    /**
     * Steuert, ob Standard-Decks beim Start generiert werden sollen.
     * Standardmäßig auf 'true' gesetzt.
     */
    private boolean generateDefaultDecks = true;

    /**
     * Gibt zurück, ob Standard-Decks generiert werden sollen.
     * @return true wenn Standard-Decks generiert werden sollen, false wenn nicht
     */
    public boolean isGenerateDefaultDecks() {
        return generateDefaultDecks;
    }

    /**
     * Setzt die Option zum Generieren von Standard-Decks.
     * @param generateDefaultDecks true um Standard-Decks zu generieren,
     * false um die Generierung zu deaktivieren
     */

    public void setGenerateDefaultDecks(boolean generateDefaultDecks) {
        this.generateDefaultDecks = generateDefaultDecks;
    }
}