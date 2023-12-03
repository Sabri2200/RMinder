package gruppo13.seproject.essential.rule;

import gruppo13.seproject.essential.action.exception.ActionException;

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
            try {
                rulePerformer.execute();
            } catch (ActionException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
