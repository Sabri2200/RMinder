package gruppo13.seproject.essential.request_handler;

import org.junit.Test;
import static org.junit.Assert.*;

public class RequestTest {

    @Test
    void createRequest() {
        // Create a sample request
        Request request = new Request(RequestType.LISTUPDATE, "Some data");

        // Check if the request is initialized correctly
        assertEquals(RequestType.LISTUPDATE, request.getType());
        assertEquals("Some data", request.getData());
        assertEquals(RequestStatus.NOTSOLVED, request.getStatus());

        // Solve the request and check if the state is updated
        request.solveRequest();
        assertEquals(RequestStatus.SOLVED, request.getStatus());
    }
}

