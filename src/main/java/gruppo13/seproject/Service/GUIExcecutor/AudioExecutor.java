package gruppo13.seproject.Service.GUIExcecutor;

import gruppo13.seproject.essential.action.exception.ActionException;
import gruppo13.seproject.essential.action.type.AudioAction;

public class AudioExecutor {
    public static void run(AudioAction a) throws ActionException {
        a.execute();
    }
}
