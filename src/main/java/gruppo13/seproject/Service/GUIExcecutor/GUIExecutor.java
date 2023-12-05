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

    private GUIExecutor() {}

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

    private static final class GUIExecutorInstanceHolder {
        private static final GUIExecutor guiExecutorInstance = new GUIExecutor();
    }

    public static GUIExecutor getInstance() {
        return GUIExecutor.GUIExecutorInstanceHolder.guiExecutorInstance;
    }

    @Override
    public void notifyError(Action a, Exception e) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (a != null) {
                alert.setTitle("Error Executing Action");
                alert.setHeaderText("An error occurred in action: " + a.getClass().getSimpleName());
            } else {
                alert.setTitle("Error Message");
                alert.setHeaderText("An error occurred");
            }
            alert.setContentText(e.getMessage());
            alert.initModality(Modality.NONE);
            alert.showAndWait();
        });

        if (e.getClass().equals(InterruptedException.class)) {
            Platform.exit();
        }

        //Platform.exit();
    }

    public void showAlert(String title, String header, String content) {
        DialogBoxExecutor.showAlert(title, header, content);
    }

}