package project;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manager für alle Karten Decks (Datenmodel)
 */
public class DeckManager {
    /** liste aller geladenen Karten Decks */
    private List<Deck> decks;
    //private static  AppSettings mainSettings;
    private static DeckManager instance;

    /**
     * DeckManager Constructor
     */
    public DeckManager(AppSettings settings) {
        this.decks = new ArrayList<>();
        if (settings.isGenerateDefaultDecks() && decks.isEmpty()) {
            exportDeckToFile(createAdvancedVocabularyDeck());
            exportDeckToFile(createDummyVocabularyDeck());
            exportDeckToFile(createBasicVocabularyDeck());
            exportDeckToFile(createFoodVocabularyDeck());
        }
        loadDecks();
    }

    public static DeckManager getInstance() {
        if (instance == null) {
            instance = new DeckManager(SettingsIO.loadSettings());
        }

        return instance;
    }

    private void loadDecks() {
        File saveFolder = new File("saves");
        if (!saveFolder.exists()) {
            saveFolder.mkdirs();
        }

        File[] jsonFiles = saveFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
        if (jsonFiles == null) return;

        Gson gson = new Gson();

        for (File file : jsonFiles) {
            try (FileReader reader = new FileReader(file)) {
                Deck deck = gson.fromJson(reader, Deck.class);
                if (deck != null && deck.getName() != null) {
                    deck.setSourceFileName(file.getName());
                    decks.add(deck);
                }
            } catch (Exception e) {
                System.err.println("Fehler beim Laden von Deck aus Datei: " + file.getName());
            }
        }
    }


    public static void exportDeckToFile(Deck deck) {
        File saveFolder = new File("saves");
        if (!saveFolder.exists()) {
            saveFolder.mkdirs();
        }

        String safeName = deck.getName().replaceAll("[^a-zA-Z0-9_\\-]", "_").toLowerCase();
        File file = new File(saveFolder, safeName + ".json");

        if (file.exists()) {
            return;
        }

        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new Gson();
            gson.toJson(deck, writer);
            System.out.println("✅ Deck exportiert: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("❌ Fehler beim Exportieren des Decks \"" + deck.getName() + "\": " + e.getMessage());
        }
    }

    /**
     * Simples Beispiel Deck erstellen
     */
    private Deck createBasicVocabularyDeck() {
        Deck deck = new Deck("Basic Vocabulary");
        deck.addCard(new Card("Hello", "Bonjour", 1));
        deck.addCard(new Card("Goodbye", "Au revoir", 1));
        deck.addCard(new Card("Thank you", "Merci", 1));
        deck.addCard(new Card("Please", "S'il vous plaît", 2));
        deck.addCard(new Card("Yes", "Oui", 1));
        deck.addCard(new Card("No", "Non", 1));
        return deck;
    }

    /**
     * Complexeres Beispiel Deck erstellen
     */
    private Deck createAdvancedVocabularyDeck() {
        Deck deck = new Deck("Advanced Vocabulary");
        deck.addCard(new Card("Nevertheless", "Néanmoins", 3));
        deck.addCard(new Card("Furthermore", "De plus", 3));
        deck.addCard(new Card("Although", "Bien que", 2));
        deck.addCard(new Card("Therefore", "Par conséquent", 3));
        return deck;
    }

    /**
     * Beispiel Deck mit Thema Essen erstellen
     */
    private Deck createFoodVocabularyDeck() {
        Deck deck = new Deck("Food Vocabulary");
        deck.addCard(new Card("Apple", "Pomme", 1));
        deck.addCard(new Card("Bread", "Pain", 1));
        deck.addCard(new Card("Cheese", "Fromage", 1));
        deck.addCard(new Card("Water", "Eau", 1));
        deck.addCard(new Card("Wine", "Vin", 1));
        deck.addCard(new Card("Beef", "Bœuf", 1));
        return deck;
    }

    private Deck createDummyVocabularyDeck() {
        Deck deck = new Deck("Advanced Food Vocabulary");
        deck.addCard(new Card("Apple", "Pomme", 1));
        deck.addCard(new Card("Bread", "Pain", 1));
        deck.addCard(new Card("Cheese", "Fromage", 1));
        deck.addCard(new Card("Water", "Eau", 1));
        deck.addCard(new Card("Wine", "Vin", 1));
        deck.addCard(new Card("Beef", "Bœuf", 1));
        deck.addCard(new Card("Cheese", "Fromage", 1));
        deck.addCard(new Card("Water", "Eau", 1));
        return deck;
    }

    /**
     * getter für Decks
     * @return alle Decks
     */
    public List<Deck> getDecks() {
        return decks;
    }

    /**
     * Deck zu Liste hinzufügen
     * @param deck das neue Deck
     */
    public void addDeck(Deck deck) {
        decks.add(deck);
    }
}
