package gruppo13.seproject.file_manager;

import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.rule.Rule;
import gruppo13.seproject.essential.rule.RuleJson;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/*
The `FileManager` class is a utility class in a Java application designed for file operations, particularly related to managing and persisting `Rule` objects.

1. Singleton Pattern Implementation:
   - The class uses a static nested class (`FileManagerInstanceHolder`) to implement the singleton pattern. This ensures that only one instance of `FileManager` is created and used throughout the application. The `getInstance()` method provides access to this instance.

2. Request Publisher Integration:
   - The `FileManager` has a `RequestPublisher` instance, obtained via `RequestPublisher.getInstance()`, which is used to publish requests, likely for logging or handling exceptions.

3. File Verification Methods:
   - `verifyFile(File file)`: Checks if a file exists and is a regular file.
   - `verifyAudioFile(File file)`: Checks if a file is readable and meets additional criteria (and checks for an audio extension).
   - `verifyReadableFile(File file)`: Checks if a file exists, is a file, and is readable.
   - `verifyWritableFile(File file)`: Checks if a file exists, is a file, and is writable.

4. Saving and Loading Rules:
   - `saveRulesToFile(List<Rule> rules, File file)`: Reads a list of `Rule` objects to JSON and writes them to a specified file. If an `IOException` occurs, it publishes an exception request.
   - `loadRulesFromFile(File file)`: Reads a file, deserializes the JSON content to a list of `Rule` objects, and returns them. In case of an `IOException`, it publishes an exception request.

5. File Extension Retrieval:
   - `getExtension(File file)`: A private method to get the file extension.

6. Error Handling:
   - In both `saveRulesToFile` and `loadRulesFromFile`, if an `IOException` is caught, an exception request is published using the `RequestPublisher`. This suggests integration with a larger system for handling errors.
*/

public class FileManager {
    private final RequestPublisher requestPublisher;

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
        String extension = getExtension(file);
        List<String> supportedExtensions = Arrays.asList("mp3", "wav", "ogg", "flac"); // Add more supported formats as needed
        return file.canRead() && verifyFile(file) && supportedExtensions.contains(extension);
    }

    private String getExtension(File file) {
        String fileName = file.getName();
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // Empty extension
        }
        return fileName.substring(lastIndexOf + 1).toLowerCase();
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

}
