package project;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class WeightingAlgorithmTest {

    @Test
    public void testSortByWeightAscending() {
        Card c1 = new Card("Apfel", "Apple", 3);
        Card c2 = new Card("Haus", "House", 1);
        Card c3 = new Card("Baum", "Tree", 2);

        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(c1, c2, c3));
        WeightingAlgorithm algorithm = new WeightingAlgorithm(cards);
        algorithm.Sort();

        assertEquals("Haus", cards.get(0).vocabulary);
        assertEquals("Baum", cards.get(1).vocabulary);
        assertEquals("Apfel", cards.get(2).vocabulary);
    }

    @Test
    public void testSortStableForSameWeight() {
        Card c1 = new Card("Eins", "One", 2);
        Card c2 = new Card("Zwei", "Two", 2);

        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(c1, c2));
        WeightingAlgorithm algorithm = new WeightingAlgorithm(cards);
        algorithm.Sort();

        // Sollte stabil sortieren: Reihenfolge gleich wie vorher
        assertEquals("Eins", cards.get(0).vocabulary);
        assertEquals("Zwei", cards.get(1).vocabulary);
    }

    @Test
    public void testSortWithEmptyList() {
        ArrayList<Card> cards = new ArrayList<>();
        WeightingAlgorithm algorithm = new WeightingAlgorithm(cards);

        assertDoesNotThrow(algorithm::Sort);
        assertTrue(cards.isEmpty());
    }
}
