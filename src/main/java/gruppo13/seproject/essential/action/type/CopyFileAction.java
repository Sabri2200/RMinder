package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.State;
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

public class CopyFileAction extends FileAction {
    private String newPath;
    private State state;
    private RequestPublisher requestPublisher;

    public CopyFileAction(File file, String newPath) {
        super(file);
        this.newPath = newPath;
        this.state = State.ACTIVE;
        requestPublisher = RequestPublisher.getInstance();
    }

    @Override
    public ActionType getType() {
        return ActionType.COPYFILE;
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
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
