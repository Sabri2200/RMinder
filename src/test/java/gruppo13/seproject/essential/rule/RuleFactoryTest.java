package gruppo13.seproject.essential.rule;
import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.trigger.Trigger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class RuleFactoryTest {

    @Test
    void testCreateRule() {
        // Create mock objects for Action, Trigger, and State
        Action mockAction = mock(Action.class);
        Trigger mockTrigger = mock(Trigger.class);
        State mockState = mock(State.class);
        // Create a list of actions
        List<Action> actions = new ArrayList<>();
        actions.add(mockAction);

        // Create a rule using the factory method
        Rule rule = RuleFactory.createRule("Test Rule", actions, mockTrigger, mockState);

        // Check if the rule properties are set correctly
        assertEquals("Test Rule", rule.getName());
        assertEquals(actions, rule.getActions());
        assertEquals(mockTrigger, rule.getTrigger());
        assertEquals(mockState, rule.getState());
    }

}

