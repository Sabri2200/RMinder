package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.action.ActionException.ActionException;
import gruppo13.seproject.essential.action.ActionPerformer;
import gruppo13.seproject.essential.rule.ListObserver.ListObserver;
import gruppo13.seproject.essential.rule.ListObserver.ListSubject;
import gruppo13.seproject.essential.State;

import java.util.ArrayList;
import java.util.List;

public class RuleManager implements ListSubject {
    private List<Rule> rules;
    private List<ListObserver> observers;

    public RuleManager() {
        rules = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public List<Rule> getRules() {
        return this.rules;
    }


    public void addRule(Rule rule) {
        rules.add(rule);
        notifyObservers();
    }

    public void removeRule(Rule rule) {}

    public void editRule(Rule oldRule, Rule newRule) {}

    public void setState(Rule rule, State state) {
        rule.setState(state);
        notifyObservers();
    }


    @Override
    public void registerObserver(ListObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(ListObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (ListObserver listObserver : observers) {
            listObserver.update();
        }
    }

}
