package gruppo13.seproject.essential.request_handler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class RequestPublisherTest {

    @Mock
    private RequestSwitcher mockRequestSwitcher;

    @Before
    public void setUp() {
        // Inizializza i mock
        MockitoAnnotations.initMocks(this);
        // Imposta il mock nella RequestSwitcher
        RequestPublisher.getInstance().setRequestSwitcher(mockRequestSwitcher);
    }

    @Test
    public void testPublishRequest() {
        RequestPublisher requestPublisher = RequestPublisher.getInstance();
        Request mockRequest = mock(Request.class);

        // Esegui il metodo che stai testando
        requestPublisher.publishRequest(mockRequest);

        // Verifica che il metodo handleRequest della RequestSwitcher sia chiamato con il mockRequest
        verify(mockRequestSwitcher, times(1)).handleRequest(mockRequest);
    }

    @Test
    public void testSetHandlers() {
        RequestPublisher requestPublisher = RequestPublisher.getInstance();
        Handler mockHandler1 = mock(Handler.class);
        Handler mockHandler2 = mock(Handler.class);

        List<Handler> handlers = new ArrayList<>();
        handlers.add(mockHandler1);
        handlers.add(mockHandler2);

        // Esegui il metodo che stai testando
        requestPublisher.setHandlers(handlers);

        // Verifica che il metodo setNext sia chiamato correttamente per ogni handler
        verify(mockHandler1, times(1)).setNext(mockHandler2);
        verify(mockHandler2, times(1)).setNext(null);
    }
    // Metodo di factory per creare un'istanza di RequestPublisher con una specifica RequestSwitcher


}
