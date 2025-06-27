package project.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import project.items.Card;
import project.items.Deck;
import project.util.io.NotificationIO;
import project.util.io.SettingsIO;
import project.util.settings.AppSettings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
     * @param settings App Einstellungen
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

    /**
     * Gibt die einzige Instanz des DeckManagers zurück (Singleton-Pattern).
     * Falls noch keine Instanz existiert, wird eine neue mit den geladenen Einstellungen erstellt.
     *
     * @return die einzige Instanz des DeckManagers
     */
    public static DeckManager getInstance() {
        if (instance == null) {
            instance = new DeckManager(SettingsIO.loadSettings());
        }

        return instance;
    }

    /**
     * Lädt alle Kartendecks aus JSON-Dateien im 'saves'-Verzeichnis.
     * Erstellt das Verzeichnis, falls es nicht existiert.
     * Ignoriert ungültige oder beschädigte JSON-Dateien.
     */
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

    /**
     * Exportiert ein Kartendeck als JSON-Datei in das 'saves'-Verzeichnis.
     * Der Dateiname wird aus dem Deck-Namen generiert (nur alphanumerische Zeichen).
     * Existierende Dateien werden nicht überschrieben.
     *
     * @param deck das zu exportierende Kartendeck
     */
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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        deck.addCard(new Card("Please", "S'il vous plaît", 1));
        deck.addCard(new Card("Yes", "Oui", 1));
        deck.addCard(new Card("No", "Non", 1));
        deck.addCard(new Card("Excuse me", "Excusez-moi", 1));
        deck.addCard(new Card("I'm sorry", "Je suis désolé", 1));
        deck.addCard(new Card("How are you?", "Comment ça va ?", 1));
        deck.addCard(new Card("Nice to meet you", "Enchanté", 1));
        return deck;
    }

    /**
     * Complexeres Beispiel Deck erstellen
     */
    private Deck createAdvancedVocabularyDeck() {
        Deck deck = new Deck("Advanced Vocabulary");
        deck.addCard(new Card("Nevertheless", "Néanmoins", 1));
        deck.addCard(new Card("Furthermore", "De plus", 1));
        deck.addCard(new Card("Although", "Bien que", 1));
        deck.addCard(new Card("Therefore", "Par conséquent", 1));
        deck.addCard(new Card("Consequently", "En conséquence", 1));
        deck.addCard(new Card("In contrast", "En revanche", 1));
        deck.addCard(new Card("Regardless", "Peu importe", 1));
        deck.addCard(new Card("Hence", "D'où", 1));
        deck.addCard(new Card("Thus", "Ainsi", 1));
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
        deck.addCard(new Card("Fish", "Poisson", 1));
        deck.addCard(new Card("Chicken", "Poulet", 1));
        deck.addCard(new Card("Egg", "Œuf", 1));
        deck.addCard(new Card("Salt", "Sel", 1));
        return deck;
    }

    /**
     * Erstellt ein Dummy-Kartendeck mit fortgeschrittenen Vokabeln zum Thema Essen.
     * Dieses Deck enthält einige Duplikate und dient Testzwecken.
     *
     * @return ein neues Dummy-Kartendeck
     */
    private Deck createDummyVocabularyDeck() {
        Deck deck = new Deck("Advanced Food Vocabulary");
        deck.addCard(new Card("Avocado", "Avocat", 1));
        deck.addCard(new Card("Blueberry", "Myrtille", 1));
        deck.addCard(new Card("Asparagus", "Asperge", 1));
        deck.addCard(new Card("Mushroom", "Champignon", 1));
        deck.addCard(new Card("Truffle", "Truffe", 1));
        deck.addCard(new Card("Lobster", "Homard", 1));
        deck.addCard(new Card("Zucchini", "Courgette", 1));
        deck.addCard(new Card("Pumpkin", "Citrouille", 1));
        deck.addCard(new Card("Spinach", "Épinard", 1));
        deck.addCard(new Card("Goose liver", "Foie gras", 1));
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

    public static void saveDeck(Deck deck) {

        if (deck == null) {
            NotificationIO.showWarning("Fehler", "Deck ist beschädigt");
            System.err.println("Fehler: Deck ist null.");
            return;
        }

        String name = deck.getName();
        if (name == null || name.trim().isEmpty()) {
            NotificationIO.showWarning("Fehler", "Deck hat keinen gültigen Namen");
            System.err.println("Fehler: Deck hat keinen gültigen Namen.");
            return;
        }

        if (deck.getCards() == null || deck.getCards().isEmpty()) {
            NotificationIO.showWarning("Fehler", "Deck enthält keine Karten");
            System.err.println("Fehler: Deck enthält keine Karten.");
            return;
        }

        File directory = new File("saves");
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                NotificationIO.showWarning("Fehler", "Fehler beim Erstellen des Deck-Ordners");
                System.err.println("Fehler beim Erstellen des Deck-Ordners.");
                return;
            }
        }

        File file = new File(directory, name.replaceAll("\\s+", "_") + ".json");
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new Gson();
            gson.toJson(deck, writer);
            System.out.println("Deck erfolgreich gespeichert: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern des Decks: " + e.getMessage());
            return;
        }
}

}
