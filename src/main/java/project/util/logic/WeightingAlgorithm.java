package project.util.logic;

import java.util.ArrayList;
import java.util.List;

import project.items.Card;
import project.util.io.SettingsIO;

/**
 * Wiege algorithmus um die Karten anzuzeigen
 */
public class WeightingAlgorithm {
    private final List<Card> cards;
    private Card lastCard = null;

    public WeightingAlgorithm(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Liefert die nächste Karte auf Basis eines gewichteten Zufallsprinzips.
     * 
     * Die Gewichtung erfolgt durch mehrfaches Einfügen der Karte in eine temporäre Liste:
     * 
     * Mit HardPref-Setting:
     * - Gewicht 1 → 1x in der Liste
     * - Gewicht 2 → 3x in der Liste
     * - Gewicht 3 → 6x in der Liste
     * 
     * Ohne HardPref-Setting:
     * - Gewicht 1 → 3x in der Liste
     * - Gewicht 2 → 2x in der Liste
     * - Gewicht 3 → 1x in der Liste
     * 
     * Dadurch steigt die Wahrscheinlichkeit, dass schwerere Karten (z.B. Gewicht 3) öfter
     * gezogen werden. Die Methode wählt anschließend zufällig eine Karte aus dieser
     * gewichteten Liste.
     *
     * @return Eine zufällige Karte entsprechend ihrer Gewichtung oder null, wenn keine vorhanden.
     */
    public Card getNextCard() {
        List<Card> weightedList = new ArrayList<>();

        // Erstelle eine gewichtete Liste aller Karten entsprechend ihrer Schwierigkeit
        for (Card c : cards) {
            int repetitions;
            if (SettingsIO.loadSettings().isPreferHardCards()) {

                // Wenn schwere Karten bevorzugt werden
                repetitions = switch (c.weight) {
                    case 1 -> 1;
                    case 2 -> 3;
                    case 3 -> 6;
                    default -> 1;
                };
            } else {
                // Standardgewichtung: leichte Karten öfter anzeigen
                repetitions = switch (c.weight) {
                    case 1 -> 3;
                    case 2 -> 2;
                    case 3 -> 1;
                    default -> 1;
                };
            }

            // Füge die Karte so oft zur Liste hinzu, wie durch das Gewicht bestimmt
            for (int i = 0; i < repetitions; i++) weightedList.add(c);
        }

        // Wenn keine Karten verfügbar sind, gib null zurück
        if (weightedList.isEmpty()) return null;

        
        Card nextCard;

        // Begrenze die Anzahl der Wiederholungsversuche
        int maxAttempts = 10;

        int attempts = 0;

        // Versuche, eine andere Karte als die zuletzt angezeigte zu ziehen
        do {
            nextCard = weightedList.get((int)(Math.random() * weightedList.size()));
            attempts++;

            // Debug-Ausgabe:
            if (nextCard == lastCard) {
                System.out.println("[SKIP] Gleiche Karte erneut gezogen (Index: " + cards.indexOf(nextCard) + "). Versuche: " + attempts);
            }

        } while (nextCard == lastCard && attempts < maxAttempts);

        lastCard = nextCard;

        return nextCard;

    }
    
    public Card getNextCardRaw() {
        List<Card> weightedList = new ArrayList<>();
        for (Card c : cards) {
            int repetitions = switch (c.weight) {
                case 1 -> 1;
                case 2 -> 3;
                case 3 -> 6;
                default -> 1;
            };
            for (int i = 0; i < repetitions; i++) weightedList.add(c);
        }
        return weightedList.isEmpty() ? null :
            weightedList.get((int)(Math.random() * weightedList.size()));
    }

}

