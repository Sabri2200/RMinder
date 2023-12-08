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

public class DeleteFileAction extends FileAction {
    private State state;
    private RequestPublisher requestPublisher;

    public DeleteFileAction(File file) {
        super(file);
        this.state = State.ACTIVE;
        this.requestPublisher = RequestPublisher.getInstance();
    }

    @Override
    public ActionType getType() {
        return ActionType.DELETEFILE;
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
        try {
            Files.deleteIfExists(Path.of(super.getFile().getAbsolutePath()));
        } catch (Exception e) {
            requestPublisher.publishRequest(new Request(RequestType.EXCEPTION, e));
        }
    }

    @Override
    public String toString() {
        return getType() + " " + super.getFile().getAbsoluteFile();
    }
}
