package gruppo13.seproject.essential.request_handler;

import gruppo13.seproject.essential.action.Action;

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
