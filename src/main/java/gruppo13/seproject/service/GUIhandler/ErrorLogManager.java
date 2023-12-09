package gruppo13.seproject.service.GUIhandler;

import gruppo13.seproject.service.AlertExecutor;
import gruppo13.seproject.essential.request_handler.*;

/*
The `ErrorLogManager` class is part of a Java application that handles error logging.

1. Implements Handler Interface:
   - `ErrorLogManager` implements the `Handler` interface, suggesting it's part of a chain of responsibility pattern where different handlers process requests.

2. Singleton Pattern Implementation:
   - The class uses a static nested class (`ErrorLogManagerInstanceHolder`) for the singleton pattern, ensuring that only one instance of `ErrorLogManager` is created. The `getInstance()` method provides access to this instance.

3. Request Handling:
   - The `handleRequest(Request request)` method is the core of this class. It checks if the `ErrorLogManager` can handle the given request. If it can, it processes the request; otherwise, it passes the request to the next handler in the chain.
   - The ability to handle a request is determined by the `canHandleRequest(Request request)` method, which checks if the request type is `EXCEPTION`.

4. Error Processing:
   - If the `ErrorLogManager` can handle the request (i.e., it's an exception request), the `execute(Request request)` method is called.
   - This method extracts the exception from the request and displays an error alert using `AlertExecutor.showErrorAlert(e)`.

5. Chain of Responsibility Support:
   - The `setNext(Handler handler)` method allows setting the next handler in the chain, supporting the chain of responsibility pattern.

6. Fallback Error Handling:
   - If no handler in the chain can process a request, the `handleRequest` method publishes a new exception request indicating that no handler could handle the request.
*/

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