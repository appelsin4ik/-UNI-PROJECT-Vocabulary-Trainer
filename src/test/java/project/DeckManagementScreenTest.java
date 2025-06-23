// package project;

// import javafx.application.Platform;
// import javafx.scene.control.Label;
// import javafx.scene.layout.VBox;
// import org.junit.jupiter.api.*;

// import java.io.File;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

// /**
//  * Testklasse für DeckManagementScreen.
//  * Getestet wird u.a. die Deck-Filterung, Dateispeicherung und UI-Erzeugung.
//  */
// public class DeckManagementScreenTest {

//     private DeckManagementScreen screen;


//     private static boolean fxStarted = false;

//     @BeforeAll
//     static void initJavaFX() {
//         if (!fxStarted) {
//             fxStarted = true;
//             Platform.startup(() -> {});
//         }
//     }
//     // @BeforeAll
//     // static void initToolkit() {
//     //     Platform.startup(() -> {});
//     // }


//     @BeforeEach
//     void setup() {
//         DeckManager.getInstance().getDecks().clear();
//         screen = new DeckManagementScreen();
//     }

//     @Test
//     void filterDecks_returnsOnlyMatchingDeck() {
//         // Vorbereitung: DeckManager & observableList leeren
//         DeckManager.getInstance().getDecks().clear();

//         Deck deck1 = new Deck("Spanisch Basics", List.of(new Card("Hola", "Hallo")));
//         Deck deck2 = new Deck("Französisch", List.of(new Card("Bonjour", "Hallo")));

//         DeckManager.getInstance().getDecks().addAll(List.of(deck1,deck2));

//         DeckManagementScreen screen = new DeckManagementScreen();

//         screen.getDeckObservableList().setAll(DeckManager.getInstance().getDecks());

//         // Filtern nach "spanisch"
//         screen.filterDecks("spanisch");

//         VBox list = screen.getDeckListBox();

//         assertEquals(1, list.getChildren().size());

//         VBox deckBox = (VBox) list.getChildren().get(0);
//         Label nameLabel = (Label) deckBox.getChildren().get(0);
//         assertEquals("Spanisch Basics", nameLabel.getText());
//     }


//     @Test
//     void saveCurrentDeck_createsFile() {
//         Deck deck = new Deck("TestDeck", List.of(new Card("Hallo", "Hello")));
//         deck.setSourceFileName("TestDeck.json");
//         DeckManager.getInstance().getDecks().add(deck);

//         screen.setSelectedDeck(deck);
//         screen.saveCurrentDeck();

//         File file = new File("saves/TestDeck.json");
//         assertTrue(file.exists());
//         assertTrue(file.length() > 0);

//         assertTrue(file.delete()); // Cleanup
//     }

//     @Test
//     void createDeckBox_containsExpectedUIElements() {
//         Deck deck = new Deck("Demo", List.of(new Card("Hallo", "Hello")));
//         VBox box = screen.createDeckBox(deck);

//         assertEquals(2, box.getChildren().size());

//         Label name = (Label) box.getChildren().get(0);
//         assertEquals("Demo", name.getText());

//         Label count = (Label) box.getChildren().get(1);
//         assertTrue(count.getText().contains("Karten"));
//     }

//     @Test
//     void setAndGetSelectedDeck_shouldMatch() {
//         Deck dummy = new Deck("Dummy", List.of(new Card("A", "B")));
//         screen.setSelectedDeck(dummy);
//         assertEquals(dummy, screen.getSelectedDeck());
//     }

//     @Test
//     void updateSearchFieldWidth_shouldAdaptToDeckCount() {
//         DeckManager.getInstance().getDecks().addAll(
//             List.of(
//                 new Deck("Deck 1", List.of()), 
//                 new Deck("Deck 2", List.of())
//             )
//         );

//         screen.fillDecks(); // Intern wird updateSearchFieldWidth() aufgerufen
//         double width = screen.getSearchFieldContainer().getPrefWidth();

//         // Bei <= 4 Decks sollte Breite 235 sein
//         assertEquals(235, width);
//     }
// }
