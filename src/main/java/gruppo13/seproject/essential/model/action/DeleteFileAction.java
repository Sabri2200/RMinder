package gruppo13.seproject.essential.model.action;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFileAction extends FileAction{

    public DeleteFileAction(String filePath) {
        super(filePath);
    }

    @Override
    public Boolean execute() {
        try {
            Path filePath = Paths.get(getFilePath());
            Files.delete(filePath);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public ActionType getType() {
        return ActionType.DELETEFILE;
    }
    public String toString() {
        return ActionType.DELETEFILE.name() + " " + getFilePath();
    }
}
