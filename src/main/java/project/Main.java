package project;

import javafx.application.Application;
import javafx.stage.Stage;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Main Application Class mit application Eintsiegspunkt
 */
public class Main extends Application {

    /** Stage für das Fenster dieser App */
    private static Stage stage;
    /** Pfad zum Ordner wo Nutzerdaten wie die Decks gespeichert werden */
    private static Path userDataPath;
    private static AppDirs appDirs;

    /**
     * main Einstiegspunkt
     */
    public static void main(String[] args) {
        appDirs = AppDirsFactory.getInstance();
        userDataPath = Path.of(appDirs.getUserDataDir("se-project", "1.0.0", "projekt-gruppe"));
        System.out.printf("Userdata Folder: %s", userDataPath);

        try {
            // Check if path already exists
            if (!Files.exists(userDataPath)) {
                // Create directory only if it doesn't exist
                Files.createDirectories(userDataPath);
                System.out.println("Created userdata folder: " + userDataPath.toAbsolutePath());
            } else {
                System.out.println("Using existing userdata folder: " + userDataPath.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("FATAL: Could not initialize userdata folder: " + e.getMessage());
            System.exit(1);
        }
        launch(args);
    }

    /**
     * getter für App Fenster Stage
     */
    public static Path getUserdataPath() {
        return Main.userDataPath;
    }

    /**
     * getter für App Fenster Stage
     */
    public static Stage getStage() {
        return Main.stage;
    }

    /**
     * JavaFX Application beginn
     */
    @Override
    public void start(Stage stage) {
        Main.stage = stage;
        stage.setTitle("Vocabulary Learner");

        DeckManager deckManager = new DeckManager();
        DeckDisplayScreen deckDisplay = new DeckDisplayScreen(deckManager);
        deckDisplay.show();
        stage.show();
    }
}