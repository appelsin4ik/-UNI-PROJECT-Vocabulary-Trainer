package project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

        Label nameLabel = new Label("MyCompany Inc.");
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label description = new Label("""
            MyCompany builds high-quality JavaFX software.
            We care about performance, design, and usability.
        """);
        description.setWrapText(true);
        description.setAlignment(Pos.CENTER);

        VBox content = new VBox(15, logo, nameLabel, description);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        stage.setScene(new Scene(content, 400, 400));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
