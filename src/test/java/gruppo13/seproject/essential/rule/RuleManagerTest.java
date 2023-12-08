package gruppo13.seproject.essential.rule;
import gruppo13.seproject.essential.Status;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.trigger.Trigger;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class RuleManagerTest {

    @Test
    void testAddRule() {
        RuleManager ruleManager = RuleManager.getInstance();
        RequestPublisher requestPublisherMock = mock(RequestPublisher.class);
        ruleManager.setRequestPublisher(requestPublisherMock);

        Rule rule = createTestRule();
        ruleManager.addRule(rule);

        List<Rule> rules = ruleManager.getRules();

        assertEquals(1, rules.size());
        assertTrue(rules.contains(rule));
        verify(requestPublisherMock, times(1)).publishRequest(RequestFactory.createListUpdateRequest());
    }

    @Test
    void testRemoveRule() {
        RuleManager ruleManager = RuleManager.getInstance();
        RequestPublisher requestPublisherMock = mock(RequestPublisher.class);
        ruleManager.setRequestPublisher(requestPublisherMock);

        Rule rule = createTestRule();
        ruleManager.addRule(rule);

        List<Rule> rules = ruleManager.getRules();

        assertEquals(1, rules.size());
        assertTrue(rules.contains(rule));

        ruleManager.removeRule(rule);
        rules = ruleManager.getRules();

        assertEquals(0, rules.size());
        assertTrue(rules.isEmpty());
        verify(requestPublisherMock, times(2)).publishRequest(RequestFactory.createListUpdateRequest());
    }

    @Test
    void testSetState() {
        RuleManager ruleManager = RuleManager.getInstance();
        RequestPublisher requestPublisherMock = mock(RequestPublisher.class);
        ruleManager.setRequestPublisher(requestPublisherMock);

        Rule rule = createTestRule();
        ruleManager.addRule(rule);

        List<Rule> rules = ruleManager.getRules();

        assertEquals(1, rules.size());
        assertTrue(rules.contains(rule));

        Status newStatus = Status.NOTACTIVE;
        ruleManager.setStatus(rule, newStatus);

        assertEquals(newStatus, rule.getStatus());

        for (Action action : rule.getActions()) {
            assertEquals(newStatus, action.getState());
        }

        verify(requestPublisherMock, times(2)).publishRequest(RequestFactory.createListUpdateRequest());
    }

    private Rule createTestRule() {
        List<Action> actions = new ArrayList<>();
        // Create actions and add them to the list

        return new Rule("TestRule", actions, Mockito.mock(Trigger.class), Status.ACTIVE);
    }
}
