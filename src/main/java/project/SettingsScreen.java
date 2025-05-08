package project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class SettingsScreen extends BorderPane {
    private Runnable onBack;
    private DeckManager deckManager;
    private DeckDisplayScreen deckDisplayScreen;
    private Scene scene;

    private static ComboBox<String> themeSelector;
    private  Button fileSelectButton;
    private  Label importLabel;

    public SettingsScreen(Runnable onBack, DeckManager deckManager, DeckDisplayScreen deckDisplayScreen) {
        //this.deckManager = deckManager;
        this.onBack = onBack;
        this.deckManager = deckManager;
        this.deckDisplayScreen = deckDisplayScreen;

        var sidebarManager = SidebarManager.getInstance();
        //Add Sidebar
        this.setLeft(sidebarManager.showSidebar());

        // Action
        for(Button b : sidebarManager.getButtons()) {

            b.setOnAction(e -> {
                switch (b.getText().trim()){
                    case "Karten":
                        sidebarManager.updateButton(sidebarManager.getCardsButton());
                        showWarning();
                        break;
                    // NEED to be DONE

                    case "Karten-Verwaltung":
                        sidebarManager.updateButton(sidebarManager.getManagementButton());
                        showCardManagerScreen();
                        break;
                    // NEED to be DONE
                    case "Decks":
                        sidebarManager.updateButton(sidebarManager.getDecksButton());
                        showDeckScreen();
                        break;

                    default:
                        break;
                }
            });

        }

        this.setCenter(createSettingsScene());

        scene = new Scene(this, 1000, 700);
    }


    public void show() {
        Main.getStage().setScene(scene);
    }

    public VBox createSettingsScene() {
        // Root-Layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f9f9f9;");

        // Title
        Label title = new Label("Einstellungen");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Light-/Dark-Mode Auswahl
        Label themeLabel = new Label("Light-/Dark-Mode");
        themeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        themeSelector = new ComboBox<>();
        themeSelector.getItems().addAll("Light-Mode", "Dark-Mode");
        themeSelector.setValue("Light-Mode");

        HBox themeBox = new HBox(495, themeLabel, themeSelector);
        themeBox.setPadding(new Insets(10));
        themeBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5;");
        themeBox.setPrefHeight(50);
        themeBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Datei-Import Bereich
        importLabel = new Label("Decks importieren");
        importLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        fileSelectButton = new Button("Datei auswählen (.json)");
        fileSelectButton.setOnAction(e -> chooseFile());

        HBox importBox = new HBox(465, importLabel, fileSelectButton);
        importBox.setPadding(new Insets(10));
        importBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5;");
        importBox.setPrefHeight(50);
        importBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);


        // Alles zusammensetzen
        root.getChildren().addAll(title,themeBox, importBox);

        return root;
    }

    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("JSON-Datei auswählen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Dateien", "*.json"));

        File selectedFile = fileChooser.showOpenDialog(Main.getStage());
        if (selectedFile != null) {
            System.out.println("Ausgewählte Datei: " + selectedFile.getAbsolutePath());
            // Hier import Logik
        }
    }

    private void showDeckScreen() {
        DeckDisplayScreen deckView = new DeckDisplayScreen(deckManager);
        deckView.show();
    }

    private void showCardManagerScreen() {
        DeckManagerScreen deckView = new DeckManagerScreen(Main.getStage());
        deckView.show();
    }

    public void showWarning() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Dieses Menu braucht noch einen Screen");
        alert.setContentText("Wäre super lieb wenn jemand das machen würde :=)");
        alert.showAndWait();
    }
}
