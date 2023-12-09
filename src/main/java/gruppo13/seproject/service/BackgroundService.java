package gruppo13.seproject.service;

import gruppo13.seproject.essential.request_handler.RequestFactory;
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

/*
The `BackgroundService` class is designed to manage background tasks related to rule management in a Java application. Here's a summary of its key functionalities:

1. Rule Manager Initialization:
   - The class has a `RuleManager` instance which is obtained through a singleton pattern (`RuleManager.getInstance()`). This manager is central to handling rules within the application.

2. Service Initialization:
   - The `startService()` method is the public interface to start the background service. It internally calls `initializeService()` to set up various components.

3. Service Setup in `initializeService()`:
   - A `Timer` object is created to schedule tasks at fixed rates.
   - Various handlers are initialized:
     - `ErrorLogManager`: Manages error logging.
     - `GUIExecutor`: Handles execution of GUI-related tasks.
     - `GUIRuleList`: Manages the list of rules in the GUI.
   - These handlers are added to a `RequestPublisher` instance, which is responsible for publishing different types of requests in the application.
   - `RuleService` is scheduled to run every 2 seconds. This service likely checks and executes rules based on certain conditions or triggers.
   - `RuleSaver` is scheduled to run every 30 seconds. This component is responsible for saving the current state of rules to a file.

4. File Management for Rules:
   - The service checks for the existence of a "rules.json" file and creates it if it doesn't exist. This file is used to persist rules.
   - It loads rules from this file using `FileManager` and adds them to the `RuleManager`. This suggests that the application can maintain state across sessions by saving and loading rules.

5. Error Handling:
   - In case of an IOException while creating the "rules.json" file, an exception request is published using `RequestPublisher`.

In summary, `BackgroundService` is a crucial component of the application that initializes and manages various background tasks related to rule processing, including checking, executing, saving, and loading rules, as well as handling GUI updates and error logging. It ensures that rules are persistently managed and the application's state is maintained across sessions.
*/

public class BackgroundService {
    private RuleManager ruleManager;

    public BackgroundService() {
        this.ruleManager = RuleManager.getInstance();
    }

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

        // Rules Checker
        RulePerformer rulePerformer = RulePerformer.getInstance();

        // Adding handlers to Request Publisher
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

        // Loading rules from file
        File file = new File("rules.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                requestPublisher.publishRequest(RequestFactory.createExceptionRequest(e));
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