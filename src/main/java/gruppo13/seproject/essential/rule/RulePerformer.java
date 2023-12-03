package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.*;
import gruppo13.seproject.essential.action.ActionException.ActionException;

import java.util.ArrayList;
import java.util.List;

public class RulePerformer implements ActionSubject, ActionPerformer {
    private RuleManager ruleManager;
    private List<ActionObserver> observers = new ArrayList<>();

    public RulePerformer(RuleManager ruleManager) {
        this.ruleManager = ruleManager;
    }

    @Override
    public void execute() throws ActionException {
        List<Rule> rules = ruleManager.getRules();
        if (!rules.isEmpty()) {
            for (Rule rule : rules) {
                if(rule.getState().equals(State.ACTIVE)) {
                    if (rule.getTrigger().verify()) {
                            ruleManager.setState(rule, State.NOTACTIVE);
                            for (Action a : rule.getActions()) {
                                try {
                                    if (a.getState().equals(State.ACTIVE)) {
                                        a.setState(State.NOTACTIVE);
                                        a.execute();
                                        notifyObservers(a);
                                    }
                                } catch (ActionException e) {
                                    notifyErrorObservers(a, e);
                                }
                            }
                        };
                }
            }
        }
    }

    @Override
    public void registerObserver(ActionObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(ActionObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Action a) {
        for (ActionObserver o : observers) {
            o.execute(a);
        }
    }

    private void notifyErrorObservers(Action a, Exception e) {
        for (ActionObserver observer : observers) {
            observer.notifyError(a, e);
        }
    }
}
