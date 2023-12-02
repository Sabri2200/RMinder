package coscarelli.seproject.GUIExcecutor;

import coscarelli.seproject.essential.action.Exception.ActionException;
import coscarelli.seproject.essential.action.Exception.AudioActionException;
import coscarelli.seproject.essential.action.actionType.AudioAction;

public class AudioExecutor {
    public static void run(AudioAction a) throws ActionException {
        a.execute();
    }
}
