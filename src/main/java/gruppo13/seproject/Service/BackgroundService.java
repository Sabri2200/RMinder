package gruppo13.seproject.Service;

import gruppo13.seproject.FileManager.FileManager;
import gruppo13.seproject.Service.GUIHandler.ErrorLogManager;
import gruppo13.seproject.Service.GUIHandler.GUIExecutor;
import gruppo13.seproject.Service.GUIHandler.GUIRuleList;
import gruppo13.seproject.essential.request_handler.Handler;
import gruppo13.seproject.essential.request_handler.Request;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.request_handler.RequestType;
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

        //requestPublisher.publishRequest(new Request(RequestType.NEWTYPE, new Exception("ooo")));

        /*errorLogManager.setNext(guiExecutor);
        guiExecutor.setNext(guiRuleList);
        guiRuleList.setNext(errorLogManager);*/


        // Rules Checker every 2 sseconds
        RuleService ruleService = new RuleService();

        timer.scheduleAtFixedRate(ruleService, 0, 2000);

        // Rule Saver
        FileManager fileManager = FileManager.getInstance();
        //fileManager.registerObserver(errorLogManager);

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
}