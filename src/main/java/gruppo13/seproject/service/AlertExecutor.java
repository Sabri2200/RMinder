package gruppo13.seproject.service;

import gruppo13.seproject.essential.action.type.DialogBoxAction;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class AlertExecutor {
    public static void run(DialogBoxAction a) {
        new Thread(() -> Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(a.getTitle());
            alert.setHeaderText(a.getContent());
            alert.setContentText(a.getMessage());
            alert.initModality(Modality.NONE);
            alert.showAndWait();
        })).start();
    }

    public static void showAlert(String title, String header, String content) {
        new Thread(() -> Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        })).start();
    }

    public static void showErrorAlert(Exception e) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("An error occurred");
            alert.setContentText(e.getMessage());
            alert.initModality(Modality.NONE);
            alert.showAndWait();
        });

        if (e.getClass().equals(InterruptedException.class)) {
            Platform.exit();
        }
    }
}
