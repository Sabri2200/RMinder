package gruppo13.seproject.essential;

import javafx.collections.ObservableList;

import java.util.List;

public interface RuleCommand {
    public ObservableList<Rule> getList();
    public void addRule(Rule rule);
    public void removeRule(Rule rule);
    public void execute();
}
