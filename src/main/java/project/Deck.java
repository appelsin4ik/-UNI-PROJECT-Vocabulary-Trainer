package project;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Deck an Karten
 */
public class Deck {
    /** Name dieses Decks */
    private String name;
    /** Liste aller Karten in diesem Deck */
    private List<Card> cards;

    /** neues leeres Deck erstellen */
    public Deck(String name) {
        this.name = name;
        this.cards = new ArrayList<Card>();
    }

    /**
     * ObjectMapper instance erstellen
     * @return jackson ObjectMapper
     */
    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // ObjectMapper für pretty print convertieren
        //objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return objectMapper;
    }

    /**
     * lädt ein neues Deck als Json aus einer Datei
     * @param filepath Pfad der Datei aus der geladen wird
     * @return das geladene Deck
     */
    public static Deck load(String filepath) throws IOException {
        // json Datei Daten lesen
        byte[] jsonData = Files.readAllBytes(Paths.get("employee.txt"));

        // ObjectMapper instance erstellen
        var mapper = Deck.getObjectMapper();
        // json string mit ObjectMapper zu object convertieren
        return mapper.readValue(jsonData, Deck.class);
    }

    /**
     * dieses Deck als Json in einer Datei speichern
     * @param filepath Pfad zu dieser Datei
     */
    public void save(String filepath) throws IOException {
        // ObjectMapper instance erstellen
        var mapper = Deck.getObjectMapper();

        // dieses Deck in Datei schreiben
        var file = new File(filepath);
        mapper.writeValue(file, this);
    }

    /**
     * Karte zu Deck hinzufügen
     */
    public void addCard(Card card) {
        cards.add(card);
    }
    /**
     * Karte zu Deck hinzufügen
     */
    public void addCard(String vocabulary, String translation) {
        Card newCard = new Card(vocabulary, translation, 1);
        cards.add(newCard);
    }

    /**
     * getter von Deck Name
     */
    public String getName() {
        return name;
    }

    /**
     * getter für Deck Karten
     */
    public List<Card> getCards() {
        return cards;
    }
}
