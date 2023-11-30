package gruppo13.seproject.essential.model.Action;

public class ModifyTextFileAction extends FileAction {
    private String stringToAdd;

    public ModifyTextFileAction(String filePath, String stringToAdd) {
        super(filePath);
        this.stringToAdd = stringToAdd;
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
