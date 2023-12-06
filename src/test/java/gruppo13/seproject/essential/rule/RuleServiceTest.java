package gruppo13.seproject.essential.rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

class RuleServiceTest {

    private RulePerformer rulePerformerMock;
    private ExecutorService executorServiceMock;

    @BeforeEach
    void setUp() {
        rulePerformerMock = mock(RulePerformer.class);
        executorServiceMock = mock(ExecutorService.class);
    }

    @AfterEach
    void tearDown() {
        rulePerformerMock = null;
        executorServiceMock = null;
    }

    @Test
    void run_ExecutesRulePerformer() {
        // Arrange
        RuleService ruleService = new RuleService();
        ruleService.rulePerformer = rulePerformerMock;
        ruleService.executorService = executorServiceMock;

        // Act
        ruleService.run();

        // Assert
        // Verify that execute is called on the RulePerformer
        verify(rulePerformerMock, times(1)).execute();
        // Add additional assertions as needed
    }

    @Test
    void run_AsynchronouslyExecutesRulePerformer() {
        // Arrange
        RuleService ruleService = new RuleService();
        ruleService.rulePerformer = rulePerformerMock;
        ruleService.executorService = executorServiceMock;

        // Use CompletableFuture to handle asynchronous execution
        CompletableFuture<Void> future = CompletableFuture.runAsync(ruleService, executorServiceMock);

        // Assert
        // Wait for the asynchronous execution to complete
        future.join();

        // Verify that execute is called on the RulePerformer
        verify(rulePerformerMock, times(1)).execute();

    }



}

