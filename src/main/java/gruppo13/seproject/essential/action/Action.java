package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.action.exception.ActionException;

public interface Action {
    public ActionType getType();
    public String toString();
    public void execute() throws ActionException;
}