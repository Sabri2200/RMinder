package gruppo13.seproject.Service;

import gruppo13.seproject.FileManager.FileManager;
import gruppo13.seproject.MainApplication;
import gruppo13.seproject.Service.GUIExcecutor.GUIExecutor;
import gruppo13.seproject.essential.rule.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
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

        List<Rule> rules = fileManager.loadRulesFromFile(file);

        if (rules != null && !rules.isEmpty()) {
            ruleManager.getRules().addAll(rules);
        }

        RuleSaver ruleSaver = new RuleSaver(file);
        timer.scheduleAtFixedRate(ruleSaver, 0, 30000);
    }
}