package gruppo13.seproject.essential.service.GUIhandler;

import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestType;
import gruppo13.seproject.service.GUIhandler.ErrorLogManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ErrorLogManagerTest {

    @Test
    public void testExecuteWithExceptionRequest() {
        // Create an instance of ErrorLogManager
        ErrorLogManager errorLogManager = ErrorLogManager.getInstance();

        // Create a mock Exception for testing
        Exception mockException = mock(Exception.class);

        // Create an EXCEPTION request with the mock Exception
        Request exceptionRequest = new Request(RequestType.EXCEPTION, mockException);

        // Use assertDoesNotThrow to check that execute does not throw an exception
        assertDoesNotThrow(() -> errorLogManager.execute(exceptionRequest));
    }

    @Test
    public void testExecuteWithNonExceptionRequest() {
        // Create an instance of ErrorLogManager
        ErrorLogManager errorLogManager = ErrorLogManager.getInstance();

        // Create a mock Request with a different type (not EXCEPTION)
        Request mockRequest = mock(Request.class);
        when(mockRequest.getType()).thenReturn(RequestType.LISTUPDATE); // Example type, not EXCEPTION

        // Use assertThrows to check that execute throws an exception for a non-EXCEPTION request
        assertThrows(IllegalArgumentException.class, () -> errorLogManager.execute(mockRequest));
    }
}
