import gruppo13.seproject.essential.Action.ActionType;
import gruppo13.seproject.essential.Action.AudioAction;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AudioActionTest { @BeforeAll
    public static void initJFX() {
        Platform.startup(() -> {
            // Inizializza JavaFX Toolkit (può essere vuoto)
        });
    }
    @Test
    public void testEsecuzioneAudioAction() {
        //Inserisco un percorso valido di un file audio, memorizzato in locale
        String percorsoFileAudio = "/Users/carmineattianese/Downloads/gg.mp3";

        // Creo un'istanza di AudioAction
        AudioAction audioAction = new AudioAction(percorsoFileAudio);

        // Eseguo l'azione
        audioAction.execute();


        // Verifico se l'azione audio è stata eseguita con successo
        assertTrue(audioAction.getPath().equals(percorsoFileAudio));
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

}
