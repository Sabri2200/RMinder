package coscarelli.seproject.essential.rule;

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

}
