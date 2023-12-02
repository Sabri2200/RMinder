package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionObserver;
import gruppo13.seproject.essential.action.ActionSubject;
import gruppo13.seproject.essential.action.ActionException.ActionException;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RuleService extends TimerTask {
    private ExecutorService executorService;
    private RulePerformer rulePerformer;

    public RuleService(RulePerformer rulePerformer) {
        this.rulePerformer = rulePerformer;
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        System.out.println("2");

        executorService.submit(() -> {
            rulePerformer.execute();
        });

    }

}
