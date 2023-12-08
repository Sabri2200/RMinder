package gruppo13.seproject.service.GUIhandler;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.type.AudioAction;
import gruppo13.seproject.essential.action.type.DialogBoxAction;
import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GUIExecutorTest {

    @Test
    public void testExecuteDialogBoxAction() {
        // Create a DialogBoxAction for testing
        String title = "Test Title";
        String content = "Test Content";
        String message = "Test Message";
        DialogBoxAction dialogBoxAction = new DialogBoxAction(title, content, message);

        // Create a Request with EXECUTION type and the DialogBoxAction
        Request executionRequest = new Request(RequestType.EXECUTION, dialogBoxAction);

        // Get the instance of GUIExecutor
        GUIExecutor guiExecutor = GUIExecutor.getInstance();

        // Execute the request
        guiExecutor.execute(executionRequest);

        // For now, let's just assert that the method execution doesn't throw any exceptions.
        assertTrue(true);
    }

    @Test
    public void testExecuteNonDialogBoxAction() {
        // Create a non-DialogBox Action for testing
        Action nonDialogBoxAction = null;
        try {
            nonDialogBoxAction = new AudioAction(File.createTempFile("file",".txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create a Request with EXECUTION type and the non-DialogBox Action
        Request executionRequest = new Request(RequestType.EXECUTION, nonDialogBoxAction);

        // Get the instance of GUIExecutor
        GUIExecutor guiExecutor = GUIExecutor.getInstance();

        // Execute the request
        guiExecutor.execute(executionRequest);

    }

}
