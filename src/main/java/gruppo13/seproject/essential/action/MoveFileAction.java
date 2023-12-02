package gruppo13.seproject.essential.action;

import gruppo13.seproject.essential.State;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MoveFileAction extends FileAction{
    private String destinationDirectory;

    public MoveFileAction(String filePath, String destinationDirectory) {
        super(filePath);
        this.destinationDirectory = destinationDirectory;
    }

    @Override
    public void execute() {
        try {
            Path sourcePath = Paths.get(getFilePath());
            Path destinationPath = new File(destinationDirectory).toPath();

            Files.move(sourcePath, destinationPath.resolve(sourcePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    @Override
    public ActionType getType() {
        return ActionType.MOVEFILE;
    }

    @Override
    public State getState() {
        return null;
    }

    @Override
    public void setState(State state) {

    }

    public String getDestinationDirectory() {
        return destinationDirectory;
    }
    public String toString() {
        return ActionType.MOVEFILE.name() + "to " + destinationDirectory;
    }
}
