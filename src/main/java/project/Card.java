package project;

/**
 * Einzelne Lernkarte
 */
public class Card {
    /** angezeigter Text */
    public String vocabulary;
    /** zu erlernendes Ergebniss */
    public String translation;
    /** gewichtung dieser Karte, verwendet für Shuffel Algorithmus */
    public int weight;

    /**
     * Karte constructor
     * @param vocabulary
     * @param translation
     * @param weight
     */
    public Card(String vocabulary, String translation, int weight) {
        this.vocabulary = vocabulary;
        this.translation = translation;
        this.weight = weight;
    }
}
