package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.ActionType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyFileAction extends FileAction {
    private String destinationDirectory;

    public CopyFileAction(String filePath, String destinationDirectory) {
        super(filePath);
        this.destinationDirectory = destinationDirectory;
    }

    @Override
    public void execute() {
        try {
            Path sourcePath = Paths.get(getFilePath());

            Path destinationPath = Paths.get(destinationDirectory, sourcePath.getFileName().toString());

            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    @Override
    public ActionType getType() {
        return ActionType.COPYFILE;
    }

    @Override
    public State getState() {
        return null;
    }

    @Override
    public void setState(State state) {

    }

    public String toString() {
        return ActionType.DIALOGBOX.name() + ActionType.COPYFILE.name() + " " + getFilePath() + "Copy in to: " + destinationDirectory;
    }
}
