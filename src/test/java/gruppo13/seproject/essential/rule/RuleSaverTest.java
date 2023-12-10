package gruppo13.seproject.essential.rule;

import static org.mockito.Mockito.*;

import java.io.File;
import java.util.concurrent.ExecutorService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class RuleSaverTest {

    private RuleManager ruleManagerMock;
    private ExecutorService executorServiceMock;
    private File fileMock;

    @Before
    public void setUp() {
        ruleManagerMock = mock(RuleManager.class);
        executorServiceMock = mock(ExecutorService.class);
        fileMock = mock(File.class);
    }

    @After
    public void tearDown() {
        ruleManagerMock = null;
        executorServiceMock = null;
        fileMock = null;
    }

    @Test
    public void run_SavesRulesToFile() {
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
