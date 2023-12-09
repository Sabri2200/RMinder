package gruppo13.seproject.essential.request_handler;

/*
The `Request` class is a fundamental component in a Java application, designed to encapsulate information about various types of requests.

1. Attributes:
   - `type`: A `RequestType` enum value representing the type of the request. This could be used to categorize requests into different types like execution, exception, list update, etc.
   - `data`: An `Object` to hold the data associated with the request. This attribute is generic, allowing the request to carry various types of data.
   - `state`: A `RequestState` enum value representing the state of the request, which can be either `NOTSOLVED` or `SOLVED`.

2. Constructor:
   - The constructor `Request(RequestType type, Object data)` initializes a new request with the specified type and data. The state of the request is set to `NOTSOLVED` by default.

3. Getters and Setters:
   - The class provides getter and setter methods for all its attributes (`getType`, `setType`, `getData`, `setData`, `getState`, `setState`). These methods allow other parts of the application to access and modify the request's properties.

4. Request Resolution:
   - The `solveRequest()` method is a convenience method to mark the request as solved. It sets the request's state to `SOLVED`. This could be used to track the processing of requests and ensure they are handled appropriately.
*/

public class Request {
    private RequestType type;
    private Object data;
    private RequestStatus status;

    public Request(RequestType type, Object data) {
        this.type = type;
        this.data = data;
        this.status = RequestStatus.NOTSOLVED;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public void solveRequest() {
        setStatus(RequestStatus.SOLVED);
    }

}