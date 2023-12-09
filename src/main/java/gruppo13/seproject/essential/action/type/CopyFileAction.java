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
The `CopyFileAction` class is a specialized implementation of a file action in a Java application, designed to copy a file from one location to another.

1. Inheritance:
   - `CopyFileAction` extends `FileAction`, indicating that it is a specific type of file-related action.

2. Attributes:
   - `newPath`: A `String` representing the destination path where the file will be copied.
   - `requestPublisher`: An instance of `RequestPublisher` used for publishing requests, likely for error handling.

3. Constructor:
   - The constructor `CopyFileAction(File file, String newPath)` initializes the action with a source file and a destination path. It also retrieves an instance of `RequestPublisher` using `RequestPublisher.getInstance()`.

4. Action Execution:
   - The `execute()` method overrides the method from the `FileAction` class. It performs the file copy operation using Java's NIO file package.
   - It uses `Files.copy` to copy the file from the source path (`super.getFile().getAbsolutePath()`) to the destination path (`this.newPath`), with the option to replace the existing file if it exists.

5. Error Handling:
   - If an exception occurs during the file copy operation, the method publishes a request with the exception details using `RequestPublisher`. This is likely used for logging or handling errors.

6. Action Type and String Representation:
   - The `getType()` method returns `ActionType.COPYFILE`, indicating the type of this action.
   - The `toString()` method provides a string representation of the `CopyFileAction`, including its type, source file path, and destination path.

7. Destination Path Accessor:
   - The `getNewPath()` method provides access to the destination path where the file will be copied.
*/

public class CopyFileAction extends FileAction {
    private String newPath;
    private RequestPublisher requestPublisher;

    public CopyFileAction(File file, String newPath) {
        super(file);
        this.newPath = newPath;
        requestPublisher = RequestPublisher.getInstance();
    }

    @Override
    public ActionType getType() {
        return ActionType.COPYFILE;
    }

    @Override
    public void execute() throws ActionException {
        Path sourcePath = Paths.get(super.getFile().getAbsolutePath()); // Percorso del file sorgente
        Path destinationPath = Paths.get(this.newPath); // Percorso di destinazione

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copiato con successo.");
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
