package project;


public class Card {
    public String vocabulary;
    public String translation;
    public String[] phrases;
    public int weight;

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
