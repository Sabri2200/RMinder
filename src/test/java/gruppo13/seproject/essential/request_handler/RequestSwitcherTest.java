package gruppo13.seproject.essential.request_handler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class RequestSwitcherTest {

    @Mock
    private Handler mockHandler;

    @Before
    public void setUp() {
        // Inizializza i mock
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetNext() {
        RequestSwitcher requestSwitcher = RequestSwitcher.getInstance();

        // Esegui il metodo che stai testando
        requestSwitcher.setNext(mockHandler);

        // Verifica che il prossimo handler sia stato impostato correttamente
        assertSame(mockHandler, requestSwitcher.getNextHandler());
    }

    @Test
    public void testHandleRequestWithNextHandler() {
        RequestSwitcher requestSwitcher = RequestSwitcher.getInstance();
        requestSwitcher.setNext(mockHandler);
        Request mockRequest = mock(Request.class);

        // Esegui il metodo che stai testando
        requestSwitcher.handleRequest(mockRequest);

        // Verifica che il metodo handleRequest del prossimo handler sia stato chiamato
        verify(mockHandler, times(1)).handleRequest(mockRequest);
    }

    @Test
    public void testHandleRequestWithoutNextHandler() {
        RequestSwitcher requestSwitcher = RequestSwitcher.getInstance();
        RequestSwitcher originalSwitcher = requestSwitcher; // Salva l'istanza originale

        // Creare un mock di RequestSwitcher
        RequestSwitcher mockRequestSwitcher = mock(RequestSwitcher.class);
        requestSwitcher.setNext(mockRequestSwitcher); // Inietta il mock come prossimo handler

        RequestPublisher requestPublisher = RequestPublisher.getInstance();
        Request mockRequest = mock(Request.class);

        // Esegui il metodo che stai testando
        requestPublisher.publishRequest(mockRequest);

        // Verifica che il metodo handleRequest del mockRequestSwitcher sia stato chiamato con l'eccezione desiderata
        verify(mockRequestSwitcher, times(1)).handleRequest(RequestFactory.createExceptionRequest(any(Exception.class)));

        // Ripristina l'istanza originale di RequestSwitcher
        requestSwitcher.setNext(originalSwitcher);
    }

}

