package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.action.type.AudioAction;
import gruppo13.seproject.essential.action.type.DialogBoxAction;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActionFactoryTest {

    @Test
    void createAction() {
        // Create a sample map entry for DialogBox action
        Map.Entry<ActionType, List<String>> dialogBoxEntry = Map.entry(
                ActionType.DIALOGBOX,
                List.of("Title", "Content", "Message")
        );

        // Create a sample map entry for Audio action
        Map.Entry<ActionType, List<String>> audioActionEntry = Map.entry(
                ActionType.MP3PLAYER,
                List.of("path/to/audio/file.mp3")
        );

        // Test creating DialogBoxAction
        Action dialogBoxAction = ActionFactory.createAction(dialogBoxEntry);
        assertTrue(dialogBoxAction instanceof DialogBoxAction);
        assertEquals("DIALOGBOX Title Content Message", dialogBoxAction.toString());

        // Test creating AudioAction
        Action audioAction = ActionFactory.createAction(audioActionEntry);
        assertTrue(audioAction instanceof AudioAction);
        assertEquals("MP3PLAYER path/to/audio/file.mp3", audioAction.toString());
    }
}
