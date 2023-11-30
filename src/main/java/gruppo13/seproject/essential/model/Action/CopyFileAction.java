package gruppo13.seproject.essential.model.Action;

public class CopyFileAction extends FileAction{
    private String destinationDirectory;

    public CopyFileAction(String filePath, String destinationDirectory) {
        super(filePath);
        this.destinationDirectory = destinationDirectory;
    }

    // TODO: 30/11/2023
    @Override
    public Boolean execute() {
        return null;
    }

    // TODO: 30/11/2023
    @Override
    public ActionType getType() {
        return null;
    }
}
