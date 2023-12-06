package gruppo13.seproject.Service.GUIHandler;

import gruppo13.seproject.Service.AlertExecutor;
import gruppo13.seproject.essential.request_handler.*;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.type.DialogBoxAction;

public class GUIExecutor implements Handler {

    private GUIExecutor() {}
    private Handler nextHandler;

    @Override
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handleRequest(Request request) {
        if (canHandleRequest(request)) {
            execute(request);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        } else {
            // No handler can handle the request
            RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception("Error: No handler can handle the request")));
        }
    }

    private boolean canHandleRequest(Request request) {
        return request.getType().equals(RequestType.EXECUTION);
    }

    private static final class GUIExecutorInstanceHolder {
        private static final GUIExecutor guiExecutorInstance = new GUIExecutor();
    }

    public static GUIExecutor getInstance() {
        return GUIExecutorInstanceHolder.guiExecutorInstance;
    }

    public void showAlert(String title, String header, String content) {
        AlertExecutor.showAlert(title, header, content);
    }

    public synchronized void execute(Request request) {
        Action action = (Action) request.getData();
        if (action.getType() == ActionType.DIALOGBOX) {
            AlertExecutor.run((DialogBoxAction) action);
        }
    }

}