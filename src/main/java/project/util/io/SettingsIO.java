package project.util.io;

import com.google.gson.Gson;

import project.util.settings.AppSettings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Verwaltet das Laden und Speichern von Anwendungseinstellungen im JSON-Format.
 * Die Einstellungen werden in einer Datei 'settings.json' im Arbeitsverzeichnis gespeichert.
 */
public class SettingsIO {
    /** Die Datei, in der die Einstellungen gespeichert werden */
    private static final File SETTINGS_FILE = new File("settings.json");

    /**
     * Lädt die Anwendungseinstellungen aus der JSON-Datei.
     * Wenn die Datei nicht existiert oder nicht gelesen werden kann,
     * werden Standardeinstellungen zurückgegeben.
     *
     * @return Die geladenen Einstellungen oder neue Standardeinstellungen
     */
    public static AppSettings loadSettings() {
        if (SETTINGS_FILE.exists()) {
            try (FileReader reader = new FileReader(SETTINGS_FILE)) {
                return new Gson().fromJson(reader, AppSettings.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new AppSettings(); // default
    }

    /**
     * Speichert die übergebenen Einstellungen in der JSON-Datei.
     *
     * @param settings Die zu speichernden Einstellungen
     */
    public static void saveSettings(AppSettings settings) {
        try (FileWriter writer = new FileWriter(SETTINGS_FILE)) {
            new Gson().toJson(settings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
