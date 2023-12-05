package gruppo13.seproject.essential;

import java.util.Comparator;
import java.util.Stack;

public class ErrorLog {
    private Stack<Exception> exceptionStack;

    public ErrorLog() {
        this.exceptionStack = new Stack<>();
    }

    private static final class ErrorLogInstanceHolder {
        private static final ErrorLog errorLogInstance = new ErrorLog();
    }

    public static ErrorLog getInstance() {
        return ErrorLogInstanceHolder.errorLogInstance;
    }

    public synchronized void addException(Exception e) {
        exceptionStack.push(e);
    }

    public synchronized Exception getException() {
        return exceptionStack.pop();
    }

    public boolean isEmpty() {
        return exceptionStack.isEmpty();
    }
}
