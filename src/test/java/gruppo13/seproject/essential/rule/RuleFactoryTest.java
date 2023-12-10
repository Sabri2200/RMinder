package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.type.DialogBoxAction;
import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestType;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.type.ClockTrigger;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.rule.Rule;
import gruppo13.seproject.essential.rule.RuleFactory;
import gruppo13.seproject.essential.rule.RuleStatus;
import gruppo13.seproject.essential.trigger.Trigger;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class RuleFactoryTest {

    @Test
    public void testCreateRuleValid() {
        // Creazione di un'azione fittizia
        Action fakeAction = new DialogBoxAction("title","content","message");

        // Creazione di un trigger fittizio
        Trigger fakeTrigger = new ClockTrigger(LocalTime.now());

        // Creazione di una regola valida
        Rule validRule = RuleFactory.createRule("ValidRule", Collections.singletonList(fakeAction), fakeTrigger, RuleStatus.ACTIVE);

        assertNotNull(validRule);
        assertEquals("ValidRule", validRule.getName());
        assertEquals(RuleStatus.ACTIVE, validRule.getRuleStatus());
    }

    @Test (expected = Exception.class)
    public void testCreateRuleInvalid() {
        // Creazione di una regola con parametro 'actions' nullo
        Rule invalidRule = RuleFactory.createRule("InvalidRule", null, new ClockTrigger(LocalTime.now()), RuleStatus.ACTIVE);

        // Verifica che la regola sia nulla a causa di un parametro nullo
        assertNull(invalidRule);

        // se RequestPublisher tiene traccia delle richieste, puoi accedere a esse
        // per verificare che sia stata pubblicata una richiesta di eccezione.
        List<Request> requests = getPublishedRequests();
        assertFalse(requests.isEmpty());
        assertEquals(RequestType.EXCEPTION, requests.get(0).getType());
    }

    // Metodo fittizio per ottenere le richieste pubblicate
    private List<Request> getPublishedRequests() {
        return Collections.emptyList();
    }
}
