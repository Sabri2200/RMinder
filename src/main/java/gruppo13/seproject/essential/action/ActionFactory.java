package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.action.type.*;
import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;

import java.io.File;
import java.util.List;
import java.util.Map;

/*
The `ActionFactory` class is a factory class in a Java application designed to create `Action` objects based on specified types and parameters.

1. Action Creation:
   - The `createAction(Map.Entry<ActionType, List<String>> action)` method is the primary method for creating `Action` objects. It checks if the provided action type matches any of the `ActionType` enum values and then creates the corresponding `Action` object based on the type.

2. Specific Action Creators:
   - The factory includes private methods for creating specific types of actions, each corresponding to a different `ActionType`:
     - `createDialogBoxAction(List<String> params)`: Creates a `DialogBoxAction`.
     - `createAudioAction(List<String> params)`: Creates an `AudioAction`.
     - `createMoveFileAction(List<String> params)`: Creates a `MoveFileAction`.
     - `createCopyFileAction(List<String> params)`: Creates a `CopyFileAction`.
     - `createDeleteFileAction(List<String> params)`: Creates a `DeleteFileAction`.
     - `createModifyFileAction(List<String> params)`: Creates a `ModifyTextFileAction`.

3. Error Handling:
   - Each specific action creation method includes try-catch blocks to handle exceptions that might occur during the creation of actions (e.g., invalid parameters).
   - If an exception occurs, it publishes an exception request using `RequestPublisher`.

4. Request Publisher Integration:
   - The `ActionFactory` uses `RequestPublisher` to publish requests, likely for logging errors or handling exceptions that occur during action creation.

5. Parameter Validation and Parsing:
   - The methods parse the provided parameters (e.g., file paths, text strings) and use them to instantiate the specific action objects. This includes handling file paths and other action-specific data.
*/

public class ActionFactory {
    public static Action createAction(Map.Entry<ActionType, List<String>> action) {
        if (action != null) {
            boolean typeMatcher = false;
            for (ActionType type : ActionType.values()) {
                if (action.getKey().equals(type)) {
                    typeMatcher = true;
                    break;
                }
            }

            return typeMatcher ? switch (action.getKey()) {
                case DIALOGBOX -> createDialogBoxAction(action.getValue());
                case MP3PLAYER -> createAudioAction(action.getValue());
                case MOVEFILE -> createMoveFileAction(action.getValue());
                case MODIFYTEXTFILE -> createModifyFileAction(action.getValue());
                case DELETEFILE -> createDeleteFileAction(action.getValue());
                case COPYFILE -> createCopyFileAction(action.getValue());
            } : null;
        }
        RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception("Error during creating this action")));
        return null;
    }

    private static DialogBoxAction createDialogBoxAction(List<String> params) {
        try {
            String title = params.get(0);
            String content = params.get(1);
            String message = params.get(2);

            return new DialogBoxAction(title, content, message);
        } catch (Exception e) {
            handleActionCreationException(e, "createDialogBoxAction");
            return null;
        }
    }

    private static AudioAction createAudioAction(List<String> params) {
        try {
            File file = new File(params.get(0));

            return new AudioAction(file);
        } catch (Exception e) {
            handleActionCreationException(e, "createAudioAction");
            return null;
        }
    }

    private static MoveFileAction createMoveFileAction(List<String> params) {
        try {
            File file = new File(params.get(0));
            String path = params.get(1);

            return new MoveFileAction(file, path);
        } catch (Exception e) {
            handleActionCreationException(e, "createMoveFileAction");
            return null;
        }

    }

    private static CopyFileAction createCopyFileAction(List<String> params) {
        try {
            File file = new File(params.get(0));
            String path = params.get(1);
            return new CopyFileAction(file, path);
        } catch (Exception e) {
            handleActionCreationException(e, "CopyFileAction");
            return null;
        }
    }

    private static DeleteFileAction createDeleteFileAction(List<String> params) {
        try {
            File file = new File(params.get(0));
            return new DeleteFileAction(file);
        } catch (Exception e) {
            handleActionCreationException(e, "DeleteFileAction");
            return null;
        }
    }
    private static ModifyTextFileAction createModifyFileAction(List<String> params) {
        try {
            File file = new File(params.get(0));
            String str = params.get(1);

            return new ModifyTextFileAction(file, str);
        } catch (Exception e) {
            handleActionCreationException(e, "createModifyFileAction");
            return null;
        }
    }

    private static void handleActionCreationException(Exception e, String actionType) {
        String errorMessage = "Error during creating " + actionType + " action";
        RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception(errorMessage)));
    }


}
