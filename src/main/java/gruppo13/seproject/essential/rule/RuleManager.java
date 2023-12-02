package gruppo13.seproject.essential.rule;

import java.util.ArrayList;
import java.util.List;

public class RuleManager {
    private List<Rule> rules;

    public RuleManager() {
        rules = new ArrayList<>();
    }

    public List<Rule> getRules() {
        return this.rules;
    }

    public void addRule(Rule rule) {
        rules.add(rule);
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
