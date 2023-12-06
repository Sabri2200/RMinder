package gruppo13.seproject.essential.rule;

import gruppo13.seproject.file_manager.FileManager;

import java.io.File;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RuleSaver extends TimerTask {
    protected RuleManager ruleManager;
    protected ExecutorService executorService;
    private File file;
    private FileManager fileManager;

    public RuleSaver(File file) {
        this.file = file;
        ruleManager = RuleManager.getInstance();
        fileManager = FileManager.getInstance();
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        executorService.submit(() -> {
            fileManager.saveRulesToFile(ruleManager.getRules(), file);
        });
    }
}
