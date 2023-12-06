package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.trigger.Trigger;

import java.util.List;

public class RuleFactory {
    public static Rule createRule(String name, List<Action> actions, Trigger trigger, State state) {
        if (name != null || actions != null || trigger != null || state != null) {
            if (!actions.isEmpty()) {
                return new Rule(name, actions, trigger, state);
            }
        }
        return null;
    }
}
