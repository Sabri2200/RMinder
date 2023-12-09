package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionType;

/*
The `DialogBoxAction` class is an implementation of the `Action` interface in a Java application, specifically designed to represent an action that involves displaying a dialog box.

1. Attributes:
   - `title`: A `String` representing the title of the dialog box.
   - `content`: A `String` representing the content or header of the dialog box.
   - `message`: A `String` representing the message to be displayed in the dialog box.

2. Constructor:
   - The constructor `DialogBoxAction(String title, String content, String message)` initializes the action with the specified title, content, and message for the dialog box.

3. Getters:
   - The class provides getter methods for all its attributes (`getTitle`, `getContent`, `getMessage`). These methods allow other parts of the application to access the properties of the dialog box action.

4. Action Execution:
   - The `execute()` method, which overrides the method from the `Action` interface, is where the logic for displaying the dialog box would typically be implemented. However, in this implementation, the method is empty, suggesting that the actual display logic might be handled by a GUI Executor.

5. Action Type:
   - The `getType()` method returns `ActionType.DIALOGBOX`, indicating the type of this action. This helps in identifying the kind of action being dealt with, especially when working with multiple action types.

6. String Representation:
   - The `toString()` method provides a string representation of the `DialogBoxAction`, including its type, title, content, and message. This can be useful for logging or debugging purposes.
*/

public class DialogBoxAction implements Action {
    private String title, content, message;

    public DialogBoxAction(String title, String content, String message) {
        this.title = title;
        this.content = content;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void execute() {}

    @Override
    public ActionType getType() {
        return ActionType.DIALOGBOX;
    }

    @Override
    public String toString() {
        return getType() + " " + getTitle() + " " + getContent() + " " + getMessage();
    }

}