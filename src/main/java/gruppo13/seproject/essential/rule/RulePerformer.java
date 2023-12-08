package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.exception.ActionException;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.RejectedExecutionException;

public class RulePerformer {
    private RuleManager ruleManager;
    private RequestPublisher requestPublisher;

    public RulePerformer() {
        this.ruleManager = RuleManager.getInstance();
        this.requestPublisher = RequestPublisher.getInstance();
    }
    // Concrete constructor for testing purposes
    public RulePerformer(RuleManager ruleManager, RequestPublisher requestPublisher) {
        this.ruleManager = ruleManager;
        this.requestPublisher = requestPublisher;
    }

    private static final class RulePerformerInstanceHolder {
        private static final RulePerformer rulePerformerInstance = new RulePerformer();
    }

    public static RulePerformer getInstance() {
        return RulePerformerInstanceHolder.rulePerformerInstance;
    }

    public void execute() {
        List<Rule> rules = ruleManager.getRules();
        if (!rules.isEmpty()) {
            for (Rule rule : rules) {
                if(rule.getState().equals(State.ACTIVE)) {
                    if (rule.getTrigger().verify()) {
                            for (Action a : rule.getActions()) {
                                try {
                                    if (a.getState().equals(State.ACTIVE)) {
                                        a.setState(State.INACTIVE);
                                        a.execute();
                                        requestPublisher.publishRequest(RequestFactory.createExecutionRequest(a));
                                    }
                                } catch (ActionException | RejectedExecutionException | NullPointerException | IllegalArgumentException |
                                         CancellationException e) {
                                    requestPublisher.publishRequest(RequestFactory.createExceptionRequest(e));
                                }
                            }
                        ruleManager.setState(rule, State.INACTIVE);
                    }
                }
            }
        }
    }

}
