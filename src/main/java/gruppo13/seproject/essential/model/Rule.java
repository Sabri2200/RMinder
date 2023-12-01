package gruppo13.seproject.essential.model;

import gruppo13.seproject.essential.model.action.Action;
import gruppo13.seproject.essential.model.trigger.Trigger;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class Rule implements Comparable<Rule> {
    private String name;
    private List<Action> actions;
    private Trigger trigger;
    private SimpleBooleanProperty state;

    public void setName(String name) {
        this.name = name;
    }

    public Rule(String name, List<Action> actions, Trigger trigger, SimpleBooleanProperty state) {
        this.name = name;
        this.actions = actions;
        this.trigger = trigger;
        this.state = state;
    }

    public void execute() {
        for (Action a : actions) {
            System.out.println(a.toString());
            if (a != null) {
                if (!a.execute()) {
                    System.out.println("error");
                }
            }
        }
        setState(new SimpleBooleanProperty(false));
    }

    public List<Action> getActions() {
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

    // Quando lo stato della regola cambia, notifico gli Observer
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

    @Override
    public String toString() {
        return name + " " + actions +
                ", trigger=" + trigger +
                ", state=" + state +
                '}';
    }

}
