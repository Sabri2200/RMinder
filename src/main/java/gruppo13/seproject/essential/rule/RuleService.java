package gruppo13.seproject.essential.rule;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RuleService extends TimerTask {
    private ExecutorService executorService;
    private RulePerformer rulePerformer;

    public RuleService() {
        this.rulePerformer = RulePerformer.getInstance();
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        executorService.submit(() -> {
                rulePerformer.execute();
        });

    }

}
