package project;

import org.junit.jupiter.api.Test;

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
        DeckManager manager = new DeckManager(SettingsIO.loadSettings());
        Deck advancedDeck = manager.getDecks().stream()
                .filter(d -> d.getName().equals("Advanced Vocabulary"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Advanced Vocabulary deck not found"));

        long weight3Count = advancedDeck.getCards().stream()
                .filter(c -> c.weight == 3)
                .count();

        assertEquals(3, weight3Count, "Expected 3 cards with weight 3 in Advanced Vocabulary deck");
    }
}
