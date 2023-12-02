package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionObserver;
import gruppo13.seproject.essential.action.ActionSubject;
import gruppo13.seproject.essential.action.Exception.ActionException;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RuleService extends TimerTask implements ActionSubject {
    private RuleManager ruleManager;
    private ExecutorService executorService;
    private List<ActionObserver> observers = new ArrayList<>();

    public RuleService(RuleManager ruleManager) {
        this.ruleManager = ruleManager;
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        System.out.println("2");
        List<Rule> rules = ruleManager.getRules();
        if (!rules.isEmpty()) {
            for (Rule rule : rules) {
                if(rule.getState().equals(State.ACTIVE)) {
                    if (rule.getTrigger().verify()) {
                        executorService.submit(() -> {
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
                            rule.setState(State.NOTACTIVE);
                        });
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
    public void notifyObservers(Action a) {
        for (ActionObserver actionObserver : observers) {
            actionObserver.execute(a);
        }
    }

    private void notifyErrorObservers(Action a, Exception e) {
        for (ActionObserver observer : observers) {
            observer.notifyError(a, e);
        }
    }
}
