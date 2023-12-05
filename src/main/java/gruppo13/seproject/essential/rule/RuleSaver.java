package gruppo13.seproject.essential.rule;

import gruppo13.seproject.FileManager.FileManager;

import java.io.File;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RuleSaver extends TimerTask {
    private RuleManager ruleManager;
    private ExecutorService executorService;
    private File file;

    public RuleSaver(File file) {
        this.file = file;
        ruleManager = RuleManager.getInstance();
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        System.out.println("saving");
        executorService.submit(() -> {
            System.out.println(file.getAbsolutePath());
            FileManager.saveRulesToFile(ruleManager.getRules(), file);
        });
        System.out.println("saved");
    }
}
