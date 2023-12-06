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
        if (action != null) {
            boolean typeMatcher = false;
            for (ActionType type : ActionType.values()) {
                if (action.getKey().equals(type)) {
                    typeMatcher = true;
                    break;
                }
            }

            return typeMatcher ? switch (action.getKey()) {
                case DIALOGBOX -> createDialogBoxAction(action.getValue());
                case MP3PLAYER -> createAudioAction(action.getValue());
                // for testing
                case INVALID_TYPE -> null;
            } : null;
        }
        RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception("Error during creating this action")));
        return null;
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
        RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception("Error during creating this action")));
        return null;
    }

    private static AudioAction createAudioAction(List<String> params) {
        try {
            File file = new File(params.get(0));

            return new AudioAction(file);
        } catch (Exception e) {
            RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(e));
        }
        RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception("Error during creating this action")));
        return null;
    }
}
