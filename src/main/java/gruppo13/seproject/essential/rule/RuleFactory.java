package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.trigger.Trigger;

import java.util.List;

public class RuleFactory {
    public static Rule createRule(String name, List<Action> actions, Trigger trigger, State state) {
        if (name != null || actions != null || trigger != null || state != null) {
            if (!actions.isEmpty()) {
                return new Rule(name, actions, trigger, state);
            }
        }
        RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in creating this rule. ")));
        return null;
    }

    public static Rule createRule(String name, List<Action> actions, Trigger trigger, int nextActivation, State state) {
        if (name != null || actions != null || trigger != null || nextActivation != 0 || state != null) {
            if (!actions.isEmpty()) {
                return new Rule(name, actions, trigger, nextActivation, state);
            }
        }
        RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in creating this rule. ")));
        return null;
    }
}
