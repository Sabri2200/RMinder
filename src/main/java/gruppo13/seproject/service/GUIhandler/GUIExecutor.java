package gruppo13.seproject.service.GUIhandler;

import gruppo13.seproject.service.AlertExecutor;
import gruppo13.seproject.essential.request_handler.*;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.type.DialogBoxAction;

/*
The `GUIExecutor` class is a part of a Java application designed to handle graphical user interface (GUI) related actions.

1. Implements Handler Interface:
   - `GUIExecutor` implements the `Handler` interface, indicating its role in a chain of responsibility pattern for handling requests.

2. Singleton Pattern Implementation:
   - The class uses a static nested class (`GUIExecutorInstanceHolder`) to implement the singleton pattern. This ensures that only one instance of `GUIExecutor` is created and used throughout the application. The `getInstance()` method provides access to this instance.

3. Request Handling:
   - The `handleRequest(Request request)` method is central to this class. It determines whether the `GUIExecutor` can handle a given request. If it can, the request is processed; otherwise, it is passed to the next handler in the chain.
   - The ability to handle a request is determined by the `canHandleRequest(Request request)` method, which checks if the request type is `EXECUTION`.

4. Execution of GUI Actions:
   - The `execute(Request request)` method is responsible for executing GUI-related actions. It checks if the action type is `DIALOGBOX` and, if so, uses `AlertExecutor` to run the action.
   - This suggests that `GUIExecutor` is specifically designed to handle dialog box actions within the application.

5. Chain of Responsibility Support:
   - The `setNext(Handler handler)` method allows setting the next handler in the chain, enabling the chain of responsibility pattern.

6. Fallback Error Handling:
   - If no handler in the chain can process a request, the `handleRequest` method publishes a new exception request indicating that no handler could handle the request.

7. Alert Display Method:
   - The `showAlert(String title, String header, String content)` method provides functionality to display alerts. It delegates the alert display to `AlertExecutor.showAlert`.
*/

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