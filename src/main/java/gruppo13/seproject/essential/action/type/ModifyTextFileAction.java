package gruppo13.seproject.essential.action.type;

import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.request_handler.RequestType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModifyTextFileAction extends FileAction {
    private String stringToAdd;
    private RequestPublisher requestPublisher;

    public ModifyTextFileAction(File file, String stringToAdd) {
        super(file);
        this.stringToAdd = stringToAdd;
        requestPublisher = RequestPublisher.getInstance();
    }

    @Override
    public void execute() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(super.getFile().getAbsoluteFile()))) {
            writer.write(this.stringToAdd);
        } catch (IOException e) {
            requestPublisher.publishRequest(new Request(RequestType.EXCEPTION, e));
        }
    }

    @Override
    public ActionType getType() {
        return ActionType.MODIFYTEXTFILE;
    }

    public String getStringToAdd() {
        return stringToAdd;
    }

    @Override
    public String toString() {
        return getType() + " " + super.getFile().getAbsolutePath() + " " + getStringToAdd();
    }

}
