package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.TriggerFactory;
import gruppo13.seproject.essential.trigger.TriggerType;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleJsonTest {

    @Test
    void testRuleToJsonAndJsonToRule() {
        // Create sample actions
        List<Action> actions = new ArrayList<>();
        // Add actions to the list

        // Create a sample trigger
        List<String> triggerParams = new ArrayList<>();
        triggerParams.add(TriggerType.CLOCKTRIGGER.name());
        // Add trigger parameters if needed

        Map.Entry<TriggerType, List<String>> triggerListEntry = Map.entry(TriggerType.CLOCKTRIGGER, triggerParams);
        Trigger trigger = TriggerFactory.createTrigger(triggerListEntry);

        // Create a sample state
        State state = State.ACTIVE;

        // Create a rule using the factory method
        Rule rule = RuleFactory.createRule("Test Rule", actions, trigger, state);

        // Convert the rule to JSON
        String jsonRule = RuleJson.ruleToJson(rule);

        // Convert JSON back to a rule
        Rule convertedRule = RuleJson.jsonToRule(new JSONObject(jsonRule));

        // Check if the properties of the converted rule match the original rule
        assertEquals(rule.getName(), convertedRule.getName());
        assertEquals(rule.getActions(), convertedRule.getActions());
        assertEquals(rule.getTrigger(), convertedRule.getTrigger());
        assertEquals(rule.getState(), convertedRule.getState());
    }

    @Test
    void testRulesToJsonAndJsonToRules() {
        // Create sample rules
        List<Rule> rules = new ArrayList<>();
        // Add rules to the list

        // Convert rules to JSON
        String jsonRules = RuleJson.rulesToJson(rules);

        // Convert JSON back to rules
        List<Rule> convertedRules = RuleJson.jsonToRules(jsonRules);

        // Check if the size of the converted rules list matches the original list
        assertEquals(rules.size(), convertedRules.size());

        // Check if each rule in the converted list matches the corresponding rule in the original list
        for (int i = 0; i < rules.size(); i++) {
            Rule originalRule = rules.get(i);
            Rule convertedRule = convertedRules.get(i);

            assertEquals(originalRule.getName(), convertedRule.getName());
            assertEquals(originalRule.getActions(), convertedRule.getActions());
            assertEquals(originalRule.getTrigger(), convertedRule.getTrigger());
            assertEquals(originalRule.getState(), convertedRule.getState());
        }
    }
}
