package gruppo13.seproject.Service.GUIHandler;

import gruppo13.seproject.essential.request_handler.*;
import gruppo13.seproject.essential.rule.Rule;
import gruppo13.seproject.essential.rule.RuleManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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