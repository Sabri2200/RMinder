package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.exception.AudioActionException;
import gruppo13.seproject.essential.action.type.AudioAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class AudioActionTest {

    @Test
    void execute() {
        // Provide a valid audio file path for testing
        String audioFilePath = "path/to/audio/file.mp3";

        // Create AudioAction instance
        AudioAction audioAction = new AudioAction(new File(audioFilePath));

        // Execute the action
        assertDoesNotThrow(() -> audioAction.execute());

        // Check if the state is updated after execution
        assertEquals(State.ACTIVE, audioAction.getState());
    }

    @Test
    void executeWithInvalidFile() {
        // Provide an invalid file path for testing
        String invalidFilePath = "path/to/invalid/file.txt";

        // Create AudioAction instance with an invalid file path
        AudioAction audioAction = new AudioAction(new File(invalidFilePath));

        // Execute the action and expect an AudioActionException
        assertThrows(AudioActionException.class, audioAction::execute);

        // Check if the state is updated after execution
        assertEquals(State.ACTIVE, audioAction.getState());
    }

    @Test
    void getType() {
        // Create AudioAction instance
        AudioAction audioAction = new AudioAction(new File("path/to/audio/file.mp3"));

        // Check if the action type is correct
        Assertions.assertEquals(ActionType.MP3PLAYER, audioAction.getType());
    }

    @Test
    void toStringTest() {
        // Provide an audio file path for testing
        String audioFilePath = "path/to/audio/file.mp3";

        // Create AudioAction instance
        AudioAction audioAction = new AudioAction(new File(audioFilePath));

        // Check if the toString representation is correct
        assertEquals("MP3PLAYER " + new File(audioFilePath).getAbsolutePath(), audioAction.toString());
    }
}
