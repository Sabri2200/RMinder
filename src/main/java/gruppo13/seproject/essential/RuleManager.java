package gruppo13.seproject.essential;

import gruppo13.seproject.essential.model.Action.Action;

import java.util.ArrayList;
import java.util.List;

public class RuleManager {
    private List<Rule> rules;

    public RuleManager() {
        rules = new ArrayList<>();
    }

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
