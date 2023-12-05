package gruppo13.seproject.Service.GUIExcecutor;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionObserver.ActionObserver;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.type.DialogBoxAction;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

import java.util.Objects;

public class GUIExecutor implements ActionObserver {
    public GUIExecutor() {}

    @Override
    public synchronized void execute(Action a) {
                try {
                    if (Objects.requireNonNull(a.getType()) == ActionType.DIALOGBOX) {
                        DialogBoxExecutor.run((DialogBoxAction) a);
                    }
                } catch(Exception e) {
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
            alert.initModality(Modality.NONE);
            alert.showAndWait();
        });
        Platform.exit();
    }
}