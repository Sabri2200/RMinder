package gruppo13.seproject.file_manager;
import gruppo13.seproject.essential.rule.Rule;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FileManagerTest {

    private FileManager fileManager;

    @Before
    public void setUp() {
        fileManager = FileManager.getInstance();
    }

    @Test
    public void testVerifyFile() {
        // Test data
        File existingFile = new File("C:/Users/sabri/Downloads/Prova.txt");
        File nonExistingFile = new File("C:/Users/sabri/Downloads/ProvaNonExist.txt");

        // Verify existing file
        assertTrue(fileManager.verifyFile(existingFile));

        // Verify non-existing file
        assertFalse(fileManager.verifyFile(nonExistingFile));
    }

    @Test
    public void testVerifyAudioFile() {
        // Test data
        File audioFile = new File("D:/Musica Freestyle/X2Download.app-Run-Boy-Run-Floor-Music.mp3");
        File nonAudioFile = new File("src/test/resources/test_text.txt");

        // Verify audio file
        assertTrue(fileManager.verifyAudioFile(audioFile));

        // Verify non-audio file
        assertFalse(fileManager.verifyAudioFile(nonAudioFile));
    }

    @Test
    public void testVerifyReadableFile() {
        // Test data
        File readableFile = new File("C:/Users/sabri/Downloads/Prova.txt");
        File nonReadableFile = new File("src/test/resources/non_readable_file.txt");

        // Verify readable file
        assertTrue(fileManager.verifyReadableFile(readableFile));

        // Verify non-readable file
        assertFalse(fileManager.verifyReadableFile(nonReadableFile));
    }

    @Test
    public void testVerifyWritableFile() {
        // Test data
        File writableFile = new File("src/test/resources/writable_file.txt");
        File nonWritableFile = new File("src/test/resources/non_writable_file.txt");

        // Verify writable file
        assertTrue(fileManager.verifyWrittableFile(writableFile));

        // Verify non-writable file
        assertFalse(fileManager.verifyWrittableFile(nonWritableFile));
    }

    @Test
    public void testSaveAndLoadRulesToFile() {
        // Test data
        List<Rule> rules = Arrays.asList(
                new Rule("Rule1", null, null, 0, null),
                new Rule("Rule2", null, null, 0, null)
        );
        File testFile = new File("src/test/resources/test_rules.json");

        // Save rules to file
        assertTrue(fileManager.saveRulesToFile(rules, testFile));

        // Load rules from file
        List<Rule> loadedRules = fileManager.loadRulesFromFile(testFile);

        // Verify loaded rules
        assertNotNull(loadedRules);
        assertEquals(rules.size(), loadedRules.size());
        assertEquals(rules.get(0).getName(), loadedRules.get(0).getName());
        assertEquals(rules.get(1).getName(), loadedRules.get(1).getName());
    }
}
