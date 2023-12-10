package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.action.exception.ActionException;
import gruppo13.seproject.essential.action.exception.FileActionException;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;

public class DeleteFileActionTest {

    private File testFile;
    private DeleteFileAction deleteFileAction;
    private RequestPublisher requestPublisher;

    @Before
    public void setUp() throws Exception {
        // Creazione di un file temporaneo per il test
        testFile = File.createTempFile("testFile", ".txt");
        deleteFileAction = new DeleteFileAction(testFile);
        requestPublisher = RequestPublisher.getInstance();
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

    @Test(expected = FileActionException.class)
    public void testFileDeletionFailure() throws Exception {
        File invalidFile = new File("path/to/notExist.txt");
        DeleteFileAction invalidDeleteAction = new DeleteFileAction(invalidFile);
        invalidDeleteAction.execute();
    }


}
