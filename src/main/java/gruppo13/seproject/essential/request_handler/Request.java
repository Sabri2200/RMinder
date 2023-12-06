package gruppo13.seproject.essential.request_handler;

public class Request {
    private RequestType type;
    private Object data;
    private RequestState state;

    public Request(RequestType type, Object data) {
        this.type = type;
        this.data = data;
        this.state = RequestState.NOTSOLVED;
    }

    // Metodi getter e setter
    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    public void solveRequest() {
        setState(RequestState.SOLVED);
    }

}