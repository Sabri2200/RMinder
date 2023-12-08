package gruppo13.seproject.file_manager;

import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.rule.Rule;
import gruppo13.seproject.essential.rule.RuleJson;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class FileManager {
    private RequestPublisher requestPublisher;

    private FileManager() {
        this.requestPublisher = RequestPublisher.getInstance();
    }

    private static final class FileManagerInstanceHolder {
        private static final FileManager fileManagerInstance = new FileManager();
    }

    public static FileManager getInstance() {
        return FileManagerInstanceHolder.fileManagerInstance;
    }


    public Boolean verifyFile(File file) {
        return file != null && file.exists() && file.isFile();
    }

    public Boolean verifyAudioFile(File file) {
        return file.canRead() && verifyFile(file); // && getExtension(file).equals("mp3") && file.canRead();
    }

    public Boolean verifyReadableFile(File file) {
        return verifyFile(file) && file.canRead();
    }

    public Boolean verifyWrittableFile(File file) {
        return verifyFile(file) && file.canWrite();
    }

    public Boolean saveRulesToFile(List<Rule> rules, File file) {
        if (rules != null && !rules.isEmpty()) {
            if (verifyWrittableFile(file)) {
                try {
                    String json = RuleJson.rulesToJson(rules);
                    FileWriter writer = new FileWriter(file);
                    writer.write(json);
                    writer.close();
                    return true;
                } catch (IOException e) {
                    requestPublisher.publishRequest(RequestFactory.createExceptionRequest(e));
                    return false;
                }
            }
        }
        return false;
    }

    public List<Rule> loadRulesFromFile(File file) {
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
                requestPublisher.publishRequest(RequestFactory.createExceptionRequest(e));
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
