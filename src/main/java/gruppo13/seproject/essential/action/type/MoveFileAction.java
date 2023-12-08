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
        Path destinationPath = Paths.get(this.newPath); // Percorso di destinazione

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
