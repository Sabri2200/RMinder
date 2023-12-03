package gruppo13.seproject.essential.action.actionType;

import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionPerformer;

public abstract class FileAction extends Action {
    private String filePath;

    public FileAction(String filePath) {
        this.filePath = filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
