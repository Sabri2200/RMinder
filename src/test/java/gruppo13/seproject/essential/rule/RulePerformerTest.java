package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.Status;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionPerformer;
import gruppo13.seproject.essential.action.exception.ActionException;
import gruppo13.seproject.essential.action.exception.ActionExceptionTest;
import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.trigger.Trigger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.Mockito.*;

class RulePerformerTest {

    private RuleManager ruleManagerMock;
    private RequestPublisher requestPublisherMock;
    private RulePerformer rulePerformer;

    @BeforeEach
    void setUp() {
        // Creating mocks for RuleManager and RequestPublisher
        ruleManagerMock = mock(RuleManager.class);
        requestPublisherMock = mock(RequestPublisher.class);
        // Creating an instance of RulePerformer with mocks
        rulePerformer = new RulePerformer(ruleManagerMock, requestPublisherMock);
    }

    @Test
    void testExecute_NoActiveRules() {
        // When there are no active rules, the execute method should have no effect
        when(ruleManagerMock.getRules()).thenReturn(Collections.emptyList());

        rulePerformer.execute();

        // Verify that getRules is called once and requestPublisher is not called
        verify(ruleManagerMock, times(1)).getRules();
        verifyNoMoreInteractions(requestPublisherMock);
    }

    @Test
    void testExecute_ActiveRule_NoTriggerVerification() {
        // When there is an active rule with no trigger verification, the execute method should have no effect
        Rule activeRule = createTestRule(Status.ACTIVE, false);
        when(ruleManagerMock.getRules()).thenReturn(Collections.singletonList(activeRule));

        rulePerformer.execute();

        // Verify that getRules is called once, trigger.verify is called once, and requestPublisher is not called
        verify(ruleManagerMock, times(1)).getRules();
        verify(activeRule.getTrigger(), times(1)).verify();
        verifyNoMoreInteractions(requestPublisherMock);
    }

    @Test
    void testExecute_ActiveRule_TriggerVerification_ActionExecution() throws ActionException {
        // When there is an active rule with trigger verification, actions should be executed if the trigger is verified
        Rule activeRule = createTestRule(Status.ACTIVE, true);
        when(ruleManagerMock.getRules()).thenReturn(Collections.singletonList(activeRule));

        rulePerformer.execute();

        // Verify that getRules is called once, trigger.verify is called once, and requestPublisher.publishRequest is called for each action
        verify(ruleManagerMock, times(1)).getRules();
        verify(activeRule.getTrigger(), times(1)).verify();

        // Verify that relevant methods on actions and requestPublisher are called for each action
        for (Action action : activeRule.getActions()) {
            verify(action, times(1)).getState();
            verify(action, times(1)).setState(Status.NOTACTIVE);
            verify(action, times(1)).execute();
            verify(requestPublisherMock, times(1)).publishRequest(RequestFactory.createExecutionRequest(action));
        }

        // Verify that ruleManager.setState is called once
        verify(ruleManagerMock, times(1)).setStatus(activeRule, Status.NOTACTIVE);
    }

    @Test
    void testExecute_ActionExceptionHandling() throws ActionException {
        // When an ActionException occurs during action execution, it should be caught and reported
        Rule activeRule = createTestRule(Status.ACTIVE, true);
        when(ruleManagerMock.getRules()).thenReturn(Collections.singletonList(activeRule));

        // Simulate an ActionException during action execution
        doThrow(new ActionExceptionTest("Test Action Exception")).when(activeRule.getActions().get(0)).execute();

        rulePerformer.execute();

        // Verify that requestPublisher.publishRequest is called with any exception
        verify(requestPublisherMock, times(1)).publishRequest(any());
    }

    // Helper method to create a mock Rule with specified state and trigger verification result
    private Rule createTestRule(Status ruleStatus, boolean triggerVerificationResult) {
        Rule rule = mock(Rule.class);
        when(rule.getStatus()).thenReturn(ruleStatus);

        Action action = mock(Action.class);
        when(action.getState()).thenReturn(Status.ACTIVE);

        Trigger trigger = mock(Trigger.class);
        when(trigger.verify()).thenReturn(triggerVerificationResult);

        when(rule.getTrigger()).thenReturn(trigger);
        when(rule.getActions()).thenReturn(Collections.singletonList(action));

        return rule;
    }
}

