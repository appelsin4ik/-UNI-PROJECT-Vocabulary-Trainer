package project;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Einzelne Lernkarte
 */
public class Card implements Comparable<Card>{
    /** angezeigter Text */
    @JsonProperty("vocabulary")
    public String vocabulary;
    /** zu erlernendes Ergebnis */
    @JsonProperty("translation")
    public String translation;
    //public String[] phrases;
    /** gewichtung dieser Karte, verwendet für Shuffel Algorithmus */
    @JsonProperty("weight")
    public int weight;

    @Override
    public int compareTo(Card other) {
        return Integer.compare(this.weight, other.weight);
    }

    /**
     * Leerer default Konstruktor
     */
    public Card() {}
    /**
     * Karte constructor
     * @param vocabulary angezeigter Text
     * @param translation zu erlernendes Ergebnis
     * @param weight wird benutzt, um die Karten bei der Abfrage später nach Schwierigkeit zu sortieren und
     *               entsprechend öfter anzuzeigen
     */
    public Card(String vocabulary, String translation, int weight) {
        this.vocabulary = vocabulary;
        this.translation = translation;
        this.weight = weight;
    }

    /**
     * Karte constructor mit Standardgewichtung
     * @param vocabulary angezeigter Text
     * @param translation zu erlernendes Ergebnis
     */
    public Card(String vocabulary, String translation) {
        this.vocabulary = vocabulary;
        this.translation = translation;
        this.weight = 0;
    }

    /**
     * Getter für den Vokabeltext der Karte
     * @return Der angezeigte Text (vocabulary) der Karte
     */
    public String getTerm() {
        return vocabulary;
    }
}
