package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.trigger.Trigger;

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
