package gruppo13.seproject.Service.GUIHandler;

import gruppo13.seproject.essential.request_handler.Handler;
import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestType;
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
        }
    }

    private boolean canHandleRequest(Request request) {
        System.out.println("RequestType.LISTUPDATE");
        return request.getType().equals(RequestType.LISTUPDATE);
    }

}