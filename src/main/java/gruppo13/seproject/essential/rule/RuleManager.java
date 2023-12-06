package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;

import java.util.ArrayList;
import java.util.List;

public class RuleManager {
    private List<Rule> rules;
    private RequestPublisher requestPublisher;

    private RuleManager() {
        rules = new ArrayList<>();
        this.requestPublisher = RequestPublisher.getInstance();
    }

    private static final class RuleManagerInstanceHolder {
        private static final RuleManager ruleManagerInstance = new RuleManager();
    }

    public static RuleManager getInstance() {
        return RuleManagerInstanceHolder.ruleManagerInstance;
    }

    public List<Rule> getRules() {
        return this.rules;
    }

    public void addRule(Rule rule) {
        rules.add(rule);
        requestPublisher.publishRequest(RequestFactory.createListUpdateRequest());
    }

    public void removeRule(Rule rule) {
        rules.remove(rule);
        requestPublisher.publishRequest(RequestFactory.createListUpdateRequest());
    }

    public void setState(Rule rule, State state) {
        rule.setState(state);

        for (Action a : rule.getActions()) {
            a.setState(state);
        }

        requestPublisher.publishRequest(RequestFactory.createListUpdateRequest());
    }

}
