package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionType;

public class DialogBoxAction implements Action {
    private String title, content, message;

    public DialogBoxAction(String title, String content, String message) {
        this.title = title;
        this.content = content;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void execute() {}

    @Override
    public ActionType getType() {
        return ActionType.DIALOGBOX;
    }

    @Override
    public String toString() {
        return getType() + " " + getTitle() + " " + getContent() + " " + getMessage();
    }

}