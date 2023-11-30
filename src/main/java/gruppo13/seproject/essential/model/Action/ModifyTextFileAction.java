package gruppo13.seproject.essential.model.Action;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ModifyTextFileAction extends FileAction {
    private String stringToAdd;
    private ActionType type;

    public ModifyTextFileAction(String filePath, String stringToAdd) {
        super(filePath);
        this.stringToAdd = stringToAdd;
        this.type = ActionType.MOVEFILE;
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
        return this.type;
    }
}
