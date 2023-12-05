package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.*;
import gruppo13.seproject.essential.action.ActionObserver.ActionObserver;
import gruppo13.seproject.essential.action.ActionObserver.ActionSubject;
import gruppo13.seproject.essential.action.exception.ActionException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.RejectedExecutionException;

public class RulePerformer implements ActionPerformer, ActionSubject {
    private RuleManager ruleManager;
    private List<ActionObserver> observers = new ArrayList<>();

    public RulePerformer() {
        this.ruleManager = RuleManager.getInstance();
    }

    @Override
    public void execute() {
        List<Rule> rules = ruleManager.getRules();
        if (!rules.isEmpty()) {
            for (Rule rule : rules) {
                if(rule.getState().equals(State.ACTIVE)) {
                    if (rule.getTrigger().verify()) {
                            for (Action a : rule.getActions()) {
                                try {
                                    if (a.getState().equals(State.ACTIVE)) {
                                        a.setState(State.NOTACTIVE);
                                        a.execute();
                                        notifyObservers(a);
                                    }
                                } catch (ActionException | RejectedExecutionException | NullPointerException | IllegalArgumentException |
                                         CancellationException e) {
                                    notifyErrorObservers(a, e);
                                }
                            }
                        ruleManager.setState(rule, State.NOTACTIVE);
                    }
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
    public synchronized void notifyObservers(Action a) {
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
