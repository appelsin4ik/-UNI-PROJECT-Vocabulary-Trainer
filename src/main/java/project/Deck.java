package project;

public class Deck {
    private Card[] cards;
    private String name;

    public Deck(String name) {
        this.name = name;
        this.cards = new Card[0]; // Initialize empty, or populate with cards
    }

    public String getName() {
        return name;
    }
}
