package gruppo13.seproject.FileManager;

import gruppo13.seproject.essential.rule.Rule;
import gruppo13.seproject.essential.rule.RuleJson;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class FileManager {

    private static Boolean verifyFile(File file) {
        return file != null && file.exists();
    }

    public static Boolean verifyAudioFile(File file) {
        return file.canRead() && verifyFile(file);
    }

    public static Boolean verifyReadableFile(File file) {
        return verifyFile(file) && file.canRead();
    }

    public static Boolean verifyWrittableFile(File file) {
        return verifyFile(file) && file.canWrite();
    }

    public static Boolean saveRulesToFile(List<Rule> rules, File file) {
        if (rules != null && !rules.isEmpty()) {
            if (verifyWrittableFile(file)) {
                try {
                    String json = RuleJson.rulesToJson(rules);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(json);
                    writer.close();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    public static List<Rule> loadRulesFromFile(File file) {
        if (verifyReadableFile(file)) {
            StringBuilder jsonBuilder = new StringBuilder();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line).append("\n");
                }
                reader.close();
                return RuleJson.jsonToRules(jsonBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Optional<String> getExtension(File file) {
            return Optional.of(file.getName())
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(file.getName().lastIndexOf(".") + 1));
    }
}
