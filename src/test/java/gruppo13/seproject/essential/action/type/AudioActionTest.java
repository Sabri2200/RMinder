package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.exception.AudioActionException;
import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class AudioActionTest {

    /*@Mock
    private AudioInputStream mockAudioInputStream;
    @Mock
    private SourceDataLine mockSourceDataLine;
    @Mock
    private RequestPublisher mockRequestPublisher;

    @Before
    public void setUp() {
        // Inizializza i mock
        MockitoAnnotations.initMocks(this);
        // Inietta il mock di RequestPublisher nella classe AudioAction
        RequestPublisher mockRequestPublisher =  RequestPublisher.getInstance();
    }

    @Test
    public void testExecuteSuccess() throws Exception {
        // Configura il mock di AudioInputStream e SourceDataLine
        when(mockAudioInputStream.getFormat()).thenReturn(new AudioFormat(44100, 16, 2, true, false));
        when(mockSourceDataLine.write(any(byte[].class), anyInt(), anyInt())).thenReturn(4096);
        when(mockAudioInputStream.read(any(byte[].class), anyInt(), anyInt())).thenReturn(-1); // Termina la lettura

        // Crea un'istanza di AudioAction con un file fittizio
        File mockFile = mock(File.class);
        AudioAction audioAction = new AudioAction(mockFile);

        // Esegui il metodo che stai testando
        audioAction.execute();

        // Verifica che il mock di SourceDataLine sia stato utilizzato correttamente
        verify(mockSourceDataLine).open(any(AudioFormat.class));
        verify(mockSourceDataLine).start();
        verify(mockSourceDataLine).drain();
        verify(mockSourceDataLine).stop();

        // Verifica che il mock di RequestPublisher non sia stato utilizzato (nessun errore è stato pubblicato)
        verifyZeroInteractions(mockRequestPublisher);
    }

    @Test (expected = IOException.class)
    public void testExecuteIOException() throws Exception {
        // Configura il mock di AudioInputStream e SourceDataLine
        when(mockAudioInputStream.getFormat()).thenReturn(new AudioFormat(44100, 16, 2, true, false));
        when(mockSourceDataLine.write(any(byte[].class), anyInt(), anyInt())).thenReturn(4096);
        when(mockAudioInputStream.read(any(byte[].class), anyInt(), anyInt())).thenThrow(new IOException("Test IOException"));

        // Crea un'istanza di AudioAction con un file fittizio
        File mockFile = mock(File.class);
        AudioAction audioAction = new AudioAction(mockFile);

        // Esegui il metodo che stai testando
        audioAction.execute();

        // Verifica che il mock di SourceDataLine sia stato utilizzato correttamente
        verify(mockSourceDataLine).open(any(AudioFormat.class));
        verify(mockSourceDataLine).start();
        verify(mockSourceDataLine).drain();
        verify(mockSourceDataLine).stop();

        // Verifica che il mock di RequestPublisher sia stato utilizzato per pubblicare l'eccezione
        ArgumentCaptor<Exception> exceptionCaptor = ArgumentCaptor.forClass(Exception.class);
        verify(mockRequestPublisher).publishRequest(RequestFactory.createExceptionRequest(exceptionCaptor.capture()));

        // Verifica che l'eccezione pubblicata contenga il messaggio corretto
        assertTrue(exceptionCaptor.getValue() instanceof AudioActionException);
        assertEquals("Test IOException", exceptionCaptor.getValue().getMessage());
    }*/

    @Test
    public void testSuccessExecute() throws AudioActionException {
        // Create an instance of AudioAction with a valid audio file path
        File audioFile = new File("D:/Musica Freestyle/Alexander Rybak - Fairytale Shang Chi.mp3");
        AudioAction audioAction = new AudioAction(audioFile);

        // Execute the action and assert that no exceptions are thrown
        audioAction.execute();
    }

    @Test(expected = AudioActionException.class)
    public void testFailureExecute() throws AudioActionException {
        // Create an instance of AudioAction with an invalid audio file path
        File invalidAudioFile = new File("invalid/path/audio.mp3");
        AudioAction audioAction = new AudioAction(invalidAudioFile);

        // Execute the action and expect it to throw AudioActionException
        audioAction.execute();
    }
    @Test
    public void testGetType() {
        // Create an instance of AudioAction with a dummy audio file path
        File audioFile = new File("dummy/path/audio.mp3");
        AudioAction audioAction = new AudioAction(audioFile);

        // Assert that the action type is as expected
        assertEquals(ActionType.MP3PLAYER, audioAction.getType());
    }

    @Test
    public void testToString() {
        // Create an instance of AudioAction with a dummy audio file path
        File audioFile = new File("D:/Musica Freestyle/Alexander Rybak - Fairytale Shang Chi.mp3");
        AudioAction audioAction = new AudioAction(audioFile);

        // Assert that the string representation is as expected
        assertEquals("MP3PLAYER D:\\Musica Freestyle\\Alexander Rybak - Fairytale Shang Chi.mp3", audioAction.toString());
    }

    @Test
    public void testGetFile() {
        // Create an instance of AudioAction with a dummy audio file path
        File audioFile = new File("dummy/path/audio.mp3");
        AudioAction audioAction = new AudioAction(audioFile);

        // Assert that the getFile method returns the correct file
        assertEquals(audioFile, audioAction.getFile());
    }
}
