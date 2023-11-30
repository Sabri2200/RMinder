package gruppo13.seproject.essential.model.Action;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MoveFileAction extends FileAction{
    private String destinationDirectory;
    private ActionType type;

    public MoveFileAction(String filePath, String destinationDirectory) {
        super(filePath);
        this.destinationDirectory = destinationDirectory;
        this.type = ActionType.MOVEFILE;
    }

    @Override
    public Boolean execute() {
        try {
            Path sourcePath = Paths.get(getFilePath());
            Path destinationPath = new File(destinationDirectory).toPath();

            Files.move(sourcePath, destinationPath.resolve(sourcePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);

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

    public String getDestinationDirectory() {
        return destinationDirectory;
    }
}
