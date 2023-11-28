package gruppo13.seproject.essential;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Duration;

import java.util.List;

public class RuleManager implements RuleCommand {
    private ObservableList<Rule> rules;
    private Service<Void> backgroundService;

    public RuleManager(Service<Void> backgroundService) {
        this.rules = FXCollections.observableArrayList();
        this.backgroundService = backgroundService;
    }

    @Override
    public ObservableList<Rule> getList() {
        return this.rules;
    }

    public void addRule(Rule rule) {
        if (!this.rules.contains(rule)) {
            this.rules.add(rule);
        }
    }

    @Override
    public void removeRule(Rule rule) {
        if (this.rules.contains(rule)) {
            rules.remove(rule);
        }
    }

    @Override
    public void execute() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            if (!backgroundService.isRunning()) {
                backgroundService.restart();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
