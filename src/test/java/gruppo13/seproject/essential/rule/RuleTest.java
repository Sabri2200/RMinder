package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.rule.Rule;
import gruppo13.seproject.essential.trigger.*;
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
        State mockState = mock(State.class);

        // Create a list of actions
        List<Action> actions = new ArrayList<>();
        actions.add(mockAction);

        // Create a Rule instance
        Rule rule = new Rule("TestRule", actions, mockTrigger, mockState);

        // Test rule attributes
        assertEquals("TestRule", rule.getName());
        assertEquals(actions, rule.getActions());
        assertEquals(mockTrigger, rule.getTrigger());
        assertEquals(mockState, rule.getState());
    }

    @Test
    void testRuleSetState() {
        // Create mock objects for Action, Trigger, and State
        Action mockAction = mock(Action.class);
        Trigger mockTrigger = mock(Trigger.class);
        State mockState = mock(State.class);

        // Create a list of actions
        List<Action> actions = new ArrayList<>();
        actions.add(mockAction);

        // Create a Rule instance
        Rule rule = new Rule("TestRule", actions, mockTrigger, mockState);

        // Set a new state and test
        State newState = mock(State.class);
        rule.setState(newState);

        // Verify that the state is updated
        assertEquals(newState, rule.getState());
    }
}
