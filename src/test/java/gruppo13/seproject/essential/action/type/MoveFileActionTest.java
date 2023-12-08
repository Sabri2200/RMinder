package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.Service.GUIHandler.ErrorLogManager;
import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.exception.ActionException;
import gruppo13.seproject.essential.request_handler.Handler;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MoveFileActionTest {

    private File sourceFile;
    private String destinationPath;
    private MoveFileAction moveFileAction;

    @Before
    public void setUp() throws Exception {
        // Creazione di un file temporaneo per il test
        sourceFile = File.createTempFile("testFile", ".txt");
        destinationPath = sourceFile.getParent() + File.separator + "movedTestFile.txt";

        moveFileAction = new MoveFileAction(sourceFile, destinationPath);

    }

    @After
    public void tearDown() throws Exception {
        // Pulizia: elimina i file creati durante il test
        Files.deleteIfExists(Paths.get(destinationPath));
        Files.deleteIfExists(sourceFile.toPath());
    }

    @Test
    public void testFileMoveSuccess() throws Exception {
        moveFileAction.execute();
        assertTrue("Il file dovrebbe essere stato spostato", Files.exists(Paths.get(destinationPath)));
    }

    @Test(expected = ActionException.class)
    public void testFileMoveFailure() throws Exception {
        File invalidFile = new File("path/to/inesistente.txt");
        MoveFileAction invalidMoveAction = new MoveFileAction(invalidFile, destinationPath);
        invalidMoveAction.execute();
    }

    @Test
    public void testStateAfterExecution() throws Exception {
        moveFileAction.execute();
        assertEquals("Lo stato dovrebbe essere ACTIVE", State.ACTIVE, moveFileAction.getState());
    }


}
