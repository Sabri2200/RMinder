package gruppo13.seproject.essential.model.action;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MessageAction implements Action {
    String title, message;

    public MessageAction(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @Override
    public Boolean execute() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(this.title);
        alert.setContentText(this.message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
        return true;
    }

    @Override
    public ActionType getType() {
        return ActionType.DIALOGBOX;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return ActionType.DIALOGBOX.name() + " " + this.title + " " + this.message;
    }

}
