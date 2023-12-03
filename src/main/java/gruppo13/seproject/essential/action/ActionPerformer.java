package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.action.exception.ActionException;

public interface ActionPerformer {
    public abstract void execute() throws ActionException;
}
