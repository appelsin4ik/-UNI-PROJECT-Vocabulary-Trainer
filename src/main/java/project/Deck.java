package project;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * Deck an Karten
 */
public class Deck {
    /** Name dieses Decks */
    @JsonProperty("name")
    private String name;
    /** Liste aller Karten in diesem Deck */
    @JsonProperty("cards")
    private List<Card> cards;

    private transient String sourceFileName;

    /** neues leeres Deck erstellen */
    public Deck() {
        this.cards = new ArrayList<>();
    }
    public Deck(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public Deck(String name,List<Card> cards) {
        this.name = name;
        this.cards = cards;
    }

    /**
     * getter von Deck Name
     * @return Name des Decks
     */
    public String getName() {
        return name;
    }

    /**
     * getter für Deck Karten
     * @return Karten des Decks
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Karte zu Deck hinzufügen
     * @param card Karte die zu dem Deck hinzugefügt wird
     */
    public void addCard(Card card) {
        cards.add(card);
    }
    /**
     * Karte zu Deck hinzufügen
     * @param vocabulary angezeigter Text
     * @param translation zu erlernendes Ergebnis
     */
    public void addCard(String vocabulary, String translation) {
        Card newCard = new Card(vocabulary, translation, 1);
        cards.add(newCard);
    }

    /**
     * Deck als Json in einer Datei speichern
     */
    public void save() throws IOException {
        var userdataPath = Main.getUserdataPath();
        var file = userdataPath.resolve(name + ".json").toFile();
        this.writeFile(file);
    }

    /**
     * ObjectMapper instance erstellen
     * @return jackson ObjectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return JsonMapper.builder()
            // ObjectMapper für pretty print convertieren
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true).build();
    }

    /**
     * Deck als Json aus einer Datei lesen
     * @param file lesbare Datei
     * @return das geladene Deck
     */
    public static Deck readFile(File file) throws IOException {
        // ObjectMapper instance erstellen
        var mapper = Deck.getObjectMapper();
        // json string mit ObjectMapper zu object convertieren
        return mapper.readValue(file, Deck.class);
    }

    /**
     * Deck als Json in eine Datei schreiben
     * @param file schreibbare Datei
     */
    private void writeFile(File file) throws IOException {
        // ObjectMapper instance erstellen
        var mapper = Deck.getObjectMapper();

        // Deck json in Datei schreiben
        mapper.writeValue(file, this);
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }
}
