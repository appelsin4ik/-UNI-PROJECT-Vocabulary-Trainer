package project;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Einzelne Lernkarte
 */

// @JsonIgnoreProperties(ignoreUnknown = true)
public class Card implements Comparable<Card>{
    /** Der anzuzeigende Begriff (z. B. ein Wort oder eine Phrase) */
    @JsonProperty("vocabulary")
    public String vocabulary;

    /** Die Übersetzung oder Bedeutung des Begriffs */
    @JsonProperty("translation")
    public String translation;

    /** Gewichtung der Karte zur Sortierung/Steuerung der Anzeigehäufigkeit */
    @JsonProperty("weight")
    public int weight;

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

    /**
     * Setzt den Begriff der Karte neu.
     * @param term neuer Begriff
     */
    public void setTerm(String term) {
        this.vocabulary = term;
    }

    /**
     * Getter für die Übersetzung.
     * @return Übersetzung der Karte
     */
    public String getTranslation() {
        return translation;
    }

    /**
     * Setzt die Übersetzung neu.
     * @param trans neue Übersetzung
     */
    public void setTranslation(String trans) {
        this.translation =trans;
    }

    /**
     * Getter für das Gewicht.
     * @return Gewicht der Karte
     */
    public int getWeight() {
        return weight;
    }


    /**
     * Gleichheitsprüfung zweier Karten (auf Inhalt und Gewicht)
     * @param obj das Vergleichsobjekt
     * @return true, wenn alle Felder übereinstimmen
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card other = (Card) obj;
        return weight == other.weight &&
            vocabulary.equals(other.vocabulary) &&
            translation.equals(other.translation);
    }

    /**
     * Berechnet den Hashcode auf Basis von Begriff, Übersetzung und Gewicht.
     * @return Hashcode der Karte
     */
    @Override
    public int hashCode() {
        return Objects.hash(vocabulary, translation, weight);
    }

    /**
     * Vergleicht Karten nach ihrer Gewichtung (für Sortierungen).
     * @param other die andere Karte
     * @return negativer Wert, wenn diese Karte ein kleineres Gewicht hat, sonst positiv oder 0
     */
    @Override
    public int compareTo(Card other) {
        return Integer.compare(this.weight, other.weight);
    }
}
