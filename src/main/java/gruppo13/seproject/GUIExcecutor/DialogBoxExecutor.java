package gruppo13.seproject.GUIExcecutor;

import gruppo13.seproject.essential.action.type.DialogBoxAction;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DialogBoxExecutor {
    public static void run(DialogBoxAction a) {
        new Thread(() -> Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(a.getTitle());
            alert.setHeaderText(a.getContent());
            alert.setContentText(a.getMessage());
            alert.showAndWait();
        })).start();
    }
}