package gruppo13.seproject.Action;

import gruppo13.seproject.essential.model.Action.ActionType;
import gruppo13.seproject.essential.model.Action.MessageAction;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
public class MessageActionTest extends Application {

    @Override
    public void start(Stage stage) {
        stage.show();
    }

    @Test
    void testCreationAndGetterMethods() {
        String title = "Test Title";
        String message = "Test Message";
        MessageAction action = new MessageAction(title, message);

        assertNotNull(action);
        assertEquals(ActionType.DIALOGBOX, action.getType());
        assertEquals(title, action.getTitle());
        assertEquals(message, action.getMessage());
    }
    @Test
    void testExecute() {
        // Crea l'azione
        MessageAction action = new MessageAction("Test Title", "Test Message");

        // Esegui l'azione
        action.execute();

        // Verifica che l'Alert sia stato mostrato con il testo corretto
        verifyThat(".alert", hasText("Test Message"));
    }

}
