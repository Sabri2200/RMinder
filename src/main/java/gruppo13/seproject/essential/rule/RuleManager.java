package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;

import java.util.ArrayList;
import java.util.List;

/*
The `RuleManager` class is a central component in a Java application for managing a collection of `Rule` objects.

1. Singleton Pattern Implementation:
   - The class uses a static nested class (`RuleManagerInstanceHolder`) to implement the singleton pattern. This ensures that only one instance of `RuleManager` is created and used throughout the application. The `getInstance()` method provides access to this instance.

2. Rule Collection Management:
   - The `RuleManager` maintains a list of `Rule` objects (`rules`), providing a centralized point for managing these rules.

3. Adding and Removing Rules:
   - `addRule(Rule rule)`: Adds a new `Rule` to the list and then publishes a list update request using `RequestPublisher`. This could be used to notify other parts of the application about the change in the rules list.
   - `removeRule(Rule rule)`: Removes a `Rule` from the list and publishes a list update request, similar to the `addRule` method.

4. Setting Rule State:
   - `setState(Rule rule, RuleStatus ruleStatus)`: Changes the state of a specified `Rule` and publishes a list update request. This method allows for the activation or deactivation of rules dynamically.

5. Request Publisher Integration:
   - The `RuleManager` has a `RequestPublisher` instance, obtained via `RequestPublisher.getInstance()`, which is used to publish requests. This is likely for notifying other components of the application about changes in the rules list.

6. Retrieving Rules:
   - `getRules()`: Returns the current list of rules. This method provides read access to other parts of the application that might need to query the existing rules.
*/
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


    public void setStatus(Rule rule, RuleStatus status) {
        rule.setRuleStatus(status);
        requestPublisher.publishRequest(RequestFactory.createListUpdateRequest());
    }

    public void setRequestPublisher(RequestPublisher requestPublisher) {
        this.requestPublisher = requestPublisher;
    }
}
