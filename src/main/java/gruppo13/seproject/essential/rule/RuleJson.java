package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionFactory;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.TriggerFactory;
import gruppo13.seproject.essential.trigger.TriggerType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
The `RuleJson` class is a utility class in a Java application designed for converting `Rule` objects to and from JSON format.

1. Rule to JSON Conversion:
   - `ruleToJson(Rule rule)`: Converts a single `Rule` object into a JSON string. It includes the rule's name, trigger, state, next activation time, and actions. Each action is converted to a string and added to a JSON array.

2. Rules to JSON Conversion:
   - `rulesToJson(List<Rule> rules)`: Converts a list of `Rule` objects into a JSON string. It iterates over each rule, converts it to a JSON object using `ruleToJson`, and adds it to a JSON array.

3. JSON to Rules Conversion:
   - `jsonToRules(String json)`: Converts a JSON string representing a list of rules into a list of `Rule` objects. It parses the JSON string into a JSON array and then converts each JSON object in the array back into a `Rule` object using `jsonToRule`.

4. JSON to Rule Conversion:
   - `jsonToRule(JSONObject jsonRule)`: Converts a single JSON object into a `Rule` object. It extracts the rule's name, trigger, state, next activation time, and actions from the JSON object.
   - For the trigger and actions, it uses `TriggerFactory.createTrigger` and `ActionFactory.createAction` respectively, to create the appropriate objects from the extracted data.
   - Depending on whether `nextActivation` is 0 or not, it uses different constructors from `RuleFactory` to create the `Rule` object.

5. Trigger and Action Parsing:
   - The method parses the trigger and action data from the JSON object, handling the extraction of types and parameters, and then uses the respective factories to create the trigger and actions.

6. Error Handling:
   - The method `jsonToRules` returns `null` if the input JSON string is empty, indicating that no rules could be parsed.
*/

public class RuleJson {

    public static String ruleToJson(Rule rule) {
        JSONObject jsonRule = new JSONObject();
        jsonRule.put("name", rule.getName());
        jsonRule.put("trigger", rule.getTrigger().toString());
        jsonRule.put("state", rule.getState().toString());
        jsonRule.put("nextActivation", rule.getNextActivation());

        JSONArray actionsArray = new JSONArray();
        for (Action action : rule.getActions()) {
            actionsArray.put(action.toString());
        }
        jsonRule.put("actions", actionsArray);

        return jsonRule.toString();
    }

    public static String rulesToJson(List<Rule> rules) {
        JSONArray rulesArray = new JSONArray();
        for (Rule rule : rules) {
            rulesArray.put(new JSONObject(ruleToJson(rule)));
        }
        return rulesArray.toString();
    }

    public static List<Rule> jsonToRules(String json) {
        if (!json.isEmpty()) {
            JSONArray rulesArray = new JSONArray(json);
            List<Rule> rules = new ArrayList<>();

            for (int i = 0; i < rulesArray.length(); i++) {
                JSONObject jsonRule = rulesArray.getJSONObject(i);
                Rule rule = jsonToRule(jsonRule);
                rules.add(rule);
            }

            return rules;
        }
        return null;
    }

    public static Rule jsonToRule(JSONObject jsonRule) {
        String name = jsonRule.getString("name");

        List<String> triggerParams = new ArrayList<>(List.of(jsonRule.getString("trigger").split(" ")));

        TriggerType triggerType = TriggerType.valueOf(triggerParams.get(0));
        triggerParams.remove(0);

        Map.Entry<TriggerType, List<String>> triggerListEntry = Map.entry(triggerType, triggerParams);

        Trigger trigger = TriggerFactory.createTrigger(triggerListEntry);

        RuleState ruleState = RuleState.valueOf(jsonRule.getString("ruleState"));

        int nextActivation = jsonRule.getInt("nextActivation");

        JSONArray actionsArray = jsonRule.getJSONArray("actions");
        List<Action> actions = new ArrayList<>();
        for (int i = 0; i < actionsArray.length(); i++) {
            String actionString = actionsArray.getString(i);
            List<String> actionParams = new ArrayList<>(List.of(actionString.split(" ")));
            ActionType actionType = ActionType.valueOf(actionParams.get(0));
            actionParams.remove(0);

            Map.Entry<ActionType, List<String>> actionListEntry = Map.entry(actionType, actionParams);
            Action action = ActionFactory.createAction(actionListEntry);
            actions.add(action);
        }

        return nextActivation == 0 ?
                RuleFactory.createRule(name, actions, trigger, ruleState) :
                RuleFactory.createRule(name, actions, trigger, nextActivation, ruleState);
    }

}
