package project;

/**
 * Einzelne Lernkarte
 */
public class Card {
    public String vocabulary;
    public String translation;
    public int weight;

    /**
     * Karte constructor
     */
    public Card(String vocabulary, String translation, int weight) {
        this.vocabulary = vocabulary;
        this.translation = translation;
        this.weight = weight;
    }
}
