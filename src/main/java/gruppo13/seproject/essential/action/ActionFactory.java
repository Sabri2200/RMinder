package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.action.type.AudioAction;
import gruppo13.seproject.essential.action.type.DialogBoxAction;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ActionFactory {
    public static Action createAction(Map.Entry<ActionType, List<String>> action) {
        return switch (action.getKey()) {
            case DIALOGBOX -> createDialogBoxAction(action.getValue());
            case MP3PLAYER -> createAudioAction(action.getValue());
            //for testing
            case INVALID_TYPE -> null;
        };
    }

    private static DialogBoxAction createDialogBoxAction(List<String> params) {
        String title = params.get(0);
        String content = params.get(1);
        String message = params.get(2);

        return new DialogBoxAction(title, content, message);
    }

    private static AudioAction createAudioAction(List<String> params) {
        File file = new File(params.get(0));

        return new AudioAction(file);
    }
}
