package gruppo13.seproject.essential.model.Action;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFileAction extends FileAction{
    ActionType type;

    public DeleteFileAction(String filePath) {
        super(filePath);
        this.type = ActionType.DELETEFILE;
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
        return this.type;
    }
}
