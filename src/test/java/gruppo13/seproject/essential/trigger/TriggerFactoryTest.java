package gruppo13.seproject.essential.trigger;
import gruppo13.seproject.essential.trigger.type.ClockTrigger;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TriggerFactoryTest {

    @Test
    public void testCreateClockTrigger() {
        // Test data
        String timeString = "12:30";
        List<String> params = List.of(timeString);
        Map.Entry<TriggerType, List<String>> triggerEntry = Map.entry(TriggerType.CLOCKTRIGGER, params);

        // Test creating a ClockTrigger
        Trigger trigger = TriggerFactory.createTrigger(triggerEntry);

        // Assert
        assertNotNull(trigger);
        assertTrue(trigger instanceof ClockTrigger);

        // Verify the time set in the ClockTrigger
        ClockTrigger clockTrigger = (ClockTrigger) trigger;
        assertEquals(12, clockTrigger.getTime().getHour());
        assertEquals(30, clockTrigger.getTime().getMinute());
    }

    @Test
    public void testCreateClockTriggerWithInvalidParams() {
        // Test data with invalid time format
        String invalidTimeString = "invalid_time_format";
        List<String> invalidParams = List.of(invalidTimeString);
        Map.Entry<TriggerType, List<String>> invalidTriggerEntry = Map.entry(TriggerType.CLOCKTRIGGER, invalidParams);

        // Test creating a ClockTrigger with invalid params
        Trigger invalidTrigger = TriggerFactory.createTrigger(invalidTriggerEntry);

        // Assert that the invalid trigger is null
        assertNull(invalidTrigger);
    }

    @Test(expected = Exception.class)
    public void testCreateTriggerWithUnsupportedType() {
        // Test data with an unsupported trigger type
        List<String> params = List.of("some_params");
        Map.Entry<TriggerType, List<String>> unsupportedTriggerEntry = Map.entry(TriggerType.valueOf("none"), params);

        // Test creating a trigger with an unsupported type
        Trigger unsupportedTrigger = TriggerFactory.createTrigger(unsupportedTriggerEntry);

        // Assert that the unsupported trigger is null
        assertNull(unsupportedTrigger);
    }
}
