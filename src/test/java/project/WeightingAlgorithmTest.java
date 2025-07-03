package project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.items.Card;
import project.util.logic.WeightingAlgorithm;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WeightingAlgorithmTest {

    private List<Card> cards;
    private Card easyCard;
    private Card mediumCard;
    private Card hardCard;

    @BeforeEach
    public void setup() {
        easyCard = new Card("easy", "leicht");
        easyCard.weight = 1;

        mediumCard = new Card("medium", "mittel");
        mediumCard.weight = 2;

        hardCard = new Card("hard", "schwer");
        hardCard.weight = 3;

        cards = new ArrayList<>(List.of(easyCard, mediumCard, hardCard));
    }

    @Test
    public void testWeightingDistribution() {
        WeightingAlgorithm algorithm = new WeightingAlgorithm(cards);

        Map<Card, Integer> counts = new HashMap<>();
        counts.put(easyCard, 0);
        counts.put(mediumCard, 0);
        counts.put(hardCard, 0);

        int iterations = 10_000;
        for (int i = 0; i < iterations; i++) {
            Card drawn = algorithm.getNextCardRaw();
            counts.put(drawn, counts.get(drawn) + 1);
        }

        System.out.println("Draw distribution:");
        System.out.println("Easy:   " + counts.get(easyCard));
        System.out.println("Medium: " + counts.get(mediumCard));
        System.out.println("Hard:   " + counts.get(hardCard));

        // Erwartetes Verhältnis: 1 : 3 : 6
        double easyRatio   = counts.get(easyCard) / (double) iterations;
        double mediumRatio = counts.get(mediumCard) / (double) iterations;
        double hardRatio   = counts.get(hardCard) / (double) iterations;

        assertTrue(hardRatio > mediumRatio);
        assertTrue(mediumRatio > easyRatio);
    }
}
