package project;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsIO {
    private static final File SETTINGS_FILE = new File("settings.json");

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

    public static void saveSettings(AppSettings settings) {
        try (FileWriter writer = new FileWriter(SETTINGS_FILE)) {
            new Gson().toJson(settings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
