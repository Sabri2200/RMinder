package gruppo13.seproject.essential.request_handler;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.type.DialogBoxAction;

import org.junit.Test;
import static org.junit.Assert.*;



public class RequestFactoryTest {

    @Test
    public void createExceptionRequest() {
        // Create an exception
        Exception exception = new RuntimeException("Test exception");

        // Create an exception request using the factory method
        Request request = RequestFactory.createExceptionRequest(exception);

        // Check if the request is created correctly
        assertEquals(RequestType.EXCEPTION, request.getType());
        assertEquals(exception, request.getData());
    }

    @Test
    public void createExecutionRequest() {
        // Create a sample action
        Action action = new DialogBoxAction("title", "content", "message");

        // Create an execution request using the factory method
        Request request = RequestFactory.createExecutionRequest(action);

        // Check if the request is created correctly
        assertEquals(RequestType.EXECUTION, request.getType());
        assertEquals(action, request.getData());
    }

    @Test
    public void createListUpdateRequest() {
        // Create a list update request using the factory method
        Request request = RequestFactory.createListUpdateRequest();

        // Check if the request is created correctly
        assertEquals(RequestType.LISTUPDATE, request.getType());
        assertNull(request.getData());
    }
}
