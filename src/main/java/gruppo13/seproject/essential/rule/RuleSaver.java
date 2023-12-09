package gruppo13.seproject.essential.rule;

import gruppo13.seproject.file_manager.FileManager;

import java.io.File;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
The `RuleSaver` class is a specialized utility class in a Java application designed to periodically save rules to a file. It extends `TimerTask`, making it suitable for scheduled execution.

1. Attributes:
   - `ruleManager`: An instance of `RuleManager` used to access the current set of rules.
   - `executorService`: An `ExecutorService` for managing asynchronous task execution.
   - `file`: A `File` object representing the file where rules will be saved.
   - `fileManager`: An instance of `FileManager` used for file operations.

2. Constructor:
   - The constructor `RuleSaver(File file)` initializes the class with a specific file. It retrieves instances of `RuleManager` and `FileManager` using their respective singleton `getInstance()` methods. It also initializes `executorService` with a cached thread pool, which is efficient for short-lived asynchronous tasks.

3. Scheduled Rule Saving:
   - The `run()` method, which overrides the method from `TimerTask`, is the entry point for the scheduled task. It submits a task to `executorService` that calls `fileManager.saveRulesToFile(...)`, passing the current list of rules and the specified file. This design allows the rule saving process to be executed asynchronously, without blocking the main thread.

4. Use of ExecutorService:
   - The use of `Executors.newCachedThreadPool()` for `executorService` suggests that the task of saving rules might not be frequent or regular enough to require a fixed-size thread pool. A cached thread pool creates new threads as needed but will reuse previously constructed threads when they are available.

5. Integration with FileManager and RuleManager:
   - `RuleSaver` integrates closely with `FileManager` for handling file write operations and `RuleManager` for accessing the current rules. This design encapsulates the responsibility of rule persistence, separating it from other business logic.
*/

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
