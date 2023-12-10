package gruppo13.seproject.essential.request_handler;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestSwitcherTest {

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
    public void testHandleRequestWithNextHandler() {
        // Creare un oggetto di test per l'handler
        TestHandler testHandler = new TestHandler();

        // Creare un'istanza di RequestSwitcher
        RequestSwitcher requestSwitcher = new RequestSwitcher();

        // Impostare l'handler di test come prossimo handler
        requestSwitcher.setNext(testHandler);

        // Creare una richiesta di test
        Request testRequest = new Request(RequestType.EXCEPTION, new Exception("Test Exception"));

        // Eseguire il metodo sotto test
        requestSwitcher.handleRequest(testRequest);

        // Verificare che il metodo handleRequest dell'handler di test sia stato chiamato
        assertTrue(testHandler.isRequestHandled());
    }

    @Test(expected = Exception.class)
    public void testHandleRequestWithoutNextHandler() {
        // Creare un'istanza di RequestSwitcher senza prossimo handler
        RequestSwitcher requestSwitcher = new RequestSwitcher();

        // Creare una richiesta di test
        Request testRequest = new Request(RequestType.EXCEPTION, new Exception("Test Exception"));

        // Eseguire il metodo sotto test
        requestSwitcher.handleRequest(testRequest);

        // Verificare che la richiesta abbia generato un'eccezione
        assertNotNull(testRequest.getData());
    }
}
