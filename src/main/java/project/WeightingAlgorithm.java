package project;

import java.util.ArrayList;
import java.util.List;

/**
 * Wiege algorithmus um die Karten anzuzeigen
 */
public class WeightingAlgorithm {
    private final List<Card> cards;

    public WeightingAlgorithm(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Liefert die nächste Karte auf Basis eines gewichteten Zufallsprinzips.
     * 
     * Die Gewichtung erfolgt durch mehrfaches Einfügen der Karte in eine temporäre Liste:
     * - Gewicht 1 → 1x in der Liste
     * - Gewicht 2 → 3x in der Liste
     * - Gewicht 3 → 6x in der Liste
     * 
     * Dadurch steigt die Wahrscheinlichkeit, dass schwerere Karten (z.B. Gewicht 3) öfter
     * gezogen werden. Die Methode wählt anschließend zufällig eine Karte aus dieser
     * gewichteten Liste.
     *
     * @return Eine zufällige Karte entsprechend ihrer Gewichtung oder null, wenn keine vorhanden.
     */
    public Card getNextCard() {
        List<Card> weightedList = new ArrayList<>();
        for (Card c : cards) {
            int repetitions = switch (c.weight) {
                case 1 -> 1;
                case 2 -> 3;
                case 3 -> 6;
                default -> 1;
            };
            for (int i = 0; i < repetitions; i++) {
                weightedList.add(c);
            }
        }

        if (weightedList.isEmpty()) return null;

        return weightedList.get((int)(Math.random() * weightedList.size()));
    }
}

