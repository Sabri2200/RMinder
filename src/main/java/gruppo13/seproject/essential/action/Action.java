package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.ActionException.ActionException;
import javafx.beans.Observable;

public abstract class Action extends ActionPerformer {
    public abstract ActionType getType();
    public abstract State getState();
    public abstract void setState(State state);
    public abstract String toString();
    public abstract void execute() throws ActionException;
}
