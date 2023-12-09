package gruppo13.seproject.essential.request_handler;

import gruppo13.seproject.essential.action.Action;

/*
The `RequestFactory` class is a factory class in a Java application designed to create `Request` objects of various types.

1. Factory Methods:
   - The class provides static methods to create different types of `Request` objects, each corresponding to a specific `RequestType`:
     - `createExceptionRequest(Exception e)`: Creates a request of type `EXCEPTION`, encapsulating an `Exception` object. This is likely used for error handling and reporting within the application.
     - `createExecutionRequest(Action a)`: Creates a request of type `EXECUTION`, containing an `Action` object. This type of request could be used to track or log the execution of actions.
     - `createListUpdateRequest()`: Creates a request of type `LISTUPDATE` with no data (`null`). This request type might be used to signal that a list (such as a list of rules or tasks) has been updated and needs to be processed or re-rendered.

2. Use of RequestType Enum:
   - Each method uses the `RequestType` enum to specify the type of the request being created. This enum is likely defined elsewhere in the application and includes types like `EXCEPTION`, `EXECUTION`, and `LISTUPDATE`.

3. Simplifying Request Creation:
   - By providing specific methods for creating requests, `RequestFactory` simplifies the process of request creation throughout the application. Instead of manually constructing `Request` objects with the correct type and data, other parts of the application can use these factory methods to create requests in a more streamlined and error-free manner.
*/

public class RequestFactory {
    public static Request createExceptionRequest(Exception e) {
        return new Request(RequestType.EXCEPTION, e);
    }

    public static Request createExecutionRequest(Action a) {
        return new Request(RequestType.EXECUTION, a);
    }

    public static Request createListUpdateRequest() {
        return new Request(RequestType.LISTUPDATE, null);
    }
}
