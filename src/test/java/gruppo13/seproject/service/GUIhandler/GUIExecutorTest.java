package gruppo13.seproject.service.GUIhandler;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.type.DialogBoxAction;
import gruppo13.seproject.essential.action.type.FileAction;
import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestType;
import gruppo13.seproject.service.GUIhandler.GUIExecutor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GUIExecutorTest {

    @Test
    public void testExecuteDialogBoxAction() {
        // Create a DialogBoxAction for testing
        String title = "Test Title";
        String content = "Test Content";
        String message = "Test Message";
        DialogBoxAction dialogBoxAction = new DialogBoxAction(title, content, message);

        // Create a Request with EXECUTION type and the DialogBoxAction
        Request executionRequest = new Request(RequestType.EXECUTION, dialogBoxAction);

        // Get the instance of GUIExecutor
        GUIExecutor guiExecutor = GUIExecutor.getInstance();

        // Execute the request
        guiExecutor.execute(executionRequest);

        // For now, let's just assert that the method execution doesn't throw any exceptions.
        assertTrue(true);
    }

    @Test
    public void testExecuteNonDialogBoxAction() {
        // Create a non-DialogBox Action for testing
        Action nonDialogBoxAction = new Action() {
            private State state = State.NOTACTIVE; // Initial state

            @Override
            public void execute() {
                // Some dummy execution
                setState(State.ACTIVE); // Simulating a state change after execution
            }

            @Override
            public ActionType getType() {
                return ActionType.INVALID_TYPE; // Using a non-DIALOGBOX type
            }

            @Override
            public State getState() {
                return state;
            }

            @Override
            public void setState(State state) {
                this.state = state;
            }
        };

        // Create a Request with EXECUTION type and the non-DialogBox Action
        Request executionRequest = new Request(RequestType.EXECUTION, nonDialogBoxAction);

        // Get the instance of GUIExecutor
        GUIExecutor guiExecutor = GUIExecutor.getInstance();

        // Execute the request
        guiExecutor.execute(executionRequest);

        // Assert that the state is changed after execution
        assertEquals(State.ACTIVE, nonDialogBoxAction.getState());
    }

}
