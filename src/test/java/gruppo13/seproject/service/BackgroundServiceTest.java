package gruppo13.seproject.service;

import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.rule.RuleManager;
import gruppo13.seproject.file_manager.FileManager;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;


class BackgroundServiceTest {

    private RuleManager ruleManager;
    private RequestPublisher requestPublisher;
    private FileManager fileManager;

    @BeforeEach
    void setUp() {
        // Create mock objects for dependencies
        ruleManager = mock(RuleManager.class);
        requestPublisher = mock(RequestPublisher.class);
        fileManager = mock(FileManager.class);
    }

    @Test
    void testStartService() {
        // Create the BackgroundService instance with mock dependencies
        BackgroundService backgroundService = new BackgroundService(ruleManager);

        // Invoke the startService method
        backgroundService.startService();

        // Verify that the necessary methods were called
        verify(requestPublisher, times(1)).setHandlers(anyList());
        verify(fileManager, times(1)).loadRulesFromFile(any());
        verify(ruleManager, atLeastOnce()).getRules();
    }
}
