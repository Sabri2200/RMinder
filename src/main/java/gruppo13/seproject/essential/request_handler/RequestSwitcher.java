package gruppo13.seproject.essential.request_handler;

// TODO: 06/12/2023 : TEST when is done
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
        nextHandler.handleRequest(request);
    }
}
