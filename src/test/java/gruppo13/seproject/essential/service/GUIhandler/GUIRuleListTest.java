package gruppo13.seproject.essential.service.GUIhandler;

import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestType;
import gruppo13.seproject.essential.rule.Rule;
import gruppo13.seproject.essential.rule.RuleManager;
import gruppo13.seproject.service.GUIhandler.GUIRuleList;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GUIRuleListTest {

    private GUIRuleList guiRuleList;
    private RuleManager ruleManager;

    @BeforeEach
    public void setUp() {
        // Create instances of GUIRuleList and RuleManager
        guiRuleList = GUIRuleList.getInstance();
        ruleManager = RuleManager.getInstance();
    }

    @Test
    public void testListUpdateRequestHandling() {
        // Initial state: Check if the list is empty
        ObservableList<Rule> initialList = guiRuleList.getList();
        assertEquals(0, initialList.size());

        // Add a rule to the RuleManager
        Rule testRule = new Rule("Test Rule", null, null, null);
        ruleManager.addRule(testRule);

        // Simulate a LISTUPDATE request
        Request listUpdateRequest = new Request(RequestType.LISTUPDATE, null);
        guiRuleList.handleRequest(listUpdateRequest);

        // After handling the request, check if the list is updated
        ObservableList<Rule> updatedList = guiRuleList.getList();
        assertEquals(1, updatedList.size());

        // Clean up: Remove the added rule
        ruleManager.removeRule(testRule);
        guiRuleList.handleRequest(listUpdateRequest);
    }
}

