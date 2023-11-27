package gruppo13.seproject.essential;

import gruppo13.seproject.essential.Action.Action;
import gruppo13.seproject.essential.Trigger.Trigger;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Objects;

public class Rule implements Comparable<Rule> {
    private String name;
    private Action[] actions;
    private Trigger trigger;
    private SimpleBooleanProperty state = new SimpleBooleanProperty();

    public void setName(String name) {
        this.name = name;
    }

    public Rule(String name, Action[] actions, Trigger trigger, SimpleBooleanProperty state) {
        this.name = name;
        this.actions = actions;
        this.trigger = trigger;
        this.state = state;
    }

    public void execute() {
        for (Action a : actions) {
            if (a != null) {
                a.execute();
            }
        }
        setState(new SimpleBooleanProperty(false));
    }

    public Action[] getActions() {
        return actions;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public Boolean getState() {
        return this.state.getValue();
    }

    public String getName() {
        return name;
    }

    public void setState(SimpleBooleanProperty state) {
        this.state = state;
    }

    @Override
    public int compareTo(Rule rule) {
        return this.name.compareTo(rule.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(name, rule.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
