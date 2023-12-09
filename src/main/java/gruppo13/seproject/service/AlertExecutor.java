package gruppo13.seproject.service;

import gruppo13.seproject.essential.action.type.DialogBoxAction;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

/*
The `AlertExecutor` class is a utility class in a JavaFX application designed to handle the display of alert dialogs. Here's a summary of its functionalities:

1. Running DialogBox Actions:
   - The `run(DialogBoxAction a)` method is used to display an information alert based on a `DialogBoxAction` object. This method creates a new thread to run the alert dialog in the JavaFX Application Thread using `Platform.runLater()`. The alert displays the title, content, and message from the `DialogBoxAction` object.

2. Showing Generic Alerts:
   - The `showAlert(String title, String header, String content)` method displays a generic information alert with specified title, header, and content. Like the `run` method, it uses a new thread and `Platform.runLater()` to ensure the alert is displayed in the JavaFX Application Thread.

3. Showing Error Alerts:
   - The `showErrorAlert(Exception e)` method displays an error alert with a generic title and header, and the exception's message as the content. If the exception is an `InterruptedException`, the method also calls `Platform.exit()` to terminate the application.

4. Thread Usage:
   - For both `run` and `showAlert`, new threads are created to handle the display of alerts. This is likely done to ensure that the alert dialogs do not block or interfere with the main application thread, especially in cases where the method might be called from a non-JavaFX thread.

5. Modality Setting:
   - In both `run` and `showErrorAlert`, the modality of the alert is set to `Modality.NONE`, allowing users to interact with other windows of the application while the alert is open.
*/

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
        new Thread(() -> Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("An error occurred");
            alert.setContentText(e.getMessage());
            alert.initModality(Modality.NONE);
            alert.showAndWait();
        })).start();

        if (e.getClass().equals(InterruptedException.class)) {
            Platform.exit();
        }
    }
}
