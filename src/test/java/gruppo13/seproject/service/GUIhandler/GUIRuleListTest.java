package gruppo13.seproject.service.GUIhandler;

import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestType;
import gruppo13.seproject.essential.rule.Rule;
import gruppo13.seproject.essential.rule.RuleManager;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GUIRuleListTest {

    private GUIRuleList guiRuleList;
    private RuleManager ruleManager;

    @Before
    public void setUp() {
        // Crea le istanze di GUIRuleList e RuleManager
        guiRuleList = GUIRuleList.getInstance();
        ruleManager = RuleManager.getInstance();
    }

    @Test
    public void testListUpdateRequestHandling() {
        // Stato iniziale: verifica se la lista è vuota
        ObservableList<Rule> initialList = guiRuleList.getList();
        assertEquals(0, initialList.size());

        // Aggiungi una regola a RuleManager
        Rule testRule = new Rule("Test Rule", null, null, null);
        ruleManager.addRule(testRule);

        // Simula una richiesta LISTUPDATE
        Request listUpdateRequest = new Request(RequestType.LISTUPDATE, null);
        guiRuleList.handleRequest(listUpdateRequest);

        // Dopo aver gestito la richiesta, verifica se la lista è stata aggiornata
        ObservableList<Rule> updatedList = guiRuleList.getList();
        assertEquals(1, updatedList.size());

        // Pulisci: rimuovi la regola aggiunta
        ruleManager.removeRule(testRule);
        guiRuleList.handleRequest(listUpdateRequest);
    }
}
