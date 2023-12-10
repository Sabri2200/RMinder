package gruppo13.seproject.essential.rule;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.type.DialogBoxAction;
import gruppo13.seproject.essential.rule.Rule;
import gruppo13.seproject.essential.rule.RuleStatus;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.type.ClockTrigger;
import org.junit.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

public class RuleTest {

    @Test
    public void testRuleConstructionWithDefaultNextActivation() {
        // Test data
        String ruleName = "TestRule";
        List<Action> actions = List.of(new DialogBoxAction("title","content","message"));
        Trigger trigger = new ClockTrigger(LocalTime.MIDNIGHT);
        RuleStatus ruleStatus = RuleStatus.ACTIVE;

        // Create a rule with default nextActivation
        Rule rule = new Rule(ruleName, actions, trigger, ruleStatus);

        // Assert
        assertNotNull(rule);
        assertEquals(ruleName, rule.getName());
        assertEquals(actions, rule.getActions());
        assertEquals(trigger, rule.getTrigger());
        assertEquals(ruleStatus, rule.getRuleStatus());
        assertEquals(0, rule.getNextActivation());
    }

    @Test
    public void testRuleConstructionWithCustomNextActivation() {
        // Test data
        String ruleName = "TestRule";
        List<Action> actions = List.of(new DialogBoxAction("title","content","message"));
        Trigger trigger = new ClockTrigger(LocalTime.MIDNIGHT);
        int customNextActivation = 5;
        RuleStatus ruleStatus = RuleStatus.ACTIVE;

        // Create a rule with custom nextActivation
        Rule rule = new Rule(ruleName, actions, trigger, customNextActivation, ruleStatus);

        // Assert
        assertNotNull(rule);
        assertEquals(ruleName, rule.getName());
        assertEquals(actions, rule.getActions());
        assertEquals(trigger, rule.getTrigger());
        assertEquals(ruleStatus, rule.getRuleStatus());
        assertEquals(customNextActivation, rule.getNextActivation());
    }

    @Test
    public void testRuleStateChange() {
        // Test data
        String ruleName = "TestRule";
        List<Action> actions = List.of(new DialogBoxAction("title","content","message"));
        Trigger trigger = new ClockTrigger(LocalTime.MIDNIGHT);
        RuleStatus initialRuleStatus = RuleStatus.ACTIVE;
        RuleStatus newRuleStatus = RuleStatus.INACTIVE;

        // Create a rule
        Rule rule = new Rule(ruleName, actions, trigger, initialRuleStatus);

        // Verify the initial state
        assertEquals(initialRuleStatus, rule.getRuleStatus());

        // Change the rule state
        rule.setRuleStatus(newRuleStatus);

        // Verify the new state
        assertEquals(newRuleStatus, rule.getRuleStatus());
    }


}
