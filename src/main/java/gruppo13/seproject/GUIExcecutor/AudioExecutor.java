package gruppo13.seproject.GUIExcecutor;

import gruppo13.seproject.essential.action.Exception.ActionException;
import gruppo13.seproject.essential.action.actionType.AudioAction;

public class AudioExecutor {
    public static void run(AudioAction a) throws ActionException {
        a.execute();
    }
}
