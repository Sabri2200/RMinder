package gruppo13.seproject.service;

import gruppo13.seproject.essential.action.type.DialogBoxAction;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AlertExecutorTest extends ApplicationTest {


    @Test
    public void testRunDialogBoxAction() {
        // Create a DialogBoxAction for testing
        String title = "Test Title";
        String content = "Test Content";
        String message = "Test Message";
        DialogBoxAction dialogBoxAction = new DialogBoxAction(title, content, message);

        // Use assertDoesNotThrow to check that run does not throw an exception
        assertDoesNotThrow(() -> AlertExecutor.run(dialogBoxAction));
    }

    @Test
    public void testShowAlert() {
        // Use assertDoesNotThrow to check that showAlert does not throw an exception
        assertDoesNotThrow(() -> AlertExecutor.showAlert("Test Title", "Test Header", "Test Content"));
    }

    @Test
    public void testShowErrorAlert() {
        // Create a mock Exception for testing
        Exception mockException = new Exception("Test Exception");

        // Use assertDoesNotThrow to check that showErrorAlert does not throw an exception
        assertDoesNotThrow(() -> AlertExecutor.showErrorAlert(mockException));
    }
}

