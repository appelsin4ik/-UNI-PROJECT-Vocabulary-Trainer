package project;
import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards;
    private String name;

    public Deck(String name) {
        this.name = name;
        this.cards = new ArrayList<Card>();
        // Initialize empty, or populate with cards
    }

    public void AddCard(String vocabulary, String translation) {
        Card newCard = new Card(vocabulary, translation, 1);
        cards.add(newCard);
    }
    public void addCard(Card card) {
        cards.add(card);
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }
}
