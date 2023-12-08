package gruppo13.seproject.essential.action.type;
import gruppo13.seproject.essential.action.ActionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DialogBoxActionTest {

    @Test
    void getType() {
        // Create DialogBoxAction instance
        DialogBoxAction dialogBoxAction = new DialogBoxAction("Title", "Content", "Message");

        // Check if the action type is correct
        assertEquals(ActionType.DIALOGBOX, dialogBoxAction.getType());
    }

    @Test
    void toStringTest() {
        // Create DialogBoxAction instance
        DialogBoxAction dialogBoxAction = new DialogBoxAction("Title", "Content", "Message");

        // Check if the toString representation is correct
        assertEquals("DIALOGBOX Title Content Message", dialogBoxAction.toString());
    }
}

