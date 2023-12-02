package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.State;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFileAction extends FileAction{

    public DeleteFileAction(String filePath) {
        super(filePath);
    }

    @Override
    public void execute() {
        try {
            Path filePath = Paths.get(getFilePath());
            Files.delete(filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public ActionType getType() {
        return ActionType.DELETEFILE;
    }

    @Override
    public State getState() {
        return null;
    }

    @Override
    public void setState(State state) {

    }

    public String toString() {
        return ActionType.DELETEFILE.name() + " " + getFilePath();
    }
}

