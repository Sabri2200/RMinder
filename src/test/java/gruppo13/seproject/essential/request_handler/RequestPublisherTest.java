package gruppo13.seproject.essential.request_handler;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RequestPublisherTest {

    // Classe di test per simulare un handler semplice
    static class TestHandler implements Handler {
        private boolean requestHandled = false;

        @Override
        public void setNext(Handler handler) {

        }

        @Override
        public void handleRequest(Request request) {
            // Simulare il comportamento dell'handler
            requestHandled = true;
        }

        public boolean isRequestHandled() {
            return requestHandled;
        }
    }

    @Test
    public void testPublishRequest() {
        // Creare un oggetto di test per l'handler
        TestHandler testHandler = new TestHandler();

        // Creare una lista contenente l'handler di test
        List<Handler> handlers = new ArrayList<>();
        handlers.add(testHandler);

        // Creare un'istanza di RequestPublisher
        RequestPublisher requestPublisher = new RequestPublisher();
        requestPublisher.setHandlers(handlers);

        // Creare una richiesta di test
        Request testRequest = new Request(RequestType.EXCEPTION, new Exception("Test Exception"));

        // Eseguire il metodo sotto test
        requestPublisher.publishRequest(testRequest);

        // Verificare che il metodo handleRequest dell'handler di test sia stato chiamato
        assertTrue(testHandler.isRequestHandled());
    }
}
