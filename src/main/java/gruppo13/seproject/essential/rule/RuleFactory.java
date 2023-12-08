package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.Status;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.trigger.Trigger;

import java.util.List;

public class RuleFactory {
    public static Rule createRule(String name, List<Action> actions, Trigger trigger, Status status) {
        if (name != null || actions != null || trigger != null || status != null) {
            if (!actions.isEmpty()) {
                return new Rule(name, actions, trigger, status);
            }
        }
        RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in creating this rule. ")));
        return null;
    }

    public static Rule createRule(String name, List<Action> actions, Trigger trigger, int nextActivation, Status status) {
        if (name != null || actions != null || trigger != null || nextActivation != 0 || status != null) {
            if (!actions.isEmpty()) {
                return new Rule(name, actions, trigger, nextActivation, status);
            }
        }
        RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in creating this rule. ")));
        return null;
    }
}
