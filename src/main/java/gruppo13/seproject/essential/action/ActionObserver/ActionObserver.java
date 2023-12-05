package gruppo13.seproject.essential.action.ActionObserver;

import gruppo13.seproject.essential.action.Action;

import java.util.Observer;

public interface ActionObserver {
    public void execute(Action a);
    public void notifyError(Action a, Exception e);

}
