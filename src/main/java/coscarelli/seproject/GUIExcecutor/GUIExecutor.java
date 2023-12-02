package coscarelli.seproject.GUIExcecutor;

import coscarelli.seproject.essential.action.Action;
import coscarelli.seproject.essential.action.ActionObserver;
import coscarelli.seproject.essential.action.actionType.AudioAction;
import coscarelli.seproject.essential.action.actionType.DialogBoxAction;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class GUIExecutor implements ActionObserver {
    public GUIExecutor() {}

    @Override
    public void execute(Action a) {
        try {
            switch (a.getType()) {
                case DIALOGBOX:
                    Platform.runLater(() -> DialogBoxExecutor.run((DialogBoxAction) a));
                    break;
            }
        } catch (Exception e) {
            notifyError(a, e);
        }
    }

    @Override
    public void notifyError(Action a, Exception e) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Executing Action");
            alert.setHeaderText("An error occurred in action: " + a.getClass().getSimpleName());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        });
    }
}
