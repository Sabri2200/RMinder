package gruppo13.seproject.Service.BackgroundService;

import gruppo13.seproject.MainApplication;
import gruppo13.seproject.Service.GUIExcecutor.GUIExecutor;
import gruppo13.seproject.essential.rule.RuleManager;
import gruppo13.seproject.essential.rule.RulePerformer;
import gruppo13.seproject.essential.rule.RuleSaver;
import gruppo13.seproject.essential.rule.RuleService;

import java.io.File;
import java.util.Timer;

public class BackgroundService {
    private RuleManager ruleManager;

    public BackgroundService() {
        this.ruleManager = RuleManager.getInstance();
    }

    public void startService() {
        initializeService();
    }

    private void initializeService() {
        Timer timer = new Timer();

        RulePerformer rulePerformer = new RulePerformer();
        rulePerformer.registerObserver(new GUIExecutor());

        RuleService task = new RuleService(rulePerformer);

        timer.scheduleAtFixedRate(task, 0, 2000);

        /*File file = new File(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("rules.json")));

        System.out.println(MainApplication.class.getResource("../../main-view.fxml"));
        System.out.println(file.exists());
        RuleSaver ruleSaver = new RuleSaver(file);
        timer.scheduleAtFixedRate(ruleSaver, 0, 30000);*/
    }
}