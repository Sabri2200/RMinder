package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.exception.ActionException;

public interface Action extends ActionPerformer {
    public ActionType getType();
    public State getState();
    public void setState(State state);
    public String toString();
    public void execute() throws ActionException;
}
