package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.trigger.Trigger;

import java.util.List;

/*
The `Rule` class is a core component of a Java application, representing a rule that consists of actions to be executed based on a trigger.

1. Attributes:
   - `name`: A `String` representing the name of the rule.
   - `actions`: A `List<Action>` representing the actions to be executed when the rule is triggered.
   - `trigger`: A `Trigger` object that defines the condition under which the actions should be executed.
   - `ruleState`: An enumeration (`RuleState`) that represents the current state of the rule (e.g., active, inactive).
   - `nextActivation`: An `int` that could represent the time or condition for the next activation of the rule.

2. Constructors:
   - The class provides two constructors:
     - The first constructor initializes the rule with a name, list of actions, trigger, and rule state, setting `nextActivation` to 0 by default.
     - The second constructor additionally allows specifying the `nextActivation` parameter. This parameter will be helpful if the actions must be executed again after `nextActivation` minutes.

3. Getters:
   - The class provides getter methods for all its attributes (`getName`, `getActions`, `getTrigger`, `getState`, `getNextActivation`), allowing other parts of the application to access these properties.

4. Set State:
   - The `setState(RuleState ruleState)` method allows changing the state of the rule, which can be used to activate or deactivate the rule based on certain conditions.
*/

public class Rule {
    private String name;
    private List<Action> actions;
    private Trigger trigger;
    private RuleState ruleState;
    private int nextActivation;

    public Rule(String name, List<Action> actions, Trigger trigger, RuleState ruleState) {
        this.name = name;
        this.actions = actions;
        this.trigger = trigger;
        this.nextActivation = 0;
        this.ruleState = ruleState;
    }

    public Rule(String name, List<Action> actions, Trigger trigger, int nextActivation, RuleState ruleState) {
        this.name = name;
        this.actions = actions;
        this.trigger = trigger;
        this.nextActivation = nextActivation;
        this.ruleState = ruleState;
    }

    public String getName() {
        return name;
    }

    public List<Action> getActions() {
        return actions;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public RuleState getRuleState() {
        return ruleState;
    }

    public void setRuleState(RuleState ruleState) {
        this.ruleState = ruleState;
    }

    public int getNextActivation() {
        return nextActivation;
    }
}
