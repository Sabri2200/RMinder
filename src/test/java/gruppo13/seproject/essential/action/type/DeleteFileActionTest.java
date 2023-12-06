/*package gruppo13.seproject.essential.action.type;
import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.ActionType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DeleteFileActionTest {

    @Test
    void execute() {
        // Create a temporary file for testing
        File tempFile;
        try {
            tempFile = File.createTempFile("test", ".txt");
        } catch (IOException e) {
            throw new RuntimeException("Error creating temporary file for testing.");
        }

        // Create DeleteFileAction instance
        DeleteFileAction deleteFileAction = new DeleteFileAction(tempFile.getAbsolutePath());

        // Execute the action
        assertDoesNotThrow(() -> deleteFileAction.execute());

        // Check if the file is deleted
        assertFalse(Files.exists(Path.of(tempFile.getAbsolutePath())));
    }

    @Test
    void executeWithNonexistentFile() {
        // Provide a nonexistent file path for testing
        String nonexistentFilePath = "path/to/nonexistent/file.txt";

        // Create DeleteFileAction instance with a nonexistent file path
        DeleteFileAction deleteFileAction = new DeleteFileAction(nonexistentFilePath);

        // Execute the action and expect no exception
        assertDoesNotThrow(deleteFileAction::execute);
    }

    @Test
    void getType() {
        // Create DeleteFileAction instance
        DeleteFileAction deleteFileAction = new DeleteFileAction("path/to/delete/file.txt");

        // Check if the action type is correct
        assertEquals(ActionType.DELETEFILE, deleteFileAction.getType());
    }

    @Test
    void toStringTest() {
        // Provide a file path for testing
        String filePath = "path/to/delete/file.txt";

        // Create DeleteFileAction instance
        DeleteFileAction deleteFileAction = new DeleteFileAction(filePath);

        // Check if the toString representation is correct
        assertEquals("DELETEFILE " + filePath, deleteFileAction.toString());
    }
}
*/
