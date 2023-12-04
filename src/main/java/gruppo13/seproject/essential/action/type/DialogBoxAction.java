package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionType;

public class DialogBoxAction implements Action {
    private String title, content, message;
    private State state;

    public DialogBoxAction(String title, String content, String message) {
        this.title = title;
        this.content = content;
        this.message = message;
        this.state = State.ACTIVE;
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
    public void execute() {
        System.out.println("executed dialogbox");
    }

    @Override
    public ActionType getType() {
        return ActionType.DIALOGBOX;
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return getType() + " " + getTitle() + " " + getContent() + " " + getMessage();
    }

}