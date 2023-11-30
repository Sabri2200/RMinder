package gruppo13.seproject.essential.model.Action;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyFileAction extends FileAction {
    private String destinationDirectory;
    private ActionType type;

    public CopyFileAction(String filePath, String destinationDirectory) {
        super(filePath);
        this.destinationDirectory = destinationDirectory;
        this.type = ActionType.COPYFILE;
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
        return this.type;
    }
}
