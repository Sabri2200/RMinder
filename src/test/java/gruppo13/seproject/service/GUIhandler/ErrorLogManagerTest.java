package gruppo13.seproject.service.GUIhandler;

import gruppo13.seproject.essential.request_handler.Handler;
import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.service.AlertExecutor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorLogManagerTest {

    private ErrorLogManager errorLogManager;
    private TestHandler nextHandler;

    @Before
    public void setUp() {
        errorLogManager = ErrorLogManager.getInstance();
        nextHandler = new TestHandler();
        errorLogManager.setNext(nextHandler);
    }

    @Test
    public void testHandleExceptionRequest() {
        // Arrange
        Exception testException = new Exception("Test Exception");
        Request exceptionRequest = RequestFactory.createExceptionRequest(testException);

        // Act
        errorLogManager.handleRequest(exceptionRequest);
    }

    @Test
    public void testHandleNonExceptionRequest() {
        // Arrange
        Request nonExceptionRequest = RequestFactory.createListUpdateRequest();

        // Act
        errorLogManager.handleRequest(nonExceptionRequest);

        // Assert
        assertTrue(nextHandler.isHandleRequestCalled());
    }

    @Test
    public void testExecuteWithException() {
        // Arrange
        Exception testException = new Exception("Test Exception");
        Request exceptionRequest = RequestFactory.createExceptionRequest(testException);

        // Act
        errorLogManager.execute(exceptionRequest);
    }

    @Test
    public void testExecuteWithNullException() {
        // Arrange
        Request exceptionRequest = RequestFactory.createExceptionRequest(null);

        // Act
        errorLogManager.execute(exceptionRequest);
    }


    // Custom test handler for tracking handleRequest calls
    private static class TestHandler implements Handler {
        private boolean isHandleRequestCalled = false;

        @Override
        public void setNext(Handler handler) {
            // Do nothing for this test
        }

        @Override
        public void handleRequest(Request request) {
            isHandleRequestCalled = true;
        }

        public boolean isHandleRequestCalled() {
            return isHandleRequestCalled;
        }
    }

    // Custom test class for tracking static method calls
    private static class AlertExecutorTest extends AlertExecutor {
        private static boolean showErrorAlertCalled = false;

        public static void showErrorAlert(Exception e) {
            showErrorAlertCalled = true;
        }

        public static boolean isShowErrorAlertCalled() {
            return showErrorAlertCalled;
        }
    }
}
