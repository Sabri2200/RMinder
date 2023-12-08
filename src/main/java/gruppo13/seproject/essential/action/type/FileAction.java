package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.action.Action;

import java.io.File;

public abstract class FileAction implements Action {
    private File file;

    public FileAction(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
