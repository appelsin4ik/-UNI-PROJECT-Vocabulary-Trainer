package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeckManager {
    private List<Deck> decks;

    public DeckManager() {
        decks = Arrays.asList(
                createBasicVocabularyDeck(),
                createAdvancedVocabularyDeck(),
                createFoodVocabularyDeck(),
                createDummyVocabularyDeck()
        );
    }

    private Deck createBasicVocabularyDeck() {
        Deck deck = new Deck("Basic Vocabulary");
        deck.addCard(new Card("Hello", "Bonjour", 1));
        deck.addCard(new Card("Goodbye", "Au revoir", 1));
        deck.addCard(new Card("Thank you", "Merci", 1));
        deck.addCard(new Card("Please", "S'il vous plaît", 2));
        deck.addCard(new Card("Yes", "Oui", 1));
        deck.addCard(new Card("No", "Non", 1));
        return deck;
    }

    private Deck createAdvancedVocabularyDeck() {
        Deck deck = new Deck("Advanced Vocabulary");
        deck.addCard(new Card("Nevertheless", "Néanmoins", 3));
        deck.addCard(new Card("Furthermore", "De plus", 3));
        deck.addCard(new Card("Although", "Bien que", 2));
        deck.addCard(new Card("Therefore", "Par conséquent", 3));
        return deck;
    }

    private Deck createFoodVocabularyDeck() {
        Deck deck = new Deck("Food Vocabulary");
        deck.addCard(new Card("Apple", "Pomme", 1));
        deck.addCard(new Card("Bread", "Pain", 1));
        deck.addCard(new Card("Cheese", "Fromage", 1));
        deck.addCard(new Card("Water", "Eau", 1));
        deck.addCard(new Card("Wine", "Vin", 1));
        deck.addCard(new Card("Beef", "Bœuf", 1));
        return deck;
    }

    private Deck createDummyVocabularyDeck() {
        Deck deck = new Deck("Advanced Food Vocabulary");
        deck.addCard(new Card("Apple", "Pomme", 1));
        deck.addCard(new Card("Bread", "Pain", 1));
        deck.addCard(new Card("Cheese", "Fromage", 1));
        deck.addCard(new Card("Water", "Eau", 1));
        deck.addCard(new Card("Wine", "Vin", 1));
        deck.addCard(new Card("Beef", "Bœuf", 1));
        deck.addCard(new Card("Cheese", "Fromage", 1));
        deck.addCard(new Card("Water", "Eau", 1));
        return deck;
    }

    public List<Deck> getDecks() {
        return decks;
    }
}
