package project.util.settings;

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
     * Gibt an, ob die Kartenreihenfolge beim Start einer Lerneinheit automatisch gemischt werden soll.
     */
    private boolean shuffleOnSessionStart = true;

    /**
     * Gibt an, ob schwierigere Karten bei der Gewichtung bevorzugt behandelt werden sollen.
     */
    private boolean preferHardCards = false; 

    /**
     * Aktiviert das automatische Weiterschalten zur nächsten Karte nach einer festgelegten Zeit.
     */
    private boolean autoAdvanceEnabled = false;

    /**
     * Zeitintervall (in Sekunden), nach dem automatisch zur nächsten Karte gewechselt wird.
     */
    private int autoAdvanceSeconds = 5;

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

    /**
     * Gibt an, ob die Karten beim Start automatisch gemischt werden sollen.
     * @return true wenn Mischen aktiviert ist, sonst false
     */
    public boolean isShuffleOnSessionStart() {
        return shuffleOnSessionStart;
    }

    /**
     * Legt fest, ob beim Start automatisch gemischt werden soll.
     * @param shuffleOnSessionStart true aktiviert automatisches Mischen
     */
    public void setShuffleOnSessionStart(boolean shuffleOnSessionStart) {
        this.shuffleOnSessionStart = shuffleOnSessionStart;
    }

    /**
     * Gibt zurück, ob schwierige Karten bei der Abfrage bevorzugt angezeigt werden sollen.
     * @return true wenn schwierige Karten bevorzugt werden
     */
    public boolean isPreferHardCards() {
        return preferHardCards;
    }

    /**
     * Setzt die Präferenz für schwierige Karten.
     * @param prefer true wenn schwierige Karten bevorzugt angezeigt werden sollen
     */
    public void setPreferHardCards(boolean prefer) {
        this.preferHardCards = prefer;
    }

    /**
     * Gibt an, ob automatisch zur nächsten Karte gewechselt werden soll.
     * @return true wenn Auto-Advance aktiviert ist
     */
    public boolean isAutoAdvanceEnabled() { return autoAdvanceEnabled; }

    /**
     * Aktiviert oder deaktiviert das automatische Weiterschalten.
     * @param b true aktiviert das Feature
     */
    public void setAutoAdvanceEnabled(boolean b) { this.autoAdvanceEnabled = b; }

    /**
     * Gibt das Zeitintervall in Sekunden zurück, nach dem automatisch zur nächsten Karte gewechselt wird.
     * @return Anzahl der Sekunden
     */
    public int getAutoAdvanceSeconds() { return autoAdvanceSeconds; }
    
    /**
     * Legt das Zeitintervall für das automatische Weiterschalten fest.
     * @param s Zeit in Sekunden
     */
    public void setAutoAdvanceSeconds(int s) { this.autoAdvanceSeconds = s; }
}