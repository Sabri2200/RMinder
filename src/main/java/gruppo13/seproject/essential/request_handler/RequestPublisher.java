package gruppo13.seproject.essential.request_handler;

import java.util.List;

/*
The `RequestPublisher` class is a key component in a Java application, designed to publish requests to a chain of handlers.

1. Singleton Pattern Implementation:
   - The class uses a static nested class (`RequestCollectionInstanceHolder`) to implement the singleton pattern, ensuring that only one instance of `RequestPublisher` is created and used throughout the application. The `getInstance()` method provides access to this instance.

2. RequestSwitcher Integration:
   - The `RequestPublisher` holds a `RequestSwitcher` instance, obtained via `RequestSwitcher.getInstance()`. The `RequestSwitcher` is likely responsible for directing requests to the appropriate handler based on the request type or other criteria.

3. Publishing Requests:
   - The `publishRequest(Request request)` method is used to publish a `Request` object to the request handling system. It synchronizes access to ensure thread safety, which is important in a multi-threaded environment.

4. Setting Handlers:
   - The `setHandlers(List<Handler> handlers)` method allows setting a list of `Handler` objects that will form a chain of responsibility for handling requests.
   - It sets up the chain by assigning each handler a `next` handler, except for the last one in the list. This method is crucial for configuring the request handling pipeline.

5. Chain of Responsibility Pattern:
   - The design of setting up handlers in a sequence and the use of `RequestSwitcher` suggest that the `RequestPublisher` employs the chain of responsibility pattern. This pattern allows a request to be passed along a chain of handlers until one of them handles it.
*/

public class RequestPublisher {
    private RequestSwitcher requestSwitcher;

    public RequestPublisher() {
        this.requestSwitcher = RequestSwitcher.getInstance();
    }


    private static final class RequestCollectionInstanceHolder {
        private static final RequestPublisher REQUEST_PUBLISHER_INSTANCE = new RequestPublisher();
    }

    public static RequestPublisher getInstance() {
        return RequestCollectionInstanceHolder.REQUEST_PUBLISHER_INSTANCE;
    }

    public synchronized void publishRequest(Request request) {
        requestSwitcher.handleRequest(request);
    }

    public void setHandlers(List<Handler> handlers) {
        if (!handlers.isEmpty()) {
            requestSwitcher.setNext(handlers.get(0));

            for (int i = 0; i < handlers.size() - 1; i++) {
                handlers.get(i).setNext(handlers.get(i + 1));
            }

            handlers.get(handlers.size() - 1).setNext(null);
        }
    }

    public void setRequestSwitcher(RequestSwitcher requestSwitcher) {
        this.requestSwitcher = requestSwitcher;
    }

}