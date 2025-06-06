package project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Diese Klasse implementiert einen "Über uns" Dialog, der Teile des Firmenporträts anzeigt.
 *  Die Klasse ist als utility class konzipiert und kann nicht instanziiert werden.
 */
public class AboutDialog {
    /**
     * Privater Konstruktor um die Instanzierung der AboutDialog Klasse zu verhindern.
     */
    private AboutDialog() {}

    /**
     * Zeigt den "Über uns" Dialog in einem neuen Fenster an.
     * Der Dialog wird modal dargestellt, das heißt, der Benutzer muss den Dialog schließen,
     * bevor er mit dem Hauptfenster weiterarbeiten kann.
     */
    public static void show() {
        Stage stage = new Stage();
        stage.setTitle("About");

        // Logo image (from resources)
        var resource = AboutDialog.class.getResource("/Logo.png");
        assert resource != null;
        ImageView logo = new ImageView(new Image(resource.toExternalForm()));
        logo.setFitWidth(300);
        logo.setPreserveRatio(true);

        // Firmenname
        Label nameLabel = new Label("Pentalingo");
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Firmen Website Links
        Hyperlink websiteLink = new Hyperlink("www.pentalingo.com");
        Hyperlink deWebsiteLink = new Hyperlink("www.pentalingo.de");
        var linkBox = new HBox(5);
        linkBox.setAlignment(Pos.CENTER);
        linkBox.getChildren().addAll(websiteLink, deWebsiteLink);

        // Ausschnitt aus Firmenporträt
        Label description = new Label(
            "Die Pentalingo GmbH mit Sitz in Dresden ist das größte deutsche Unternehmen im Bereich eLearning und setzt neue Maßstäbe für digitales Lernen. " +
            "Mit einem engagierten Team von fast 100 Mitarbeiter:innen und einer klaren Vision unter der Leitung von Andreas Verbo (CEO) " +
            "und Michael Wagner (CMO) prägt Pentalingo die Zukunft der Bildung.");
        description.setWrapText(true);
        description.setAlignment(Pos.CENTER);

        Label version = new Label("Version: 1.1");

        VBox content = new VBox(15, logo, nameLabel, linkBox, description, version);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        stage.setScene(new Scene(content, 400, 500));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
