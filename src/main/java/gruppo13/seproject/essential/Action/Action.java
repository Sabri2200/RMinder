package gruppo13.seproject.essential.Action;

public abstract class Action {
    ActionType type;

    public Action(ActionType type) {
        this.type = type;
    }

    public abstract void execute();

    public ActionType getType() {
        return type;
    }
}

