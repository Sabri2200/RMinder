package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.action.Action;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import gruppo13.seproject.essential.trigger.Trigger;


public class RuleTest {

    @Test
    void testRuleInitialization() {
        // Create mock objects for Action, Trigger, and State
        Action mockAction = mock(Action.class);
        Trigger mockTrigger = mock(Trigger.class);
        RuleStatus mockStatus = mock(RuleStatus.class);

        // Create a list of actions
        List<Action> actions = new ArrayList<>();
        actions.add(mockAction);

        // Create a Rule instance
        Rule rule = new Rule("TestRule", actions, mockTrigger, mockStatus);

        // Test rule attributes
        assertEquals("TestRule", rule.getName());
        assertEquals(actions, rule.getActions());
        assertEquals(mockTrigger, rule.getTrigger());
        assertEquals(mockStatus, rule.getRuleStatus());
    }

    @Test
    void testRuleSetState() {
        // Create mock objects for Action, Trigger, and State
        Action mockAction = mock(Action.class);
        Trigger mockTrigger = mock(Trigger.class);
        RuleStatus mockStatus = mock(RuleStatus.class);

        // Create a list of actions
        List<Action> actions = new ArrayList<>();
        actions.add(mockAction);

        // Create a Rule instance
        Rule rule = new Rule("TestRule", actions, mockTrigger, mockStatus);

        // Set a new state and test
        RuleStatus newStatus = mock(RuleStatus.class);
        rule.setRuleStatus(newStatus);

        // Verify that the state is updated
        assertEquals(newStatus, rule.getRuleStatus());
    }
}
