package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manager für alle Karten Decks (Datenmodel)
 */
public class DeckManager {
    /** liste aller geladenen Karten Decks */
    private List<Deck> decks;

    /**
     * DeckManager Constructor
     */
    public DeckManager() {
        decks = Arrays.asList(
                createBasicVocabularyDeck(),
                createAdvancedVocabularyDeck(),
                createFoodVocabularyDeck()
        );
    }

    /**
     * Simples Beispiel Deck erstellen
     */
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

    /**
     * Complexeres Beispiel Deck erstellen
     */
    private Deck createAdvancedVocabularyDeck() {
        Deck deck = new Deck("Advanced Vocabulary");
        deck.addCard(new Card("Nevertheless", "Néanmoins", 3));
        deck.addCard(new Card("Furthermore", "De plus", 3));
        deck.addCard(new Card("Although", "Bien que", 2));
        deck.addCard(new Card("Therefore", "Par conséquent", 3));
        return deck;
    }

    /**
     * Beispiel Deck mit Thema Essen erstellen
     */
    private Deck createFoodVocabularyDeck() {
        Deck deck = new Deck("Food Vocabulary");
        deck.addCard(new Card("Apple", "Pomme", 1));
        deck.addCard(new Card("Bread", "Pain", 1));
        deck.addCard(new Card("Cheese", "Fromage", 1));
        deck.addCard(new Card("Water", "Eau", 1));
        deck.addCard(new Card("Wine", "Vin", 1));
        deck.addCard(new Card("Beef", "Bœuf", 2));
        return deck;
    }

    /**
     * getter für Decks
     * @return alle Decks
     */
    public List<Deck> getDecks() {
        return decks;
    }
}
