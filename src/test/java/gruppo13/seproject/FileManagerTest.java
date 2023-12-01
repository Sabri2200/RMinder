package gruppo13.seproject;

import gruppo13.seproject.essential.model.FileManager;
import gruppo13.seproject.essential.model.Rule;
import javafx.beans.property.SimpleBooleanProperty;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.*;

public class FileManagerTest {

    private File tempFile;
    private FileManager fileManager;

    @BeforeEach
    void setUp() throws IOException {
        // Crea un file temporaneo per i test
        tempFile = File.createTempFile("test", ".txt");
        fileManager = FileManager.createFileManager(tempFile);
    }

    @AfterEach
    void tearDown() {
        tempFile.delete();
    }

    @Test
    void testSaveRulesToFile() throws IOException {
        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule("rule", null, null, new SimpleBooleanProperty(true)));
        fileManager.saveRulesToFile(rules);

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

        // Verifica il contenuto del file
        // assertEquals("Contenuto atteso", fileContent.toString().trim());
    }

    @Test
    void testSaveRulesToFileWithEmptyList() throws IOException {
        List<Rule> rules = new ArrayList<>();

        fileManager.saveRulesToFile(rules);

        // Verifica che il file sia vuoto o contenga il contenuto atteso per una lista vuota
        assertEquals(0, tempFile.length());
    }

    @Test
    void testSaveRulesToFileWithNullList() {
        assertDoesNotThrow(() -> fileManager.saveRulesToFile(null));
    }

    @Test
    void testSaveRulesToFileExceptionHandling() throws IOException {
        List<Rule> rules = new ArrayList<>();

        rules.add(new Rule("rule", null, null, new SimpleBooleanProperty(true)));

        // Rende il file di sola lettura per indurre un'eccezione
        assertTrue(tempFile.setReadOnly());

        assertThrows(IOException.class, () -> fileManager.saveRulesToFile(rules));
    }

    @Test
    void testLoadRulesFromFileBasic() throws IOException {
        // Preparazione: crea un file con contenuto noto
        testSaveRulesToFile();

        // Azione: carica le regole dal file
        List<Rule> loadedRules = fileManager.loadRulesFromFile();
        List<Rule> expectedRules = new ArrayList<>();

        expectedRules.add(new Rule("\"rule", null, null, new SimpleBooleanProperty(false)));

        // Verifica: confronta le regole caricate con quelle attese
        assertEquals(expectedRules, loadedRules);
    }

    @Test
    void testLoadRulesFromFileEmpty() throws IOException {
        // Azione: carica le regole dal file vuoto
        List<Rule> loadedRules = fileManager.loadRulesFromFile();

        // Verifica: aspettati una lista vuota o un comportamento specifico
        assertTrue(loadedRules.isEmpty());
    }

    /*@Test
    void testLoadRulesFromFileMalformed() throws IOException {
        // Preparazione: crea un file con contenuto malformato
        File malformedFile = File.createTempFile("test", ".tx");

        FileManager fm = FileManager.createFileManager(malformedFile);

        // Azione e Verifica: aspettati un'eccezione o un comportamento specifico
        assertThrows(IOException.class, () -> fm.loadRulesFromFile());

        // Pulizia: elimina il file di test
        malformedFile.delete();
    }*/



}
