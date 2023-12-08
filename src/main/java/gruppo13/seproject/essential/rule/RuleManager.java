package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.Status;

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


    public void setStatus(Rule rule, Status status) {
        rule.setStatus(status);
        requestPublisher.publishRequest(RequestFactory.createListUpdateRequest());
    }

    public void setRequestPublisher(RequestPublisher requestPublisher) {
        this.requestPublisher = requestPublisher;
    }
}
