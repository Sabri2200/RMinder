package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.trigger.Trigger;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class RuleManagerTest {

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
    void testSetStatus() {
        RuleManager ruleManager = RuleManager.getInstance();
        RequestPublisher requestPublisherMock = mock(RequestPublisher.class);
        ruleManager.setRequestPublisher(requestPublisherMock);

        Rule rule = createTestRule();
        ruleManager.addRule(rule);

        List<Rule> rules = ruleManager.getRules();

        assertEquals(1, rules.size());
        assertTrue(rules.contains(rule));

        RuleStatus newStatus = RuleStatus.INACTIVE;
        ruleManager.setStatus(rule, newStatus);

        assertEquals(newStatus, rule.getRuleStatus());

        verify(requestPublisherMock, times(2)).publishRequest(RequestFactory.createListUpdateRequest());
    }
    @Test
    public Rule createTestRule() {
        List<Action> actions = new ArrayList<>();
        // Create actions and add them to the list
        return new Rule("TestRule", actions, Mockito.mock(Trigger.class), RuleStatus.ACTIVE);
    }
}
