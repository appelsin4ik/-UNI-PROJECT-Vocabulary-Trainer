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
import javafx.stage.Stage;

import java.io.File;

public class SettingsScreen{
    private Runnable onBack;
    private DeckManager deckManager;
    private DeckDisplayScreen deckDisplayScreen;
    private Stage stage;

    private static ComboBox<String> themeSelector;
    private  Button fileSelectButton;
    private  Label importLabel;

    public SettingsScreen(Stage stage,Runnable onBack,DeckManager deckManager,DeckDisplayScreen deckDisplayScreen) {
        //this.deckManager = deckManager;
        this.onBack = onBack;
        this.stage = stage;
        this.deckManager = deckManager;
        this.deckDisplayScreen = deckDisplayScreen;
    }


    public void show(){
        BorderPane root = new BorderPane();

        //Add Sidebar
        root.setLeft(SidebarManager.getInstance().showSidebar());

        // Action
        for(Button b : SidebarManager.getInstance().getButtons()) {

            b.setOnAction(e -> {
                switch (b.getText().trim()){
                    case "Karten":
                        SidebarManager.getInstance().updateButton(SidebarManager.getInstance().getCardsButton());
                        showWarning();
                        break;
                    // NEED to be DONE

                    case "Karten-Verwaltung":
                        SidebarManager.getInstance().updateButton(SidebarManager.getInstance().getManagementButton());
                        showCardManagerScreen(stage);
                        break;
                    // NEED to be DONE
                    case "Decks":
                        SidebarManager.getInstance().updateButton(SidebarManager.getInstance().getDecksButton());
                        showDeckScreen(stage);
                        break;

                    default:
                        break;
                }
            });

        }

        root.setCenter(createSettingsScene());


        //1000 700
        stage.setScene(new Scene(root, 1000, 700));
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
        fileSelectButton.setOnAction(e -> chooseFile(stage));

        HBox importBox = new HBox(465, importLabel, fileSelectButton);
        importBox.setPadding(new Insets(10));
        importBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5;");
        importBox.setPrefHeight(50);
        importBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);


        // Alles zusammensetzen
        root.getChildren().addAll(title,themeBox, importBox);

        return root;
    }

    private void chooseFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("JSON-Datei auswählen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Dateien", "*.json"));

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("Ausgewählte Datei: " + selectedFile.getAbsolutePath());
            // Hier import Logik
        }
    }

    private void showDeckScreen(Stage stage){
        DeckDisplayScreen deckView = new DeckDisplayScreen(deckManager,stage);
        deckView.show();
    }

    private void showCardManagerScreen(Stage stage){
        DeckManagerScreen deckView = new DeckManagerScreen(stage);
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
