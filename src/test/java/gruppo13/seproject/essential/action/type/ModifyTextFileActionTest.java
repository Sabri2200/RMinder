package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.Status;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ModifyTextFileActionTest {

    private File testFile;
    private String contentToAdd;
    private ModifyTextFileAction modifyTextFileAction;

    @Before
    public void setUp() throws Exception {
        // Creazione di un file temporaneo per il test
        testFile = File.createTempFile("testFile", ".txt");
        contentToAdd = "Test Content";

        modifyTextFileAction = new ModifyTextFileAction(testFile, contentToAdd);
    }

    @After
    public void tearDown() throws Exception {
        // Pulizia: elimina i file creati durante il test
        Files.deleteIfExists(testFile.toPath());
    }

    @Test
    public void testFileModificationSuccess() throws Exception {
        modifyTextFileAction.execute();
        String fileContent = new String(Files.readAllBytes(testFile.toPath()));
        assertEquals("Il contenuto del file dovrebbe corrispondere", contentToAdd, fileContent);
    }

    @Test(expected = IOException.class)
    public void testFileModificationFailure() throws Exception {
        File invalidFile = new File("path/to/inesistente.txt");
        ModifyTextFileAction invalidModifyAction = new ModifyTextFileAction(invalidFile, contentToAdd);
        invalidModifyAction.execute();
    }

    @Test
    public void testStateAfterExecution() throws Exception {
        modifyTextFileAction.execute();
        assertEquals("Lo stato dovrebbe essere ACTIVE", Status.ACTIVE, modifyTextFileAction.getState());
    }

    @Test
    public void testToString() {
        String expectedString = "MODIFYTEXTFILE " + testFile.getName() + " " + contentToAdd;
        assertEquals("Il metodo toString dovrebbe restituire la stringa corretta", expectedString, modifyTextFileAction.toString());
    }

}
