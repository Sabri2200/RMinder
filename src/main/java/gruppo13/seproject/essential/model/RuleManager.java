package gruppo13.seproject.essential.model;

import gruppo13.seproject.essential.model.action.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class RuleManager{
    private List<Rule> rules;

    public RuleManager() {
        rules = new ArrayList<>();
    }

    // meglio chiamarlo subscribeRule
    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public void removeRule(Rule rule) {
        rules.remove(rule);
    }

    public boolean contains(Rule rule) {
        return rules.contains(rule);
    }

    public boolean isEmpty() {
        return this.rules.isEmpty();
    }

    public List<Rule> getList() {
        return this.rules;
    }

    public void saveRule(String ruleName, List<Action> actions) {

    }

}
