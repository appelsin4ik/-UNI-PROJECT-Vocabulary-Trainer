package project;

import org.junit.jupiter.api.Test;

import project.control.DeckManager;
import project.items.Card;
import project.items.Deck;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckManagerTest {
    
    @Test
    public void testDeckExportSkippedIfFileExists() throws IOException {
        Deck deck = new Deck("UniqueDeck", List.of(new Card("Baum", "tree")));
        File file = new File("saves", "uniquedeck.json");

        // Vorbereiten: Datei anlegen
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("placeholder");
        }

        long originalLength = file.length();

        // Export sollte nicht überschreiben
        DeckManager.exportDeckToFile(deck);

        assertEquals(originalLength, file.length()); // Dateiinhalt unverändert
        file.delete();
    }

    @Test
    public void testWeightDistributionInAdvancedDeck() {
        Deck advancedDeck = new Deck("Advanced Vocabulary");
        advancedDeck.addCard(new Card("Nevertheless", "Néanmoins", 3));
        advancedDeck.addCard(new Card("Furthermore", "De plus", 3));
        advancedDeck.addCard(new Card("Therefore", "Par conséquent", 3));
        advancedDeck.addCard(new Card("Although", "Bien que", 2));

        DeckManager.getInstance().getDecks().clear();
        DeckManager.getInstance().getDecks().add(advancedDeck);

        long weight3Count = advancedDeck.getCards().stream()
            .filter(c -> c.weight == 3)
            .count();

        assertEquals(3, weight3Count, "Expected 3 cards with weight 3 in Advanced Vocabulary deck");
    }
}
