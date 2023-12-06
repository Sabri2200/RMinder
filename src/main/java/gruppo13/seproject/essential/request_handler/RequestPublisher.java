package gruppo13.seproject.essential.request_handler;

import java.util.List;

public class RequestPublisher {
    private RequestSwitcher requestSwitcher;

    public RequestPublisher() {
        this.requestSwitcher = new RequestSwitcher();
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
        requestSwitcher.setNext(handlers.get(0));

        for (int i = 0; i<handlers.size()-1;i++) {
            handlers.get(i).setNext(handlers.get(i + 1));
        }

        handlers.get(handlers.size()-1).setNext(requestSwitcher);
        // gestire quando nessun handler può gestire la richiesta.
    }

}