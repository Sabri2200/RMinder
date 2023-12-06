package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.action.ActionType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class CopyFileActionTest {

    @Test
    void execute() {
        // Prepare test files
        String sourceFilePath = "C:/Users/sabri/Downloads/Prova.txt";
        String destinationDirectory = "C:/Users/sabri/Downloads";
        String destinationFilePath = destinationDirectory + "/file.txt";

        try {
            Files.createDirectories(Paths.get(destinationDirectory));
            Files.createFile(Paths.get(sourceFilePath));
        } catch (IOException e) {
            fail("Failed to create test files.");
        }

        // Create CopyFileAction instance
        CopyFileAction copyFileAction = new CopyFileAction(sourceFilePath, destinationDirectory);

        // Execute the action
        assertDoesNotThrow(() -> copyFileAction.execute());

        // Check if the file was copied successfully
        assertTrue(Files.exists(Paths.get(destinationFilePath)));

        // Clean up
        try {
            Files.deleteIfExists(Paths.get(sourceFilePath));
            Files.deleteIfExists(Paths.get(destinationFilePath));
            Files.deleteIfExists(Paths.get(destinationDirectory));
        } catch (IOException e) {
            fail("Failed to clean up test files.");
        }
    }

    @Test
    void getType() {
        CopyFileAction copyFileAction = new CopyFileAction("path/to/source/file.txt", "path/to/destination");
        assertEquals(ActionType.DIALOGBOX, copyFileAction.getType());
    }

    @Test
    void toStringTest() {
        CopyFileAction copyFileAction = new CopyFileAction("path/to/source/file.txt", "path/to/destination");
        assertEquals("DIALOGBOX", copyFileAction.toString());
    }
}
