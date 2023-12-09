package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.action.exception.ActionException;

public interface Action {
    ActionType getType();
    String toString();
    void execute() throws ActionException;
}