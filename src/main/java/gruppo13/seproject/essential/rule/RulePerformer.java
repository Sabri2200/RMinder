package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
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

/*
The `RulePerformer` class is a key component in a Java application designed to execute `Rule` objects based on their triggers and states.

1. Singleton Pattern Implementation:
   - The class uses a static nested class (`RulePerformerInstanceHolder`) to implement the singleton pattern, ensuring that only one instance of `RulePerformer` is created and used throughout the application. The `getInstance()` method provides access to this instance.

2. Rule Execution:
   - The `execute()` method is the core functionality of this class. It iterates through all the `Rule` objects obtained from `RuleManager` and executes the actions of each active rule whose trigger condition is met.

3. Trigger Verification and Action Execution:
   - For each active rule, the method checks if the rule's trigger condition (`rule.getTrigger().verify()`) is true. If so, it executes all the actions associated with that rule.
   - Each action is executed within a try-catch block to handle any exceptions that might occur during execution.

4. Exception Handling:
   - If an exception occurs while executing an action (such as `ActionException`, `RejectedExecutionException`, etc.), the exception is published using `RequestPublisher`.

5. Rule State Management:
   - After executing a rule's actions, if the rule has a `nextActivation` time set, the method calculates the next activation time and creates a new trigger for it.
   - A new rule is then created with this trigger and added to the `RuleManager`.
   - The original rule's state is set to `INACTIVE` after execution.

6. Request Publisher Integration:
   - The `RulePerformer` has a `RequestPublisher` instance, obtained via `RequestPublisher.getInstance()`, which is used to publish requests, likely for logging execution details or handling exceptions.

7. Rule and Trigger Factories:
   - The class uses `RuleFactory` and `TriggerFactory` to create new rules and triggers, respectively, especially for handling rules with `nextActivation` times.

8. Time Calculation for Next Activation:
   - The `getStrings(Rule rule)` method calculates the next activation time for a rule based on the current time and the `nextActivation` value of the rule.
*/

public class RulePerformer {
    private RuleManager ruleManager;
    private RequestPublisher requestPublisher;

    public RulePerformer() {
        this.ruleManager = RuleManager.getInstance();
        this.requestPublisher = RequestPublisher.getInstance();
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
                if(rule.getState().equals(RuleState.ACTIVE)) {
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
                        ruleManager.setState(rule, RuleState.INACTIVE);
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
