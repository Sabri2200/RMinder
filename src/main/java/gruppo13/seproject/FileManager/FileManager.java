package gruppo13.seproject.FileManager;

import java.io.File;

public class FileManager {

    private static Boolean verifyFile(File file) {
        return file != null && file.exists();
    }

    public static Boolean verifyAudioFile(File file) {
        return file.canRead() && verifyFile(file);
    }

    public static void saveRulesToFile(String rules, File file) {

    }
}
