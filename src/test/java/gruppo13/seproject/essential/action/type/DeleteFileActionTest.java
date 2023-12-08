package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.Status;
import gruppo13.seproject.essential.action.exception.ActionException;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.nio.file.Files;

public class DeleteFileActionTest {

    private File testFile;
    private DeleteFileAction deleteFileAction;

    @Before
    public void setUp() throws Exception {
        // Creazione di un file temporaneo per il test
        testFile = File.createTempFile("testFile", ".txt");
        deleteFileAction = new DeleteFileAction(testFile);
    }

    @After
    public void tearDown() throws Exception {
        // Pulizia: elimina i file creati durante il test, se ancora esistono
        Files.deleteIfExists(testFile.toPath());
    }

    @Test
    public void testFileDeletionSuccess() throws Exception {
        deleteFileAction.execute();
        assertFalse("Il file non dovrebbe esistere", testFile.exists());
    }

    @Test(expected = ActionException.class)
    public void testFileDeletionFailure() throws Exception {
        File invalidFile = new File("path/to/inesistente.txt");
        DeleteFileAction invalidDeleteAction = new DeleteFileAction(invalidFile);
        invalidDeleteAction.execute();
    }


}
