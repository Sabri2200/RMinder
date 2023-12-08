package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.exception.ActionException;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.TriggerFactory;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                                    a.execute();
                                    requestPublisher.publishRequest(RequestFactory.createExecutionRequest(a));
                                } catch (ActionException | RejectedExecutionException | NullPointerException | IllegalArgumentException |
                                         CancellationException e) {
                                    requestPublisher.publishRequest(RequestFactory.createExceptionRequest(e));
                                }
                            }
                            if (rule.getNextActivation() != 0) {
                                List<String> activationTriggerParams = getStrings(rule);
                                System.out.println(activationTriggerParams);

                                Trigger activationTrigger = TriggerFactory.createTrigger(Map.entry(rule.getTrigger().getType(), activationTriggerParams));

                                Rule activationRule = RuleFactory.createRule(rule.getName() + "*", rule.getActions(), activationTrigger, rule.getState());
                                ruleManager.addRule(activationRule);
                            }
                        ruleManager.setState(rule, State.INACTIVE);
                    }
                }
            }
        }
    }

    private static List<String> getStrings(Rule rule) {
        LocalTime newTime = LocalTime.now().plusMinutes(rule.getNextActivation());

        String hourString = String.valueOf(newTime.getHour());
        String minuteString = String.valueOf(newTime.getMinute());

        List<String> activationTriggerParams = new ArrayList<>();
        activationTriggerParams.add(hourString + ":" + minuteString);
        return activationTriggerParams;
    }

}
