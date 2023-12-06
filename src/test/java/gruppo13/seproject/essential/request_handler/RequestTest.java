package gruppo13.seproject.essential.request_handler;
import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestState;
import gruppo13.seproject.essential.request_handler.RequestType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestTest {

    @Test
    void createRequest() {
        // Create a sample request
        Request request = new Request(RequestType.LISTUPDATE, "Some data");

        // Check if the request is initialized correctly
        assertEquals(RequestType.LISTUPDATE, request.getType());
        assertEquals("Some data", request.getData());
        assertEquals(RequestState.NOTSOLVED, request.getState());

        // Solve the request and check if the state is updated
        request.solveRequest();
        assertEquals(RequestState.SOLVED, request.getState());
    }
}

