package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.State;
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

public class RuleJson {

    public static String ruleToJson(Rule rule) {
        JSONObject jsonRule = new JSONObject();
        jsonRule.put("name", rule.getName());
        jsonRule.put("trigger", rule.getTrigger().toString());
        jsonRule.put("state", rule.getState().toString());

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

    private static Rule jsonToRule(JSONObject jsonRule) {
        String name = jsonRule.getString("name");

        List<String> triggerParams = new ArrayList<>(List.of(jsonRule.getString("trigger").split(" ")));

        TriggerType triggerType = TriggerType.valueOf(triggerParams.get(0));
        triggerParams.remove(0);

        Map.Entry<TriggerType, List<String>> triggerListEntry = Map.entry(triggerType, triggerParams);

        Trigger trigger = TriggerFactory.createTrigger(triggerListEntry);

        State state = State.valueOf(jsonRule.getString("state"));

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

        return RuleFactory.createRule(name, actions, trigger, state);
    }


}
