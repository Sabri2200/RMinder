package gruppo13.seproject.Service;

import gruppo13.seproject.FileManager.FileManager;
import gruppo13.seproject.MainApplication;
import gruppo13.seproject.Service.GUIExcecutor.GUIExecutor;
import gruppo13.seproject.essential.rule.RuleManager;
import gruppo13.seproject.essential.rule.RulePerformer;
import gruppo13.seproject.essential.rule.RuleSaver;
import gruppo13.seproject.essential.rule.RuleService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
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

        // Exception Handler
        ErrorLogManager errorLogManager = new ErrorLogManager();

        timer.scheduleAtFixedRate(errorLogManager, 0, 5000);

        // Rules Checker
        RulePerformer rulePerformer = new RulePerformer();
        rulePerformer.registerObserver(GUIExecutor.getInstance());

        RuleService ruleService = new RuleService(rulePerformer);

        timer.scheduleAtFixedRate(ruleService, 0, 2000);

        // Rule Saver
        FileManager fileManager = FileManager.getInstance();

        File file = new File("rules.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        ruleManager.getRules().addAll(Objects.requireNonNull(fileManager.loadRulesFromFile(file)));

        RuleSaver ruleSaver = new RuleSaver(file);
        timer.scheduleAtFixedRate(ruleSaver, 0, 30000);
    }
}