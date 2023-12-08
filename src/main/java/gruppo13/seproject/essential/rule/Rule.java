package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.Status;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.trigger.Trigger;

import java.util.List;

public class Rule {
    private String name;
    private List<Action> actions;
    private Trigger trigger;
    private Status status;
    private int nextActivation;

    public Rule(String name, List<Action> actions, Trigger trigger, Status status) {
        this.name = name;
        this.actions = actions;
        this.trigger = trigger;
        this.nextActivation = 0;
        this.status = status;
    }

    public Rule(String name, List<Action> actions, Trigger trigger, int nextActivation, Status status) {
        this.name = name;
        this.actions = actions;
        this.trigger = trigger;
        this.nextActivation = nextActivation;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public List<Action> getActions() {
        return actions;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getNextActivation() {
        return nextActivation;
    }
}
