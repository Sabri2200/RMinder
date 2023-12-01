package gruppo13.seproject.essential.model.action;

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
    public Boolean execute() {
        try {
            Path sourcePath = Paths.get(getFilePath());

            Path destinationPath = Paths.get(destinationDirectory, sourcePath.getFileName().toString());

            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    @Override
    public ActionType getType() {
        return ActionType.COPYFILE;
    }
    public String toString() {
        return ActionType.DIALOGBOX.name() + ActionType.COPYFILE.name() + " " + getFilePath() + "Copy in to: " + destinationDirectory;
    }
}
