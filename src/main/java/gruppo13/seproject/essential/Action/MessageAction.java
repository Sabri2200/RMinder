package gruppo13.seproject.essential.Action;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MessageAction extends Action {
    String title, message;

    public MessageAction(String title, String message) {
        super(ActionType.DIALOGBOX);
        this.title = title;
        this.message = message;
    }

    @Override
    public void execute() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(this.title);
        alert.setContentText(this.message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
