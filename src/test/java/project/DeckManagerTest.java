package project;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckManagerTest {

    @Test
    public void testDeckInitialization() {
        DeckManager deckManager = new DeckManager();
        List<Deck> decks = deckManager.getDecks();

        assertNotNull(decks);
        assertFalse(decks.isEmpty(), "Deck list should not be empty");
        assertEquals(4, decks.size(), "Expected 4 default decks");

        boolean containsBasic = decks.stream().anyMatch(d -> d.getName().equals("Basic Vocabulary"));
        boolean containsAdvanced = decks.stream().anyMatch(d -> d.getName().equals("Advanced Vocabulary"));

        assertTrue(containsBasic, "Should contain 'Basic Vocabulary' deck");
        assertTrue(containsAdvanced, "Should contain 'Advanced Vocabulary' deck");
    }

    @Test
    public void testBasicVocabularyDeckContents() {
        DeckManager manager = new DeckManager();
        Deck basicDeck = manager.getDecks().stream()
                .filter(d -> d.getName().equals("Basic Vocabulary"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Basic Vocabulary deck not found"));

        assertEquals(6, basicDeck.getCards().size(), "Expected 6 cards in Basic Vocabulary deck");

        Card firstCard = basicDeck.getCards().get(0);
        assertEquals("Hello", firstCard.vocabulary);
        assertEquals("Bonjour", firstCard.translation);
    }

    @Test
    public void testWeightDistributionInAdvancedDeck() {
        DeckManager manager = new DeckManager();
        Deck advancedDeck = manager.getDecks().stream()
                .filter(d -> d.getName().equals("Advanced Vocabulary"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Advanced Vocabulary deck not found"));

        long weight3Count = advancedDeck.getCards().stream()
                .filter(c -> c.weight == 3)
                .count();

        assertEquals(3, weight3Count, "Expected 3 cards with weight 3 in Advanced Vocabulary deck");
    }
}
