package project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URI;

public class AboutDialog {
    private AboutDialog() {}

    public static void show() {
        Stage stage = new Stage();
        stage.setTitle("About");

        // Logo image (from resources or file path)
        var resource = AboutDialog.class.getResource("/Logo.png");
        assert resource != null;
        ImageView logo = new ImageView(new Image(resource.toExternalForm()));
        logo.setFitWidth(300);
        logo.setPreserveRatio(true);

        Label nameLabel = new Label("Pentalingo");
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Hyperlink websiteLink = new Hyperlink("www.pentalingo.com");
        Hyperlink deWebsiteLink = new Hyperlink("www.pentalingo.de");
        VBox linkBox = new VBox(5);
        linkBox.setAlignment(Pos.CENTER);
        linkBox.getChildren().addAll(websiteLink, deWebsiteLink);


        Label description = new Label(
            "Die Pentalingo GmbH mit Sitz in Dresden ist das größte deutsche Unternehmen im Bereich eLearning und setzt neue Maßstäbe für digitales Lernen. " +
            "Mit einem engagierten Team von fast 100 Mitarbeiter:innen und einer klaren Vision unter der Leitung von Andreas Verbo (CEO) " +
            "und Michael Wagner (CMO) prägt Pentalingo die Zukunft der Bildung.");
        description.setWrapText(true);
        description.setAlignment(Pos.CENTER);

        VBox content = new VBox(15, logo, nameLabel, linkBox, description);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        stage.setScene(new Scene(content, 400, 500));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
