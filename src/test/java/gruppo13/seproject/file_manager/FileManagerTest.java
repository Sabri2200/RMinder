package gruppo13.seproject.file_manager;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.type.AudioAction;
import gruppo13.seproject.essential.rule.Rule;
import gruppo13.seproject.essential.rule.RuleStatus;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.type.ClockTrigger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileManagerTest {

    private File tempFile;
    private FileManager fileManager;

    @Before
    public void setUp() throws IOException {
        tempFile = File.createTempFile("test", ".json");
        fileManager = FileManager.getInstance();
    }

    @After
    public void tearDown() {
        tempFile.delete();
    }

    @Test
    void verifyFile() {
        assertTrue(tempFile != null && tempFile.exists());
    }

    @Test
    void verifyAudioFile() {
        assertTrue(tempFile.canRead());
    }

    @Test
    void verifyReadableFile() {
        assertTrue(tempFile.canRead());
    }

    @Test
    void verifyWrittableFile() {
        assertTrue(tempFile.canWrite());
    }

    @Test
    void saveRulesToFile() throws IOException {
        testSaveRulesToFile();
    }

    @Test
    void loadRulesFromFile() throws IOException {
        testLoadRulesFromFileBasic();
    }

    @Test (expected = IOException.class)
    void testSaveRulesToFile() throws IOException {
        List<Rule> rules = new ArrayList<>();
        List<Action> actions = new ArrayList<>();

        actions.add(new AudioAction(new File("/Users/michelecoscarelli/Downloads/gg.mp3")));
        Trigger trigger = new ClockTrigger(LocalTime.of(00, 00));

        rules.add(new Rule("name1", actions, trigger, RuleStatus.ACTIVE));
        rules.add(new Rule("name2", actions, trigger, RuleStatus.INACTIVE));

        fileManager.saveRulesToFile(rules, tempFile);

        // Verifica che il file non sia vuoto
        assertTrue(tempFile.length() > 0);

        // Leggi il contenuto del file e verifica che corrisponda alle aspettative
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }
        System.out.println(fileContent.toString().trim());
        // Verifica il contenuto del file
        // assertEquals("Contenuto atteso", fileContent.toString().trim());
    }

    @Test
    void testSaveRulesToFileWithEmptyList() throws IOException {
        List<Rule> rules = new ArrayList<>();

        fileManager.saveRulesToFile(rules, tempFile);

        // Verifica che il file sia vuoto o contenga il contenuto atteso per una lista vuota
        assertEquals(0, tempFile.length());
    }

    @Test
    void testSaveRulesToFileWithNullList() {
        try {
            fileManager.saveRulesToFile(null, tempFile);
        } catch (Exception e) {
            // If an unexpected exception is thrown, fail the test
            fail("Unexpected exception: " + e.getClass().getSimpleName());
        }
    }

    @Test
    void testSaveRulesToFileExceptionHandling() {
        List<Rule> rules = new ArrayList<>();

        rules.add(new Rule("rule", null, null, RuleStatus.INACTIVE));

        // Rende il file di sola lettura per indurre un'eccezione
        assertTrue(tempFile.setReadOnly());

        assertThrows(Exception.class, () -> fileManager.saveRulesToFile(rules, tempFile));
    }

    @Test(expected = IOException.class)
    void testLoadRulesFromFileBasic() throws IOException {
        // Preparazione: crea un file con contenuto noto
        testSaveRulesToFile();

        // Azione: carica le regole dal file
        List<Rule> loadedRules = new ArrayList<>();

        for (Rule rule : fileManager.loadRulesFromFile(tempFile)) {
            loadedRules.add(rule);
        }

        //List<Rule> loadedRules = fileManager.loadRulesFromFile();
        List<Rule> expectedRules = new ArrayList<>();
        List<Action> actions = new ArrayList<>();

        actions.add(new AudioAction(new File("/Users/michelecoscarelli/Downloads/gg.mp3")));
        Trigger trigger = new ClockTrigger(LocalTime.of(00, 00));

        expectedRules.add(new Rule("name1", actions, trigger, RuleStatus.ACTIVE));
        expectedRules.add(new Rule("name2", actions, trigger, RuleStatus.INACTIVE));

        for (Rule rule : loadedRules) {
            System.out.println(rule.getName());
            for (Action a : rule.getActions()) {
                System.out.println(a.toString());
            }
            System.out.println(rule.getTrigger());
            System.out.println(rule.getRuleStatus());
        }
    }

    @Test
    void testLoadRulesFromFileEmpty() {
        // Azione: carica le regole dal file vuoto
        List<Rule> loadedRules = fileManager.loadRulesFromFile(tempFile);

        // Verifica: aspettati una lista vuota o un comportamento specifico
        assertTrue(loadedRules == null);
    }

    @Test
    void testLoadRulesFromFileMalformed() throws Exception {
        // Preparazione: crea un file con contenuto malformato
        File malformedFile = File.createTempFile("test", ".tx");

        // Azione e Verifica: aspettati un'eccezione o un comportamento specifico
        assertThrows(IOException.class, () -> fileManager.loadRulesFromFile(malformedFile));

        // Pulizia: elimina il file di test
        malformedFile.delete();
    }
}