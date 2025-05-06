package project;

import java.util.ArrayList;
import java.util.List;

public class DeckManager {
    private List<Deck> decks;

    public DeckManager() {
        decks = new ArrayList<>();
        // Add some sample decks
        decks.add(new Deck("English Verbs"));
        decks.add(new Deck("English Nouns"));
        decks.add(new Deck("French Verbs"));
        decks.add(new Deck("Spanish Adjectives"));
    }

    public List<Deck> getDecks() {
        return decks;
    }
}
