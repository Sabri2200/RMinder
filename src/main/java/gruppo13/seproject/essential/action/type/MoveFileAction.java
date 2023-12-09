package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.exception.ActionException;
import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.request_handler.RequestType;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/*
The `MoveFileAction` class is an implementation of the `FileAction` abstract class, specifically designed to handle moving a file from one location to another in a Java application.

1. Inheritance:
   - `MoveFileAction` extends `FileAction`, indicating that it is a specialized form of file-related action.

2. Attributes:
   - `newPath`: A `String` representing the destination path where the file will be moved.
   - `requestPublisher`: An instance of `RequestPublisher` used for publishing requests, likely for error handling and reporting.

3. Constructor:
   - The constructor `MoveFileAction(File file, String newPath)` initializes the action with a source file and a destination path. It also retrieves an instance of `RequestPublisher` using `RequestPublisher.getInstance()`.

4. Action Execution:
   - The `execute()` method, which overrides the method from the `FileAction` class, is responsible for performing the file move operation.
   - It uses Java's NIO `Files` class to move the file from the source path (`super.getFile().getAbsolutePath()`) to the destination path (`this.newPath`), with the option to replace the existing file if it exists.

5. Error Handling:
   - If an exception occurs during the file move operation, the method publishes a request with the exception details using `RequestPublisher`. This is likely used for logging or handling errors.

6. Action Type and String Representation:
   - The `getType()` method returns `ActionType.MOVEFILE`, indicating the type of this action.
   - The `toString()` method provides a string representation of the `MoveFileAction`, including its type, source file path, and destination path.

7. Destination Path Accessor:
   - The `getNewPath()` method provides access to the destination path where the file will be moved.
*/

public class MoveFileAction extends FileAction {
    private String newPath;
    private RequestPublisher requestPublisher;

    public MoveFileAction(File file, String newPath) {
        super(file);
        this.newPath = newPath;
        requestPublisher = RequestPublisher.getInstance();
    }

    @Override
    public ActionType getType() {
        return ActionType.MOVEFILE;
    }

    @Override
    public void execute() throws ActionException {
        Path sourcePath = Paths.get(super.getFile().getAbsolutePath()); // Percorso del file sorgente
        Path destinationPath = Paths.get(this.newPath + "/" + super.getFile().getName()); // Percorso di destinazione

        try {
            // Sposta il file dalla sorgente alla destinazione
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            requestPublisher.publishRequest(new Request(RequestType.EXCEPTION, e));
        }
    }

    public String getNewPath() {
        return newPath;
    }

    @Override
    public String toString() {
        return getType() + " " + super.getFile().getAbsoluteFile() + " " + getNewPath();
    }
}
