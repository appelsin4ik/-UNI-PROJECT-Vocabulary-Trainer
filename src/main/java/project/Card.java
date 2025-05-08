package project;

/**
 * Einzelne Lernkarte
 */
public class Card implements Comparable<Card>{
    /** angezeigter Text */
    public String vocabulary;
    /** zu erlernendes Ergebniss */
    public String translation;
    public String[] phrases;
    /** gewichtung dieser Karte, verwendet für Shuffel Algorithmus */
    public int weight;

    @Override
    public int compareTo(Card other) {
        return Integer.compare(this.weight, other.weight);
    }

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
    public Card(String vocabulary, String translation, String[] phrases, int weight) {
        this.vocabulary = vocabulary;
        this.translation = translation;
        this.weight = weight;
    }
}
