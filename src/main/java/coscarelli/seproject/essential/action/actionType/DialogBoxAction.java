package coscarelli.seproject.essential.action.actionType;

import coscarelli.seproject.essential.State;
import coscarelli.seproject.essential.action.Action;
import coscarelli.seproject.essential.action.ActionType;

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
        System.out.println("a message");
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

}
