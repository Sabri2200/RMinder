package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.trigger.Trigger;

import java.util.List;

/*
The `RuleFactory` class is a factory class in a Java application designed to create `Rule` objects.

1. Rule Creation Methods:
   - The class provides two static methods to create `Rule` objects, each catering to different sets of parameters:
     - `createRule(String name, List<Action> actions, Trigger trigger, RuleState ruleState)`: Creates a `Rule` object with the specified name, actions, trigger, and state. This method does not set the `nextActivation` attribute, implying it defaults to 0.
     - `createRule(String name, List<Action> actions, Trigger trigger, int nextActivation, RuleState ruleState)`: Similar to the first method but also includes the `nextActivation` parameter, allowing for more detailed rule configuration.

2. Parameter Validation:
   - Both methods perform validation checks on the provided parameters. They ensure that the name, actions, trigger, and rule state are not null and that the actions list is not empty. The second method also checks if `nextActivation` is not 0.

3. Error Handling:
   - If the validation fails (e.g., if the actions list is empty or other parameters are null), the methods do not create a `Rule` object. Instead, they publish an exception request using `RequestPublisher.getInstance().publishRequest(...)`, indicating an error in rule creation.

4. Use of RequestPublisher:
   - The `RequestPublisher` is used for error reporting. When rule creation fails, an exception request is created using `RequestFactory.createExceptionRequest(new Exception("Error in creating this rule. "))` and published. This suggests integration with a larger system for handling errors and exceptions.
*/

public class RuleFactory {
    public static Rule createRule(String name, List<Action> actions, Trigger trigger, RuleStatus ruleStatus) {
        if (name != null || actions != null || trigger != null || ruleStatus != null) {
            if (!actions.isEmpty()) {
                return new Rule(name, actions, trigger, ruleStatus);
            }
        }
        RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in creating this rule. ")));
        return null;
    }

    public static Rule createRule(String name, List<Action> actions, Trigger trigger, int nextActivation, RuleStatus ruleStatus) {
        if (name != null || actions != null || trigger != null || nextActivation != 0 || ruleStatus != null) {
            if (!actions.isEmpty()) {
                return new Rule(name, actions, trigger, nextActivation, ruleStatus);
            }
        }
        RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in creating this rule. ")));
        return null;
    }

}
