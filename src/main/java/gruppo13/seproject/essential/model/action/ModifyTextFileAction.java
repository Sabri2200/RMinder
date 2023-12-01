package gruppo13.seproject.essential.model.action;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ModifyTextFileAction extends FileAction {
    private String stringToAdd;

    public ModifyTextFileAction(String filePath, String stringToAdd) {
        super(filePath);
        this.stringToAdd = stringToAdd;
    }

    @Override
    public Boolean execute() {
        try {
            Path filePath = Paths.get(getFilePath());
            Files.write(filePath, stringToAdd.getBytes(), StandardOpenOption.APPEND);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public ActionType getType() {
        return ActionType.MODIFYTEXT;
    }

    public String toString() {
        return "In  "+ ActionType.MODIFYTEXT.name() + " is added " + stringToAdd;
    }
}
