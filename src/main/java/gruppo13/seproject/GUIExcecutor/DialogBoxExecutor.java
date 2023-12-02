package gruppo13.seproject.GUIExcecutor;

import gruppo13.seproject.essential.action.actionType.DialogBoxAction;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DialogBoxExecutor {

    public static void run(DialogBoxAction a) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(a.getTitle());
        alert.setContentText(a.getContent());
        alert.setContentText(a.getMessage());
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }
}
