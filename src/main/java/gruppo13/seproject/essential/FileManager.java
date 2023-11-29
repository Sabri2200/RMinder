package gruppo13.seproject.essential;

import java.io.*;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import gruppo13.seproject.essential.Action.*;
import gruppo13.seproject.essential.Trigger.Trigger;
import gruppo13.seproject.essential.Trigger.TriggerFactory;
import gruppo13.seproject.essential.Trigger.TriggerType;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.Objects;

public class FileManager {
    File file;
    Gson gson;

    public FileManager(File file) {
        this.file = file;
        this.gson = new Gson();
    }

    public void saveRulesToFile(List<Rule> rules) {
        StringBuffer sb = new StringBuffer();

        for (Rule rule : rules) {
            sb.append(rule.getName()).append(";");
            sb.append(rule.getTrigger().toString()).append(";");

            for (Action action : rule.getActions()) {
                if (action != null) {
                    sb.append(action.toString()).append(",");
                }
            }

            sb.append(";");
            sb.append(rule.getState().toString()).append(";").append("\n");
        }

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(sb, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Rule> loadRulesFromFile() {
        ActionFactory af = new ActionFactory();
        TriggerFactory tf = new TriggerFactory();

        List<Rule> rules = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length < 4) {
                    // La linea non ha abbastanza parti per costruire una Rule
                    continue;
                }

                String name = parts[0];

                Trigger t = null;
                String[] trigger = parts[1].split(" ");

                if (Objects.equals(trigger[0], TriggerType.ClockTrigger.name())) {
                    t = tf.createClockTrigger(LocalTime.parse(trigger[1]));
                }

                List<Action> actions = new ArrayList<>();
                String[] actionParts = parts[2].split(",");
                for (String actionStr : actionParts) {
                    if (!actionStr.isEmpty()) {
                        String[] actionPart = actionStr.split(" ");
                        Action action = null;

                        if (Objects.equals(actionPart[0], ActionType.MP3PLAYER.name())) {
                            action = af.createAudioAction(actionPart[1]);
                        } else if (Objects.equals(actionPart[0], ActionType.DIALOGBOX.name())){
                            action = af.createMessageAction(actionPart[1], actionPart[2]);
                        }

                        actions.add(action);
                    }
                }

                int cont = 0;
                Action[] as = new Action[10];

                for (Action a : actions) {
                    as[cont] = a;
                    cont++;
                }

                String state = parts[3];
                SimpleBooleanProperty s = Objects.equals(state, "true") ? new SimpleBooleanProperty(true) : new SimpleBooleanProperty(false);

                Rule rule = new Rule(name, as, t, s);
                rules.add(rule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Rule rule : rules) {
            System.out.println(rule.toString());
        }

        return rules;
    }
}
