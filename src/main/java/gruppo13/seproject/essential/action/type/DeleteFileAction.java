package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.exception.FileActionException;
import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.request_handler.RequestType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/*
The `DeleteFileAction` class is an implementation of the `FileAction` abstract class, specifically designed to handle the deletion of a file in a Java application.

1. Inheritance:
   - `DeleteFileAction` extends `FileAction`, indicating that it is a specialized form of file-related action.

2. Attributes:
   - `requestPublisher`: An instance of `RequestPublisher` used for publishing requests, likely for error handling and reporting.

3. Constructor:
   - The constructor `DeleteFileAction(File file)` initializes the action with a specific file to be deleted. It also retrieves an instance of `RequestPublisher` using `RequestPublisher.getInstance()`.

4. Action Execution:
   - The `execute()` method, which overrides the method from the `FileAction` class, is responsible for performing the file deletion operation.
   - It uses Java's NIO `Files` class to delete the file specified in the `FileAction` superclass. The method `Files.deleteIfExists` is used to delete the file if it exists, preventing an exception if the file is not found.

5. Error Handling:
   - If an exception occurs during the file deletion operation, the method publishes a request with the exception details using `RequestPublisher`. This mechanism is likely used for logging or handling errors.

6. Action Type and String Representation:
   - The `getType()` method returns `ActionType.DELETEFILE`, indicating the type of this action.
   - The `toString()` method provides a string representation of the `DeleteFileAction`, including its type and the file path.
*/

public class DeleteFileAction extends FileAction {
    private RequestPublisher requestPublisher;

    public DeleteFileAction(File file) {
        super(file);
        this.requestPublisher = RequestPublisher.getInstance();
    }

    @Override
    public ActionType getType() {
        return ActionType.DELETEFILE;
    }

    @Override
    public void execute() throws FileActionException {
        try {
            Files.deleteIfExists(Path.of(super.getFile().getAbsolutePath()));
        } catch (IOException e) {
            String errorMessage = "Error deleting file at " + super.getFile().getAbsolutePath();
            requestPublisher.publishRequest(new Request(RequestType.EXCEPTION, errorMessage));
            throw new FileActionException(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Unexpected error deleting file at " + super.getFile().getAbsolutePath();
            requestPublisher.publishRequest(new Request(RequestType.EXCEPTION, errorMessage));
            throw new FileActionException(errorMessage);
        }
    }


    @Override
    public String toString() {
        return getType() + " " + super.getFile().getAbsoluteFile();
    }
}
