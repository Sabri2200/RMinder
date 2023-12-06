package gruppo13.seproject.essential.file_manager;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.type.AudioAction;
import gruppo13.seproject.essential.rule.Rule;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.type.ClockTrigger;
import gruppo13.seproject.file_manager.FileManager;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.time.LocalTime;
import java.util.*;

public class FileManagerTest {

    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        // Crea un file temporaneo per i test
        tempFile = File.createTempFile("test", ".json");
    }

    @AfterEach
    void tearDown() {
        tempFile.delete();
    }

    @Test
    void testSaveRulesToFile() throws IOException {
        List<Rule> rules = new ArrayList<>();
        List<Action> actions = new ArrayList<>();

        actions.add(new AudioAction(new File("/Users/michelecoscarelli/Downloads/gg.mp3")));
        Trigger trigger = new ClockTrigger(LocalTime.of(00, 00));

        rules.add(new Rule("name1", actions, trigger, State.ACTIVE));
        rules.add(new Rule("name2", actions, trigger, State.NOTACTIVE));

        FileManager.getInstance().saveRulesToFile(rules, tempFile);

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

        FileManager.getInstance().saveRulesToFile(rules, tempFile);

        // Verifica che il file sia vuoto o contenga il contenuto atteso per una lista vuota
        assertEquals(0, tempFile.length());
    }

    @Test
    void testSaveRulesToFileWithNullList() {
        assertDoesNotThrow(() -> FileManager.getInstance().saveRulesToFile(null, tempFile));
    }

    @Test
    void testSaveRulesToFileExceptionHandling() throws IOException {
        List<Rule> rules = new ArrayList<>();

        rules.add(new Rule("rule", null, null, State.NOTACTIVE));

        // Rende il file di sola lettura per indurre un'eccezione
        assertTrue(tempFile.setReadOnly());

        assertThrows(Exception.class, () -> FileManager.getInstance().saveRulesToFile(rules, tempFile));
    }

    @Test
    void testLoadRulesFromFileBasic() throws IOException {
        // Preparazione: crea un file con contenuto noto
        testSaveRulesToFile();

        // Azione: carica le regole dal file
        List<Rule> loadedRules = new ArrayList<>();

        for (Rule rule : FileManager.getInstance().loadRulesFromFile(tempFile)) {
            loadedRules.add(rule);
        }

        //List<Rule> loadedRules = fileManager.loadRulesFromFile();
        List<Rule> expectedRules = new ArrayList<>();
        List<Action> actions = new ArrayList<>();

        actions.add(new AudioAction(new File("/Users/michelecoscarelli/Downloads/gg.mp3")));
        Trigger trigger = new ClockTrigger(LocalTime.of(00, 00));

        expectedRules.add(new Rule("name1", actions, trigger, State.ACTIVE));
        expectedRules.add(new Rule("name2", actions, trigger, State.NOTACTIVE));

        for (Rule rule : loadedRules) {
            System.out.println(rule.getName());
            for (Action a : rule.getActions()) {
                System.out.println(a.toString());
            }
            System.out.println(rule.getTrigger());
            System.out.println(rule.getState());
        }
        // Verifica: confronta le regole caricate con quelle attese
        //assertEquals(expectedRules, loadedRules);
    }

    @Test
    void testLoadRulesFromFileEmpty() throws IOException {
        // Azione: carica le regole dal file vuoto
        List<Rule> loadedRules = FileManager.getInstance().loadRulesFromFile(tempFile);

        // Verifica: aspettati una lista vuota o un comportamento specifico
        assertTrue(loadedRules == null);
    }

    @Test
    void testLoadRulesFromFileMalformed() throws IOException {
        // Preparazione: crea un file con contenuto malformato
        File malformedFile = File.createTempFile("test", ".tx");

        // Azione e Verifica: aspettati un'eccezione o un comportamento specifico
        assertThrows(IOException.class, () -> FileManager.getInstance().loadRulesFromFile(malformedFile));

        // Pulizia: elimina il file di test
        malformedFile.delete();
    }



}
