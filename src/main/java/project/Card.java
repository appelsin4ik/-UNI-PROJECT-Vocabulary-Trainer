package project;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Einzelne Lernkarte
 */

// @JsonIgnoreProperties(ignoreUnknown = true)
public class Card implements Comparable<Card>{
    /** angezeigter Text */
    @JsonProperty("vocabulary")
    public String vocabulary;
    /** zu erlernendes Ergebnis */
    @JsonProperty("translation")
    public String translation;
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

    public void setTerm(String term) {
        this.vocabulary = term;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String trans) {
        this.translation =trans;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card other = (Card) obj;
        return weight == other.weight &&
            vocabulary.equals(other.vocabulary) &&
            translation.equals(other.translation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vocabulary, translation, weight);
    }
}
