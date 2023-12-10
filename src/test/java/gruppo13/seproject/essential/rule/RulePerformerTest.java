package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.exception.ActionException;
import gruppo13.seproject.essential.action.type.CopyFileAction;
import gruppo13.seproject.essential.action.type.DialogBoxAction;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.TriggerFactory;
import gruppo13.seproject.essential.trigger.TriggerType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RulePerformerTest {

    private RulePerformer rulePerformer;
    private RuleManager ruleManager;
    private RequestPublisher requestPublisher;

    @Before
    public void setUp() {
        this.rulePerformer = RulePerformer.getInstance();
        this.ruleManager = RuleManager.getInstance();
        this.requestPublisher = RequestPublisher.getInstance();
    }

    @Test
    public void testExecuteWithVerifiedTrigger() {
        // Creazione di un'azione fittizia
        Action fakeAction = new DialogBoxAction("title", "content", "message");

        // Creazione di un trigger che sarà sempre verificato
        Trigger fakeTrigger = TriggerFactory.createTrigger(Map.entry(TriggerType.CLOCKTRIGGER, List.of("12:00")));

        // Creazione di una regola con trigger verificato e azione
        Rule testRule = RuleFactory.createRule("TestRule", List.of(fakeAction), fakeTrigger, RuleStatus.ACTIVE);
        ruleManager.addRule(testRule);

        try {
            // Esecuzione della RulePerformer
            rulePerformer.execute();
        } catch (Exception e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }
}
