package gruppo13.seproject.service.GUIhandler;

import gruppo13.seproject.essential.request_handler.*;
import gruppo13.seproject.essential.rule.Rule;
import gruppo13.seproject.essential.rule.RuleManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
The `GUIRuleList` class is part of a Java application and is designed to manage and update a list of rules in the graphical user interface (GUI).

1. Implements Handler Interface:
   - `GUIRuleList` implements the `Handler` interface, indicating its role in a chain of responsibility pattern for handling requests.

2. Singleton Pattern Implementation:
   - The class uses a static nested class (`GUIRuleListInstanceHolder`) to implement the singleton pattern, ensuring that only one instance of `GUIRuleList` is created and used throughout the application. The `getInstance()` method provides access to this instance.

3. Observable List of Rules:
   - An `ObservableList<Rule>` named `rules` is used to store and observe changes in the list of rules. This list is initialized with the current rules from `RuleManager`.

4. Rule Manager Integration:
   - The `RuleManager` instance, obtained via `RuleManager.getInstance()`, is used to manage the underlying rules. This integration suggests that `GUIRuleList` acts as a bridge between the rule management logic and the GUI.

5. List Management Methods:
   - The `getList()` method returns the observable list of rules, allowing other parts of the application to observe and react to changes in the rule list.
   - The `setList()` method updates the observable list with the current rules from the `RuleManager`, effectively refreshing the GUI representation of the rules.

6. Request Handling:
   - The `handleRequest(Request request)` method checks if `GUIRuleList` can handle the given request. If it can, it calls `setList()` to update the rule list in the GUI. If it cannot, the request is passed to the next handler in the chain.
   - The ability to handle a request is determined by the `canHandleRequest(Request request)` method, which checks if the request type is `LISTUPDATE`.

7. Chain of Responsibility Support:
   - The `setNext(Handler handler)` method allows setting the next handler in the chain, supporting the chain of responsibility pattern.

8. Fallback Error Handling:
   - If no handler in the chain can process a request, the `handleRequest` method publishes a new exception request indicating that no handler could handle the request.
*/

public class GUIRuleList implements Handler {
    private ObservableList<Rule> rules;
    private RuleManager ruleManager;
    private Handler nextHandler;

    private static final class GUIRuleListInstanceHolder {
        private static final GUIRuleList guiRuleListInstance = new GUIRuleList();
    }

    public static GUIRuleList getInstance() {
        return GUIRuleListInstanceHolder.guiRuleListInstance;
    }

    public GUIRuleList() {
        this.ruleManager = RuleManager.getInstance();
        this.rules = FXCollections.observableArrayList(ruleManager.getRules());
    }

    public ObservableList<Rule> getList() {
        return rules;
    }

    public void setList() {
        rules.clear();
        rules.setAll(ruleManager.getRules());
    }

    @Override
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handleRequest(Request request) {
        if (canHandleRequest(request)) {
            setList();
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        } else {
            // No handler can handle the request
            RequestPublisher.getInstance().publishRequest(RequestFactory.createExceptionRequest(new Exception("Error: No handler can handle the request")));
        }
    }

    private boolean canHandleRequest(Request request) {
        return request.getType().equals(RequestType.LISTUPDATE);
    }

}