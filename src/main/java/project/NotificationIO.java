package project;

import javafx.scene.control.Alert;

public class NotificationIO {
    /**
     * Zeigt einen Informations-Dialog an
     * @param title Der Titel des Dialog-Fensters
     * @param message Die anzuzeigende Nachricht
     */

    public static void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Zeigt einen Warnung-Dialog an
     * @param title Der Titel des Dialog-Fensters
     * @param message Die anzuzeigende Nachricht
     */

    public static void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
