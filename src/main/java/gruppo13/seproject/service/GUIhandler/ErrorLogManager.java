package gruppo13.seproject.service.GUIhandler;

import gruppo13.seproject.service.AlertExecutor;
import gruppo13.seproject.essential.request_handler.*;

public class ErrorLogManager implements Handler {

    private Handler nextHandler;

    public ErrorLogManager() {
    }

    private static final class ErrorLogManagerInstanceHolder {
        private static final ErrorLogManager errorLogManagerInstance = new ErrorLogManager();
    }

    public static ErrorLogManager getInstance() {
        return ErrorLogManagerInstanceHolder.errorLogManagerInstance;
    }

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
        return request.getType().equals(RequestType.EXCEPTION);
    }

    public void execute(Request request) {
        if (request != null) {
            Exception e = (Exception) request.getData();
            if (e != null) {
                AlertExecutor.showErrorAlert(e);;
            }
        }
    }

}