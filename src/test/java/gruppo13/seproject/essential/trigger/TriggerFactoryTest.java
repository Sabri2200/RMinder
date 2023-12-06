package gruppo13.seproject.essential.trigger;

import gruppo13.seproject.essential.trigger.type.ClockTrigger;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TriggerFactoryTest {

    @Test
    void createTrigger_WithClockTriggerType_ReturnsClockTrigger() {
        // Arrange
        TriggerType triggerType = TriggerType.CLOCKTRIGGER;
        List<String> params = List.of("12:30");
        Map.Entry<TriggerType, List<String>> triggerEntry = Map.entry(triggerType, params);

        // Act
        Trigger trigger = TriggerFactory.createTrigger(triggerEntry);

        // Assert
        assertNotNull(trigger);
        assertTrue(trigger instanceof ClockTrigger);
    }

    @Test
    void createTrigger_WithInvalidType_ReturnsNull() {
        // Arrange
        TriggerType triggerType = TriggerType.INVALID_TYPE;
        List<String> params = List.of("12:30");
        Map.Entry<TriggerType, List<String>> triggerEntry = Map.entry(triggerType, params);

        // Act
        Trigger trigger = TriggerFactory.createTrigger(triggerEntry);

        // Assert
        assertNull(trigger);
    }

    @Test
    void createClockTrigger_WithValidParams_ReturnsClockTrigger() {
        // Arrange
        List<String> params = List.of("12:30");

        // Act
        ClockTrigger clockTrigger = TriggerFactory.createClockTrigger(params);

        // Assert
        assertNotNull(clockTrigger);
    }

    @Test
    void createClockTrigger_WithInvalidParams_ReturnsNull() {
        // Arrange
        List<String> params = List.of("invalid");

        // Act
        ClockTrigger clockTrigger = TriggerFactory.createClockTrigger(params);

        // Assert
        assertNull(clockTrigger);
    }



}

