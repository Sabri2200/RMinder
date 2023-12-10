package gruppo13.seproject.service;

import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.rule.RuleManager;
import gruppo13.seproject.file_manager.FileManager;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;



public class BackgroundServiceTest {

    private RuleManager ruleManager;
    private RequestPublisher requestPublisher;
    private FileManager fileManager;

    @Before
    public void setUp() {
        // Create mock objects for dependencies
        ruleManager = mock(RuleManager.class);
        requestPublisher = mock(RequestPublisher.class);
        fileManager = mock(FileManager.class);
    }

    @Test
    public void testStartService() {
        // Create the BackgroundService instance with mock dependencies
        BackgroundService backgroundService = new BackgroundService();

        // Invoke the startService method
        backgroundService.startService();

        // Verify that the necessary methods were called
        verify(requestPublisher, times(1)).setHandlers(anyList());
        verify(fileManager, times(1)).loadRulesFromFile(any());
        verify(ruleManager, atLeastOnce()).getRules();
    }
}
