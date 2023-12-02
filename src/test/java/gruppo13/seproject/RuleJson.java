package gruppo13.seproject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RuleJson {

    @Test
    public void generateJson() {
        String jsonString = "{\n" +
                "  \"name\": \"RegolaEsempio\",\n" +
                "  \"actions\": [\n" +
                "    {\n" +
                "      \"actionType\": \"TipoAzione1\",\n" +
                "      \"parameters\": [\"param1\", \"param2\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"actionType\": \"TipoAzione2\",\n" +
                "      \"parameters\": [\"param3\", \"param4\"]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"trigger\": {\n" +
                "    \"triggerType\": \"TipoTrigger\",\n" +
                "    \"parameters\": [\"param5\", \"param6\"]\n" +
                "  },\n" +
                "  \"state\": true\n" +
                "}\n";
        JSONObject json = new JSONObject(jsonString);

        List<String> parameters = new ArrayList<>();
        parameters.add("\"name\":\"" + json.getString("name") + "\"");

        JSONArray actionsJson = json.getJSONArray("actions");
        for (int i = 0; i < actionsJson.length(); i++) {
            parameters.add("\"action" + i + "\":" + actionsJson.getJSONObject(i).toString());
        }

        JSONObject triggerJson = json.getJSONObject("trigger");
        parameters.add("\"trigger\":" + triggerJson.toString());

        parameters.add("\"state\":" + json.getBoolean("state"));

        System.out.println(parameters.toString());
        /*
        JSONObject json = new JSONObject();

        json.put("name", rule.getName());

        JSONArray actions = new JSONArray();

        for (Action action : rule.getActions()) {
            JSONObject action1 = new JSONObject();
            action1.put("actionType", action.getType().name());

            String[] params = action.getParameters();
            action1.put("parameters", new JSONArray(params));
            actions.put(action1);
        }


        json.put("actions", actions);

        JSONObject trigger = new JSONObject();
        trigger.put("triggerType", rule.getTrigger().getType());
        trigger.put("parameters", new JSONArray(rule.getTrigger().getParameters()));

        json.put("trigger", trigger);
        json.put("state", rule.getState());

        return json.toString();*/
    }
}
