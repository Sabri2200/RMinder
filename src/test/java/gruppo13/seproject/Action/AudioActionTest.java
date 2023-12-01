package gruppo13.seproject.Action;

import gruppo13.seproject.essential.model.action.ActionType;
import gruppo13.seproject.essential.model.action.AudioAction;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AudioActionTest {
    @BeforeAll
    public static void initJFX() {
        Platform.startup(() -> {
            // Inizializza JavaFX Toolkit (può essere vuoto)
        });
    }
    @Test
    public void testExecutionAudioAction() {
        //Inserisco un percorso valido di un file audio, memorizzato in locale
        String path = "/Users/michelecoscarelli/Downloads/gg.mp3";

        // Creo un'istanza di AudioAction
        AudioAction audioAction = new AudioAction(path);

        // Eseguo l'azione
        audioAction.execute();


        // Verifico se l'azione audio è stata eseguita con successo
        assertTrue(audioAction.getPath().equals(path));
    }


    @Test
    void testAudioActionToString() {
        // Creo un oggetto AudioAction con un percorso valido del file audio
        AudioAction audioAction = new AudioAction("/Users/carmineattianese/Downloads/gg.mp3");

        // Verifico che il metodo toString restituisca la stringa attesa
        assertEquals("MP3PLAYER /Users/carmineattianese/Downloads/gg.mp3", audioAction.toString());
    }


    @Test
    void testAudioActionType() {
        // Creo un oggetto AudioAction con un percorso valido del file audio
        AudioAction audioAction = new AudioAction("/Users/carmineattianese/Downloads/gg.mp3");

        // Verifico che il tipo restituito sia ActionType.MP3PLAYER
        assertEquals(ActionType.MP3PLAYER, audioAction.getType());
    }

    @Test
    void testCreation() {
        String testPath = "test.mp3";
        AudioAction action = new AudioAction(testPath);
        assertNotNull(action);
        assertEquals(ActionType.MP3PLAYER, action.getType());
        assertEquals(testPath, action.getPath());
    }

    @Test
    void testToString() {
        String testPath = "test.mp3";
        AudioAction action = new AudioAction(testPath);
        String expectedString = "MP3PLAYER test.mp3";
        assertEquals(expectedString, action.toString());
    }

    @Test
    void testFileFormat() {
        String validAudioPath = "test.mp3";
        AudioAction validAudioAction = new AudioAction(validAudioPath);
        assertTrue(isAudioFile(validAudioAction.getPath()));

        String invalidAudioPath = "test.txt";
        AudioAction invalidAudioAction = new AudioAction(invalidAudioPath);
        assertFalse(isAudioFile(invalidAudioAction.getPath()));
    }

    private boolean isAudioFile(String filePath) {
        String lowerCasePath = filePath.toLowerCase();
        return lowerCasePath.endsWith(".mp3") || lowerCasePath.endsWith(".wav") || lowerCasePath.endsWith(".aac");
    }

}
