package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionFactory;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.TriggerFactory;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.trigger.TriggerType;
import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;


public class RuleJsonTest {
    @Test
    public void testRuleToJson() {
            // Create a Rule for testing
        Trigger trigger = TriggerFactory.createTrigger(Map.entry(TriggerType.CLOCKTRIGGER, List.of("12:00")));
        Action action = ActionFactory.createAction(Map.entry(ActionType.MP3PLAYER, List.of("/path/to/audio")));
        Rule rule = RuleFactory.createRule("TestRule", List.of(action), trigger, RuleStatus.ACTIVE);

        // Convert the Rule to JSON
        String json = RuleJson.ruleToJson(rule);

        // Verify that the JSON is not null and contains the expected properties
        assertNotNull(json);
        JSONObject jsonObject = new JSONObject(json);
        assertEquals("TestRule", jsonObject.getString("name"));
        assertEquals("CLOCKTRIGGER 12:00", jsonObject.getString("trigger"));
        assertEquals("ACTIVE", jsonObject.getString("state"));
        assertEquals(0, jsonObject.getInt("nextActivation"));

        JSONArray actionsArray = jsonObject.getJSONArray("actions");
        assertEquals(1, actionsArray.length());
        assertEquals("MP3PLAYER /path/to/audio", actionsArray.getString(0));
        }

    @Test
    public void testRulesToJson() {
        // Create a list of rules for testing
        Trigger trigger = TriggerFactory.createTrigger(Map.entry(TriggerType.CLOCKTRIGGER, List.of("12:00")));
        Action action = ActionFactory.createAction(Map.entry(ActionType.MP3PLAYER, List.of("/path/to/audio")));
        Rule rule1 = RuleFactory.createRule("TestRule1", List.of(action), trigger, RuleStatus.ACTIVE);
        Rule rule2 = RuleFactory.createRule("TestRule2", List.of(action), trigger, RuleStatus.INACTIVE);
        List<Rule> rules = List.of(rule1, rule2);

        // Convert the list of rules to JSON
        String json = RuleJson.rulesToJson(rules);

        // Verify that the JSON is not null and contains the expected array of rules
        assertNotNull(json);
        JSONArray jsonArray = new JSONArray(json);
        assertEquals(2, jsonArray.length());

        JSONObject jsonRule1 = jsonArray.getJSONObject(0);
        assertEquals("TestRule1", jsonRule1.getString("name"));

        JSONObject jsonRule2 = jsonArray.getJSONObject(1);
        assertEquals("TestRule2", jsonRule2.getString("name"));
    }

    @Test
    public void testJsonToRules() {
        // Create a JSON string representing a list of rules
        String json = "[{\"name\":\"TestRule1\",\"trigger\":\"CLOCKTRIGGER 12:00\",\"ruleState\":\"ACTIVE\",\"nextActivation\":0,\"actions\":[\"MP3PLAYER /path/to/audio\"]}," +
                "{\"name\":\"TestRule2\",\"trigger\":\"CLOCKTRIGGER 12:00\",\"ruleState\":\"INACTIVE\",\"nextActivation\":0,\"actions\":[\"MP3PLAYER /path/to/audio\"]}]";

        // Convert the JSON string to a list of rules
        List<Rule> rules = RuleJson.jsonToRules(json);

        // Verify that the list of rules is not null and contains the expected rules
        assertNotNull(rules);
        assertEquals(2, rules.size());
        assertEquals("TestRule1", rules.get(0).getName());
        assertEquals("TestRule2", rules.get(1).getName());
    }

    @Test
    public void testJsonToRule() {
        // Create a JSON object representing a rule
        JSONObject jsonRule = new JSONObject("{\"name\":\"TestRule\",\"trigger\":\"CLOCKTRIGGER 12:00\",\"ruleState\":\"ACTIVE\",\"nextActivation\":0,\"actions\":[\"MP3PLAYER /path/to/audio\"]}");

        // Convert the JSON object to a rule
        Rule rule = RuleJson.jsonToRule(jsonRule);

        // Verify that the rule is not null and contains the expected properties
        assertNotNull(rule);
        assertEquals("TestRule", rule.getName());
        assertEquals("CLOCKTRIGGER 12:00", rule.getTrigger().toString());
        assertEquals(RuleStatus.ACTIVE, rule.getRuleStatus());
        assertEquals(0, rule.getNextActivation());

        List<Action> actions = rule.getActions();
        assertEquals(1, actions.size());
        assertEquals("MP3PLAYER /path/to/audio", actions.get(0).toString());
    }
}

