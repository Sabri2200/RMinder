package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.Status;
import gruppo13.seproject.essential.action.Action;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import gruppo13.seproject.essential.trigger.Trigger;


class RuleTest {

    @Test
    void testRuleInitialization() {
        // Create mock objects for Action, Trigger, and State
        Action mockAction = mock(Action.class);
        Trigger mockTrigger = mock(Trigger.class);
        Status mockStatus = mock(Status.class);

        // Create a list of actions
        List<Action> actions = new ArrayList<>();
        actions.add(mockAction);

        // Create a Rule instance
        Rule rule = new Rule("TestRule", actions, mockTrigger, mockStatus);

        // Test rule attributes
        assertEquals("TestRule", rule.getName());
        assertEquals(actions, rule.getActions());
        assertEquals(mockTrigger, rule.getTrigger());
        assertEquals(mockStatus, rule.getStatus());
    }

    @Test
    void testRuleSetState() {
        // Create mock objects for Action, Trigger, and State
        Action mockAction = mock(Action.class);
        Trigger mockTrigger = mock(Trigger.class);
        Status mockStatus = mock(Status.class);

        // Create a list of actions
        List<Action> actions = new ArrayList<>();
        actions.add(mockAction);

        // Create a Rule instance
        Rule rule = new Rule("TestRule", actions, mockTrigger, mockStatus);

        // Set a new state and test
        Status newStatus = mock(Status.class);
        rule.setStatus(newStatus);

        // Verify that the state is updated
        assertEquals(newStatus, rule.getStatus());
    }
}
