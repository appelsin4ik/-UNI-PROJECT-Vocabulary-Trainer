package project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeckTest {

    @Test
    public void testAddCard() {
        Deck deck = new Deck("TestDeck");
        assertEquals(0, deck.getCards().size());

        deck.addCard("Hund", "Dog");
        assertEquals(1, deck.getCards().size());
        assertEquals("Hund", deck.getCards().get(0).vocabulary);
        assertEquals("Dog", deck.getCards().get(0).translation);
    }

    @Test
    public void testCardCompareTo() {
        Card c1 = new Card("Haus", "House", 1);
        Card c2 = new Card("Baum", "Tree", 3);
        assertTrue(c1.compareTo(c2) < 0);
        assertTrue(c2.compareTo(c1) > 0);
    }
}

