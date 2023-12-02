package coscarelli.seproject.essential.rule;

import coscarelli.seproject.essential.State;
import coscarelli.seproject.essential.action.Action;
import coscarelli.seproject.essential.trigger.Trigger;

import java.util.List;

public class Rule {
    private String name;
    private List<Action> actions;
    private Trigger trigger;
    private State state;

    public Rule(String name, List<Action> actions, Trigger trigger, State state) {
        this.name = name;
        this.actions = actions;
        this.trigger = trigger;
        this.state = state;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
