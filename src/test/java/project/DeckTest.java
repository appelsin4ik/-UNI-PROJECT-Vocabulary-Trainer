package project;

import javafx.scene.control.TextField;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testDeckCreation() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("Hund", "dog"));
        cards.add(new Card("Katze", "cat"));

        Deck deck = new Deck("TestDeck", cards);

        assertEquals("TestDeck", deck.getName());
        assertEquals(2, deck.getCards().size());
    }
}

