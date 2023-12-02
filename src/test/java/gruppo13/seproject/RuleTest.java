package gruppo13.seproject;

import gruppo13.seproject.essential.action.action.Action;
import gruppo13.seproject.essential.model.Rule;
import gruppo13.seproject.essential.model.action.Action;
import gruppo13.seproject.essential.model.action.MessageAction;
import gruppo13.seproject.essential.model.trigger.ClockTrigger;
import gruppo13.seproject.essential.model.trigger.Trigger;
import javafx.beans.property.SimpleBooleanProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RuleTest {

    private Rule rule;

    @BeforeEach
    public void setUp() {
        // Configura uno scenario di base prima di ogni test
        List<Action> actions = new ArrayList<>();
        actions.add(new MessageAction("title","test"));

        Trigger trigger = new ClockTrigger(LocalTime.MIDNIGHT);
        SimpleBooleanProperty state = new SimpleBooleanProperty(true);

        rule = new Rule("TestRule", actions, trigger, state);
    }

    @Test
    public void testExecute() {
        // Verifica che l'esecuzione delle azioni imposti lo stato a false
        assertTrue(rule.getState());  // Lo stato dovrebbe essere true prima dell'esecuzione
        rule.execute();
        assertFalse(rule.getState()); // Dovrebbe essere impostato a false dopo l'esecuzione
    }

    @Test
    public void testGetActions() {
        // Verifica che la lista delle azioni restituita sia non nulla e contenga le azioni configurate
        List<Action> actions = rule.getActions();
        assertNotNull(actions);
        assertEquals(1, actions.size());  // Aggiorna questo valore se hai aggiunto più azioni nel setup
    }

    @Test
    public void testGetTrigger() {
        // Verifica che il trigger restituito sia non nullo
        Trigger trigger = rule.getTrigger();
        assertNotNull(trigger);
    }

    @Test
    public void testGetState() {
        // Verifica che lo stato restituito sia lo stesso dello stato configurato nel setup
        assertTrue(rule.getState());
    }

    @Test
    public void testGetName() {
        // Verifica che il nome restituito sia lo stesso del nome configurato nel setup
        assertEquals("TestRule", rule.getName());
    }

    @Test
    public void testSetState() {
        // Verifica che il metodo setState imposti correttamente lo stato
        rule.setState(new SimpleBooleanProperty(false));
        assertFalse(rule.getState());
    }

    @Test
    public void testCompareTo() {
        // Verifica che il metodo compareTo funzioni correttamente
        Rule otherRule = new Rule("AnotherRule", new ArrayList<>(), new ClockTrigger(LocalTime.MIDNIGHT), new SimpleBooleanProperty(true));
        assertTrue(rule.compareTo(otherRule) < 0);
    }

    @Test
    public void testEquals() {
        // Verifica che due regole con lo stesso nome siano considerate uguali
        Rule sameRule = new Rule("TestRule", new ArrayList<>(),  new ClockTrigger(LocalTime.MIDNIGHT), new SimpleBooleanProperty(true));
        assertTrue(rule.equals(sameRule));
    }

    @Test
    public void testHashCode() {
        // Verifica che lo stesso oggetto produca lo stesso hashCode
        Rule sameRule = new Rule("TestRule", new ArrayList<>(),  new ClockTrigger(LocalTime.MIDNIGHT), new SimpleBooleanProperty(true));
        assertEquals(rule.hashCode(), sameRule.hashCode());
    }

    @Test
    public void testToString() {
        Rule rule = new Rule("TestRule", Arrays.asList(new MessageAction("title",",message")), new ClockTrigger(LocalTime.MIDNIGHT), new SimpleBooleanProperty(true));

        String expectedString = "TestRule [], trigger=" + rule.getTrigger().toString() + ", state=true}";
        assertEquals(expectedString, rule.toString());
    }
}
