package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.action.exception.ActionException;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CopyFileActionTest {

    private File sourceFile;
    private String destinationPath;
    private CopyFileAction copyFileAction;
    private RequestPublisher requestPublisher;

    @Before
    public void setUp() throws Exception {
        // Creazione di un file temporaneo per il test
        sourceFile = File.createTempFile("testFile", ".txt");
        destinationPath = sourceFile.getParent() + File.separator + "copiedTestFile.txt";
        requestPublisher = RequestPublisher.getInstance();
        copyFileAction = new CopyFileAction(sourceFile, destinationPath);
    }

    @After
    public void tearDown() throws Exception {
        // Pulizia: elimina i file creati durante il test
        Files.deleteIfExists(Paths.get(destinationPath));
        Files.deleteIfExists(sourceFile.toPath());
    }

    @Test
    public void testFileCopySuccess() throws Exception {
        copyFileAction.execute();
        assertTrue("Il file di destinazione dovrebbe esistere", Files.exists(Paths.get(destinationPath)));
    }

    @Test(expected = ActionException.class)
    public void testFileCopyFailure() throws Exception {
        File invalidFile = new File("path/to/inesistente.txt");
        CopyFileAction invalidCopyAction = new CopyFileAction(invalidFile, destinationPath);
        invalidCopyAction.execute();
    }

}
