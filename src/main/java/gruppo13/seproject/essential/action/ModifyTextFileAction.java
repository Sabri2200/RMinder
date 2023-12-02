package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.State;

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
    public void execute() {
        try {
            Path filePath = Paths.get(getFilePath());
            Files.write(filePath, stringToAdd.getBytes(), StandardOpenOption.APPEND);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ActionType getType() {
        return ActionType.MODIFYTEXT;
    }

    @Override
    public State getState() {
        return null;
    }

    @Override
    public void setState(State state) {

    }

    public String toString() {
        return "In  "+ ActionType.MODIFYTEXT.name() + " is added " + stringToAdd;
    }
}

