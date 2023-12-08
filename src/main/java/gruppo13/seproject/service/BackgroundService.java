package gruppo13.seproject.service;

import gruppo13.seproject.file_manager.FileManager;
import gruppo13.seproject.service.GUIhandler.ErrorLogManager;
import gruppo13.seproject.service.GUIhandler.GUIExecutor;
import gruppo13.seproject.service.GUIhandler.GUIRuleList;
import gruppo13.seproject.essential.request_handler.Handler;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.rule.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class BackgroundService {
    private RuleManager ruleManager;

    public BackgroundService() {
        this.ruleManager = RuleManager.getInstance();
    }
    // other contructor for testing


    public BackgroundService(RuleManager ruleManager) {
        this.ruleManager = ruleManager;
    }

    public void startService() {
        initializeService();
    }

    private void initializeService() {
        Timer timer = new Timer();

        // Exception Handler
        ErrorLogManager errorLogManager = ErrorLogManager.getInstance();

        // Action Handler
        GUIExecutor guiExecutor = GUIExecutor.getInstance();

        // List Updater
        GUIRuleList guiRuleList = GUIRuleList.getInstance();

        //ruleManager.registerObserver(guiRuleList);

        // Rules Checker
        RulePerformer rulePerformer = RulePerformer.getInstance();

        //rulePerformer.registerObserver(guiExecutor);
        //rulePerformer.registerObserver(errorLogManager);

        List<Handler> handlers = new ArrayList<>();

        handlers.add(errorLogManager);
        handlers.add(guiExecutor);
        handlers.add(guiRuleList);

        RequestPublisher requestPublisher = RequestPublisher.getInstance();
        requestPublisher.setHandlers(handlers);


        // Rules Checker every 2 sseconds
        RuleService ruleService = new RuleService();

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

            for (Rule rule : rules) {
                ruleManager.addRule(rule);
            }

        }

        RuleSaver ruleSaver = new RuleSaver(file);
        timer.scheduleAtFixedRate(ruleSaver, 0, 30000);
    }

    public void setRuleManager(RuleManager ruleManager) {
        this.ruleManager = ruleManager;
    }
}