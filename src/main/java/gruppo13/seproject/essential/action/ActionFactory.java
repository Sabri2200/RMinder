package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.action.type.AudioAction;
import gruppo13.seproject.essential.action.type.DialogBoxAction;
import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ActionFactory {
    public static Action createAction(Map.Entry<ActionType, List<String>> action) {
        return switch (action.getKey()) {
            case DIALOGBOX -> createDialogBoxAction(action.getValue());
            case MP3PLAYER -> createAudioAction(action.getValue());
        };
    }

    private static DialogBoxAction createDialogBoxAction(List<String> params) {
        try {
            String title = params.get(0);
            String content = params.get(1);
            String message = params.get(2);

            return new DialogBoxAction(title, content, message);
        } catch (Exception e) {
            RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(e));
        }

        return null;
    }

    private static AudioAction createAudioAction(List<String> params) {
        try {
            File file = new File(params.get(0));

            return new AudioAction(file);
        } catch (Exception e) {
            RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(e));
        }

        return null;
    }
}
