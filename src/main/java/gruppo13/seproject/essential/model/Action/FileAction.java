package gruppo13.seproject.essential.model.Action;

public abstract class FileAction implements Action{
    private String filePath;

    public FileAction(String filePath) {
        this.filePath = filePath;
    }


}
