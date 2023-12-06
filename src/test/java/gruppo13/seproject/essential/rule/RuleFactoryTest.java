package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.type.ClockTrigger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class RuleFactoryTest {
    private String name;
    private List<Action> actions;
    private Trigger trigger;
    private State state;


    @BeforeEach
    void setUp() {
        name = "ruleName";
        actions = new ArrayList<>();
        trigger = new ClockTrigger(LocalTime.now());
        state = State.ACTIVE;
    }

    @Test
    void createRule() {
        assertNotNull(RuleFactory.createRule(name, actions, trigger, state));
    }

    @Test
    void createNullRule() {
        assertNull(RuleFactory.createRule(null, null, null, null));
    }

    @Test
    void createEmptyActionsRule() {
        assertNull(RuleFactory.createRule(name, new ArrayList<>(), trigger, state));
    }

}