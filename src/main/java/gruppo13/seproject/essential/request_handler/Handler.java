package gruppo13.seproject.essential.request_handler;

public interface Handler {
    void setNext(Handler handler);
    void handleRequest(Request request);
}
