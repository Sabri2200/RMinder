package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Exception.ActionException;

public interface Action {
    public void execute() throws ActionException;
    public ActionType getType();
    public State getState();
    public void setState(State state);
}