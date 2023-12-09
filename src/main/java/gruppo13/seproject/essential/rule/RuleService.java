package gruppo13.seproject.essential.rule;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
The `RuleService` class is a Java utility class designed to periodically execute rules within an application. It extends `TimerTask`, making it suitable for scheduled execution in a timer-based manner.

1. Attributes:
   - `executorService`: An `ExecutorService` used for managing asynchronous task execution.
   - `rulePerformer`: An instance of `RulePerformer` used to execute the rules.

2. Constructor:
   - The constructor initializes the `rulePerformer` with an instance obtained from `RulePerformer.getInstance()`, adhering to the singleton pattern.
   - It also initializes `executorService` with a cached thread pool (`Executors.newCachedThreadPool()`). This type of thread pool is efficient for short-lived asynchronous tasks and can dynamically allocate threads as needed.

3. Scheduled Rule Execution:
   - The `run()` method, which overrides the method from `TimerTask`, is the entry point for the scheduled task. It submits a task to `executorService` that calls `rulePerformer.execute()`. This design allows the rule execution process to be performed asynchronously, ensuring that the main application thread is not blocked.

4. Use of ExecutorService:
   - The use of a cached thread pool for `executorService` is suitable for tasks that have a variable execution time and are not constantly running. It helps in efficiently managing system resources by reusing threads and creating new ones as needed.

5. Integration with RulePerformer:
   - `RuleService` integrates closely with `RulePerformer` for executing rules. This separation of concerns allows `RuleService` to focus on the scheduling and asynchronous execution of tasks, while `RulePerformer` handles the specifics of rule execution.
*/

public class RuleService extends TimerTask {
    protected ExecutorService executorService;
    protected RulePerformer rulePerformer;

    public RuleService() {
        this.rulePerformer = RulePerformer.getInstance();
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        executorService.submit(() -> rulePerformer.execute());
    }

}
