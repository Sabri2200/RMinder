package gruppo13.seproject.essential.rule;

import static org.junit.jupiter.api.Assertions.*;
import gruppo13.seproject.essential.rule.RuleSaver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.concurrent.ExecutorService;

class RuleSaverTest {

    private RuleManager ruleManagerMock;
    private ExecutorService executorServiceMock;
    private File fileMock;

    @BeforeEach
    void setUp() {
        ruleManagerMock = mock(RuleManager.class);
        executorServiceMock = mock(ExecutorService.class);
        fileMock = mock(File.class);
    }

    @AfterEach
    void tearDown() {
        ruleManagerMock = null;
        executorServiceMock = null;
        fileMock = null;
    }

    @Test
    void run_SavesRulesToFile() {
        // Arrange
        RuleSaver ruleSaver = new RuleSaver(fileMock);
        ruleSaver.ruleManager = ruleManagerMock;
        ruleSaver.executorService = executorServiceMock;

        // Act
        ruleSaver.run();

        // Assert
        // Verify that saveRulesToFile is called with the correct arguments
        verify(ruleManagerMock, times(1)).getRules();
        verify(executorServiceMock, times(1)).submit(any(Runnable.class));
    }


}
