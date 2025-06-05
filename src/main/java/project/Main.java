package project;

import javafx.application.Application;
import javafx.stage.Stage;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Hauptanwendungsklasse der Anwendung.
 * Diese Klasse initialisiert die JavaFX-Anwendung und verwaltet den Benutzerdatenordner.
 */
public class Main extends Application {
    /** Primäre JavaFX Stage für das Fenster dieser App */
    private static Stage stage;
    /** Pfad zum Ordner wo Nutzerdaten wie die Decks gespeichert werden */
    private static Path userDataPath;
    /** AppDirs-Instanz zur plattformunabhängigen Verwaltung von Anwendungsverzeichnissen */
    private static AppDirs appDirs;

    /**
     * Haupteinstiegspunkt der Anwendung.
     * Initialisiert das Nutzerverzeichnis und startet die JavaFX-App.
     * @param args Kommandozeilenargumente (weitergeleitet an JavaFX)
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
     * getter für Pfad zum Benutzerverzeichnis
     * @return Path-Objekt, das auf das Benutzerverzeichnis zeigt
     */
    public static Path getUserdataPath() {
        return Main.userDataPath;
    }

    /**
     * getter für App Hauptfenster Fenster Stage
     * @return Stage-Objekt des Hauptfensters
     */
    public static Stage getStage() {
        return Main.stage;
    }

    /**
     * JavaFX App beginn/initialisierung.
     * Erstellt das Hauptfenster und zeigt den initialen DeckDisplayScreen an.
     * @param stage Die primäre Stage der JavaFX-Anwendung
     */
    @Override
    public void start(Stage stage) {
        Main.stage = stage;
        stage.setTitle("Pentalingo");

        DeckManager deckManager = new DeckManager();
        DeckDisplayScreen deckDisplay = new DeckDisplayScreen(deckManager);
        deckDisplay.show();
        stage.show();
    }
}