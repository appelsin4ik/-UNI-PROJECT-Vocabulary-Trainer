package project;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class DeckManagementScreen extends BorderPane {


    private VBox cardListBox;   
    private Deck selectedDeck;    

    private VBox deckListBox;

    private ScrollPane cardScroll;

    private Scene scene;
    private ObservableList<Deck> deckObservableList;
    private TextField deckNameField;
    private Button deleteDeckButton;

    public DeckManagementScreen() {
        this.setStyle("-fx-background-color: #f5f5f5;");

        var sidebarManager = SidebarManager.getInstance();
        this.setLeft(sidebarManager.showSidebar());

        for (Button b : sidebarManager.getButtons()) {
            b.setOnAction(e -> {
                switch (b.getText().trim()) {
                    case "Karten" -> {
                        sidebarManager.updateButton(sidebarManager.getCardsButton());
                        SidebarManager.showCreationScreen();
                    }
                    case "Decks" -> {
                        sidebarManager.updateButton(sidebarManager.getDecksButton());
                        SidebarManager.showDeckScreen();
                    }
                    case "Einstellungen" -> {
                        sidebarManager.updateButton(sidebarManager.getSettingsButton());
                        SidebarManager.showSettingsScreen();
                    }
                    case "About" -> AboutDialog.show();
                }
            });
        }

        VBox content = createMainContent();
        this.setCenter(content);
        scene = new Scene(this, 1000, 700);
    }

    private VBox createMainContent() {
        Label title = new Label("Deck-Verwaltung");
        title.setStyle("""
            -fx-font-size: 24px;
            -fx-font-weight: bold;
            -fx-text-fill: #333;
        """);
        VBox.setMargin(title, new Insets(0, 0, 10, 0));

        HBox mainContent = new HBox(20);
        mainContent.setPadding(new Insets(20));

        deckObservableList = FXCollections.observableArrayList(DeckManager.getInstance().getDecks());

        ScrollPane deckScroll = new ScrollPane();

        deckListBox = new VBox(15); 
        deckListBox.setPadding(new Insets(0, 10, 10, 10)); 

        deckScroll.setContent(deckListBox);
        deckScroll.setFitToWidth(true);
        deckScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        deckScroll.setStyle(StyleConstants.TRANSPARENT);
        deckScroll.setPrefWidth(400); 

        // nach links bewegen
        HBox.setMargin(deckScroll, new Insets(0, -10, 0, -30)); 

        // schmale, dezente Scrollbar
        Platform.runLater(() -> {
            var scrollbar = deckScroll.lookup(".scroll-bar:vertical");
            if (scrollbar != null) {
                scrollbar.setStyle("-fx-pref-width: 8px; -fx-background-color: transparent;");
            }
        });

        fillDecks();


        VBox editorPane = new VBox(15);
        editorPane.setPadding(new Insets(10));
        editorPane.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 20;");
        editorPane.setPrefWidth(680);
        editorPane.setPrefHeight(600);

        deckNameField = new TextField();
        deckNameField.setPromptText("Deck-Name");
        deckNameField.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #ddd; -fx-border-radius: 6; -fx-padding: 8;");
        deckNameField.setDisable(true);

        deleteDeckButton = new Button("Deck löschen");
        deleteDeckButton.setStyle("""
            -fx-background-color: #c62828;
            -fx-text-fill: white;
            -fx-padding: 8 16;
            -fx-background-radius: 6;
        """);
        deleteDeckButton.setDisable(true);

        deleteDeckButton.setOnMouseEntered(e -> {
            if (!deleteDeckButton.isDisabled()) {
                deleteDeckButton.setStyle("""
                    -fx-background-color: #b71c1c;
                    -fx-text-fill: white;
                    -fx-padding: 8 16;
                    -fx-background-radius: 6;
                """);
            }
        });

        deleteDeckButton.setOnMouseExited(e -> {
            if (!deleteDeckButton.isDisabled()) {
                deleteDeckButton.setStyle("""
                    -fx-background-color: #c62828;
                    -fx-text-fill: white;
                    -fx-padding: 8 16;
                    -fx-background-radius: 6;
                """);
            }
        });

        HBox deckButtons = new HBox(10, deleteDeckButton);

        cardListBox = new VBox(10);
        cardListBox.setStyle(StyleConstants.TRANSPARENT);
        cardListBox.setPadding(new Insets(10));
        

        cardScroll = new ScrollPane(cardListBox);
        cardScroll.setStyle(StyleConstants.TRANSPARENT);
        cardScroll.setFitToWidth(true);
        cardScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);


        // schmale, dezente Scrollbar
        Platform.runLater(() -> {
            var scrollbar = cardScroll.lookup(".scroll-bar:vertical");
            if (scrollbar != null) {
                scrollbar.setStyle("-fx-pref-width: 8px; -fx-background-color: transparent;");
            }
        });

        VBox.setVgrow(cardScroll, Priority.ALWAYS);

        TableColumn<Card, String> vocabCol = new TableColumn<>("Begriff");
        vocabCol.setCellValueFactory(new PropertyValueFactory<>("vocabulary"));

        TableColumn<Card, String> translationCol = new TableColumn<>("Übersetzung");
        translationCol.setCellValueFactory(new PropertyValueFactory<>("translation"));

        TableColumn<Card, Integer> weightCol = new TableColumn<>("Gewichtung");
        weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));

        editorPane.getChildren().addAll(deckNameField, deckButtons, cardScroll);
        mainContent.getChildren().addAll(deckScroll, editorPane);
        VBox.setMargin(mainContent, new Insets(-20, 0, 0, 0));

        deckNameField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (selectedDeck != null) {
                selectedDeck.setName(newVal.trim());
                saveCurrentDeck();
                fillDecks();
            }
        });

        deleteDeckButton.setOnAction(e -> {
            deleteConfirmation();
        });


        VBox result = new VBox(10);
        result.setPadding(new Insets(20));

        result.getChildren().addAll(title,mainContent);

        return result;
    }

    public void show() {
        Main.getStage().setScene(scene);
    }

    private VBox createCardList(java.util.List<Card> cards) {
        VBox list = new VBox(20); 
        list.setPadding(new Insets(10));

        for (Card c : new ArrayList<>(cards)) { 
            VBox cardBox = new VBox(10);
            cardBox.setPadding(new Insets(20));
            cardBox.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 12;
                -fx-border-color: #ddd;
                -fx-border-radius: 12;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 2);
            """);

            TextField vocabField = new TextField(c.getTerm());
            vocabField.setPromptText("Begriff");
            vocabField.setStyle("""
                -fx-font-size: 18px;
                -fx-font-weight: bold;
                -fx-background-color: #f9f9f9;
                -fx-background-radius: 8;
                -fx-padding: 10;
                -fx-border-color: transparent;
            """);

            TextField transField = new TextField(c.getTranslation());
            transField.setPromptText("Übersetzung");
            transField.setStyle("""
                -fx-font-size: 16px;
                -fx-background-color: #f9f9f9;
                -fx-background-radius: 8;
                -fx-padding: 10;
                -fx-border-color: transparent;
            """);

            Button deleteButton = new Button("Löschen");
            deleteButton.setStyle("""
                -fx-background-color: #e57373;
                -fx-text-fill: white;
                -fx-background-radius: 20;
                -fx-padding: 6 16;
            """);

            deleteButton.setOnMouseEntered(ev -> deleteButton.setStyle("""
                -fx-background-color: #ef5350;
                -fx-text-fill: white;
                -fx-background-radius: 20;
                -fx-padding: 6 16;
            """));

            deleteButton.setOnMouseExited(ev -> deleteButton.setStyle("""
                -fx-background-color: #e57373;
                -fx-text-fill: white;
                -fx-background-radius: 20;
                -fx-padding: 6 16;
            """));

            deleteButton.setOnAction(e -> {
                cards.remove(c);
                cardListBox.getChildren().setAll(createCardList(cards));
                DeckDisplayScreen.saveDeckToFile(selectedDeck);
                fillDecks();
            });

            vocabField.textProperty().addListener((obs, oldVal, newVal) -> {
                c.setTerm(newVal);
                saveCurrentDeck();
            });
            transField.textProperty().addListener((obs, oldVal, newVal) -> {
                c.setTranslation(newVal);
                saveCurrentDeck();
            });

            Separator sep = new Separator();

            HBox buttonRow = new HBox(deleteButton);
            buttonRow.setAlignment(Pos.BOTTOM_RIGHT);

            cardBox.getChildren().addAll(vocabField, sep, transField, buttonRow);
            list.getChildren().add(cardBox);
        }

        list.getChildren().add(createAddCardBox());
        return list;
    }

    private void fillDecks() {
        if (deckListBox != null) {
            deckListBox.getChildren().clear();
        }
        for (Deck d : deckObservableList) {
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER); 
            box.setPadding(new Insets(15)); 
            box.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 10; /*8*/
                -fx-border-color: #ddd;
                -fx-border-radius: 10; /*8*/
                // -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 4, 0, 0, 1);
            """);
            box.setSpacing(5);
            box.setPrefSize(230, 100); 

            Label name = new Label(d.getName());
            name.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            Label count = new Label(d.getCards().size() + " Karten");
            count.setStyle("""
                -fx-font-size: 13px;
                -fx-text-fill: #666;
            """);
            box.getChildren().addAll(name,count);

            box.setOnMouseClicked(e -> {
                selectedDeck = d;
                deckNameField.setText(d.getName());
                cardListBox.getChildren().setAll(createCardList(d.getCards()));
                deleteDeckButton.setDisable(false);
                deckNameField.setDisable(false);
                Platform.runLater(() -> {
                    Node viewport = cardScroll.lookup(".viewport");
                        if (viewport != null) {
                            viewport.setStyle("-fx-background-color: transparent;");
                        }
                });
            });

            deckListBox.getChildren().add(box);
        }
    }

    private VBox createAddCardBox() {
        VBox addBox = new VBox();
        addBox.setAlignment(Pos.CENTER);
        addBox.setPadding(new Insets(15));
        addBox.setSpacing(5);
        addBox.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-border-color: #bbb;
            -fx-border-radius: 10;
            -fx-cursor: hand;
        """);
        addBox.setPrefSize(230, 100);

        Label plus = new Label("+");
        plus.setStyle("""
            -fx-font-size: 32px;
            -fx-text-fill: #888;
        """);

        Label label = new Label("Karte hinzufügen");
        label.setStyle("""
            -fx-font-size: 12px;
            -fx-text-fill: #888;
        """);

        addBox.getChildren().addAll(plus, label);
        addBox.setOnMouseEntered(e ->{
            addBox.setStyle("""
                -fx-background-color: #f0f0f0;
                -fx-background-radius: 10;
                -fx-border-color: #999;
                -fx-border-radius: 10;
                -fx-cursor: hand;
            """);
            plus.setStyle("""
                -fx-font-size: 32px;
                -fx-text-fill: #3498db;
            """);
        });

        addBox.setOnMouseExited(e ->{
            addBox.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 10;
                -fx-border-color: #bbb;
                -fx-border-radius: 10;
                -fx-cursor: hand;
            """);
            plus.setStyle("""
                -fx-font-size: 32px;
                -fx-text-fill: #888;
            """);
        });
        addBox.setOnMouseClicked(e -> openAddCardDialog());

        return addBox;
    }

    private void openAddCardDialog() {
        Dialog<Card> dialog = new Dialog<>();
        dialog.setTitle("Neue Karte erstellen");

        TextField vocabField = new TextField();
        vocabField.setPromptText("Begriff");
        vocabField.setStyle("""
            -fx-background-color: #f9f9f9;
            -fx-border-color: #ddd;
            -fx-border-radius: 6;
            -fx-background-radius: 6;
            -fx-padding: 10;
            -fx-font-size: 14px;
        """);

        TextField transField = new TextField();
        transField.setPromptText("Übersetzung");
        transField.setStyle("""
            -fx-background-color: #f9f9f9;
            -fx-border-color: #ddd;
            -fx-border-radius: 6;
            -fx-background-radius: 6;
            -fx-padding: 10;
            -fx-font-size: 14px;
        """);

        VBox content = new VBox(10, vocabField, transField);
        content.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(content);

        ButtonType okButtonType = new ButtonType("Hinzufügen", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setStyle("""
            -fx-background-color: #2196F3;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-padding: 10 20;
            -fx-background-radius: 6;
        """);

        okButton.setOnMouseEntered(e -> okButton.setStyle("""
            -fx-background-color: #1976D2;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-padding: 10 20;
            -fx-background-radius: 6;
        """));

        okButton.setOnMouseExited(e -> okButton.setStyle("""
            -fx-background-color: #2196F3;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-padding: 10 20;
            -fx-background-radius: 6;
        """));

        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("""
            -fx-background-color: #e0e0e0;
            -fx-text-fill: #333;
            -fx-font-size: 14px;
            -fx-padding: 10 20;
            -fx-background-radius: 6;
        """);

        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("""
            -fx-background-color: #cfcfcf;
            -fx-text-fill: #333;
            -fx-font-size: 14px;
            -fx-padding: 10 20;
            -fx-background-radius: 6;
        """));

        cancelButton.setOnMouseExited(e -> cancelButton.setStyle("""
            -fx-background-color: #e0e0e0;
            -fx-text-fill: #333;
            -fx-font-size: 14px;
            -fx-padding: 10 20;
            -fx-background-radius: 6;
        """));

        okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if (vocabField.getText().isBlank() || transField.getText().isBlank()) {
                event.consume();
                NotificationIO.showWarning("Ungültige Eingabe", "Beide Felder müssen ausgefüllt sein.");
            }
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Card(vocabField.getText().trim(), transField.getText().trim());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(newCard -> {
            if (selectedDeck != null) {
                selectedDeck.getCards().add(newCard);
                cardListBox.getChildren().setAll(createCardList(selectedDeck.getCards()));
                DeckDisplayScreen.saveDeckToFile(selectedDeck);

                fillDecks();
            }
        });
    }

    private void saveCurrentDeck() {
        if (selectedDeck != null && selectedDeck.getSourceFileName() != null) {
            DeckDisplayScreen.saveDeckToFile(selectedDeck);
        }
    }

    private void deleteConfirmation() {
        if (selectedDeck != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Deck löschen");
            confirm.setHeaderText("Möchtest du das Deck wirklich löschen?");
            confirm.setContentText("Deck: \"" + selectedDeck.getName() + "\"");

            ButtonType yes = new ButtonType("Löschen", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirm.getButtonTypes().setAll(yes, no);

            Button yesStyle = (Button) confirm.getDialogPane().lookupButton(yes);
            yesStyle.setStyle("""
                -fx-background-color: #f44336;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-padding: 8 20;
                -fx-background-radius: 6;
            """);

            yesStyle.setOnMouseEntered(ev -> yesStyle.setStyle("""
                -fx-background-color: #d32f2f;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-padding: 8 20;
                -fx-background-radius: 6;
            """));

            yesStyle.setOnMouseExited(ev -> yesStyle.setStyle("""
                -fx-background-color: #f44336;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-padding: 8 20;
                -fx-background-radius: 6;
            """));

            Button noStyle = (Button) confirm.getDialogPane().lookupButton(no);
            noStyle.setStyle("""
                -fx-background-color: #e0e0e0;
                -fx-text-fill: #333;
                -fx-font-size: 14px;
                -fx-padding: 8 20;
                -fx-background-radius: 6;
            """);

            noStyle.setOnMouseEntered(ev -> noStyle.setStyle("""
                -fx-background-color: #d5d5d5;
                -fx-text-fill: #333;
                -fx-font-size: 14px;
                -fx-padding: 8 20;
                -fx-background-radius: 6;
            """));

            noStyle.setOnMouseExited(ev -> noStyle.setStyle("""
                -fx-background-color: #e0e0e0;
                -fx-text-fill: #333;
                -fx-font-size: 14px;
                -fx-padding: 8 20;
                -fx-background-radius: 6;
            """));

            confirm.showAndWait().ifPresent(response -> {
                if (response == yes) {
                    DeckManager.getInstance().getDecks().remove(selectedDeck);
                    deckObservableList.remove(selectedDeck);

                    File file = new File("saves", selectedDeck.getSourceFileName());
                    if (file.exists()) file.delete();

                    selectedDeck = null;
                    deckNameField.clear();
                    cardListBox.getChildren().clear();
                    fillDecks();
                }
            });
        }
    }

}
