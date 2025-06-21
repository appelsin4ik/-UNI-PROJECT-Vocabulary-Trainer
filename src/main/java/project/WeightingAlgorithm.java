package project;

import java.util.ArrayList;

/**
 * Wiege algorithmus um die Karten anzuzeigen
 */
public class WeightingAlgorithm {
    /** list aller Karten, die verarbeitet werden */
    private ArrayList<Card> cards;

    /**
     * neue Instanz des Algorithmus erstellen
     * @param cards Karten die verarbeitet werden sollen
     */
    public WeightingAlgorithm(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * Karten Sortieren
     */
    public void Sort() {
        cards.sort(Card::compareTo);
    }
}
