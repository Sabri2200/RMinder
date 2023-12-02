package gruppo13.seproject.essential.action;

public abstract class FileAction implements Action{
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
