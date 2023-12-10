package gruppo13.seproject.essential.request_handler;


/*
The `RequestSwitcher` class is a component in a Java application that plays a crucial role in the chain of responsibility pattern for handling requests.

1. Implements Handler Interface:
   - `RequestSwitcher` implements the `Handler` interface, indicating its role in processing requests.

2. Singleton Pattern Implementation:
   - The class uses a static nested class (`RequestSwitcherInstanceHolder`) to implement the singleton pattern. This ensures that only one instance of `RequestSwitcher` is created and used throughout the application. The `getInstance()` method provides access to this instance.

3. Next Handler Management:
   - The `setNext(Handler handler)` method allows setting the next handler in the chain of responsibility. This is essential for the chain to function, as each handler needs to know which handler to pass the request to if it cannot handle it itself.

4. Request Handling:
   - The `handleRequest(Request request)` method is the core of this class. If `RequestSwitcher` has a `nextHandler` set, it passes the request to this handler for processing.
   - If there is no `nextHandler`, it indicates that no handler in the chain can process the request. In this case, `RequestSwitcher` creates and publishes an exception request using `RequestPublisher`, signaling an unhandled request situation.

5. Fallback Error Handling:
   - The fallback mechanism in `handleRequest` ensures that if a request cannot be processed by any handler in the chain, it doesn't get silently ignored. Instead, an exception request is published, which could be logged or handled by another part of the application.
*/

public class RequestSwitcher implements Handler {
    private Handler nextHandler;

    public RequestSwitcher() {}

    private static final class RequestSwitcherInstanceHolder {
        private static final RequestSwitcher REQUEST_SWITCHER_INSTANCE = new RequestSwitcher();
    }

    public static RequestSwitcher getInstance() {
        return RequestSwitcher.RequestSwitcherInstanceHolder.REQUEST_SWITCHER_INSTANCE;
    }

    @Override
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handleRequest(Request request) {
        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        } else {
            // No handler can handle the request
            RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception("Error: No handler can handle the request")));
        }
    }
}