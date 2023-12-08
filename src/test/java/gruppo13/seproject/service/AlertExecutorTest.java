package gruppo13.seproject.service;

import gruppo13.seproject.essential.action.type.DialogBoxAction;
import org.testfx.framework.junit5.ApplicationTest;

import org.junit.Test;

public class AlertExecutorTest extends ApplicationTest {

    @Test
    public void testRunDialogBoxAction() {
        // Create a DialogBoxAction for testing
        String title = "Test Title";
        String content = "Test Content";
        String message = "Test Message";
        DialogBoxAction dialogBoxAction = new DialogBoxAction(title, content, message);

        // Run the method that should not throw an exception
        AlertExecutor.run(dialogBoxAction);
    }

    @Test
    public void testShowAlert() {
        // Run the method that should not throw an exception
        AlertExecutor.showAlert("Test Title", "Test Header", "Test Content");
    }

    @Test (expected = Exception.class)
    public void testShowErrorAlert() {
        // Create a mock Exception for testing
        Exception mockException = new Exception("Test Exception");

        // Run the method that should not throw an exception
        AlertExecutor.showErrorAlert(mockException);
    }
}
