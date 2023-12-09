package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.request_handler.RequestType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
The `ModifyTextFileAction` class is an implementation of the `FileAction` abstract class, specifically designed to modify the content of a text file in a Java application.

1. Inheritance:
   - `ModifyTextFileAction` extends `FileAction`, indicating that it is a specialized form of file-related action.

2. Attributes:
   - `stringToAdd`: A `String` representing the content to be added or written to the file.
   - `requestPublisher`: An instance of `RequestPublisher` used for publishing requests, likely for error handling and reporting.

3. Constructor:
   - The constructor `ModifyTextFileAction(File file, String stringToAdd)` initializes the action with a specific file and the string content to be added to the file. It also retrieves an instance of `RequestPublisher` using `RequestPublisher.getInstance()`.

4. Action Execution:
   - The `execute()` method, which overrides the method from the `FileAction` class, is responsible for performing the file modification operation.
   - It uses a `BufferedWriter` to write the `stringToAdd` to the file specified in the `FileAction` superclass. The method uses a try-with-resources statement to ensure that the writer is closed properly, even if an exception occurs.

5. Error Handling:
   - If an `IOException` occurs during the file writing operation, the method publishes a request with the exception details using `RequestPublisher`. This mechanism is likely used for logging or handling errors.

6. Action Type and String Representation:
   - The `getType()` method returns `ActionType.MODIFYTEXTFILE`, indicating the type of this action.
   - The `toString()` method provides a string representation of the `ModifyTextFileAction`, including its type, file path, and the string to be added.

7. String Content Accessor:
   - The `getStringToAdd()` method provides access to the string content that will be added to the file.
*/

public class ModifyTextFileAction extends FileAction {
    private String stringToAdd;
    private RequestPublisher requestPublisher;

    public ModifyTextFileAction(File file, String stringToAdd) {
        super(file);
        this.stringToAdd = stringToAdd;
        requestPublisher = RequestPublisher.getInstance();
    }

    @Override
    public void execute() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(super.getFile().getAbsoluteFile()))) {
            writer.write(this.stringToAdd);
        } catch (IOException e) {
            requestPublisher.publishRequest(new Request(RequestType.EXCEPTION, e));
        }
    }

    @Override
    public ActionType getType() {
        return ActionType.MODIFYTEXTFILE;
    }

    public String getStringToAdd() {
        return stringToAdd;
    }

    @Override
    public String toString() {
        return getType() + " " + super.getFile().getAbsolutePath() + " " + getStringToAdd();
    }

}
