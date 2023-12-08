package gruppo13.seproject.essential.request_handler;

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

    public Handler getNextHandler() {
        return nextHandler;
    }
}
