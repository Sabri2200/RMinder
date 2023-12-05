package gruppo13.seproject.Service.GUIRuleList;

import gruppo13.seproject.essential.rule.ListObserver.ListObserver;
import gruppo13.seproject.essential.rule.Rule;
import gruppo13.seproject.essential.rule.RuleManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GUIRuleList implements ListObserver {
    private ObservableList<Rule> rules;
    private RuleManager ruleManager;

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
        System.out.println(getList());
    }

    @Override
    public void update() {
        setList();
    }
}