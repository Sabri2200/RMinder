package gruppo13.seproject.essential.trigger.type;
import gruppo13.seproject.essential.trigger.Trigger;
import java.time.LocalTime;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClockTriggerTest {

    @Test
    void verifyShouldReturnTrueWhenCurrentTimeIsEqualToTriggerTime() {
        // Arrange
        LocalTime triggerTime = LocalTime.now();
        Trigger clockTrigger = new ClockTrigger(triggerTime);

        // Act
        boolean result = clockTrigger.verify();

        // Assert
        assertTrue(result);
    }

    @Test
    void verifyShouldReturnTrueWhenCurrentTimeIsAfterTriggerTime() {
        // Arrange
        LocalTime triggerTime = LocalTime.now().minusMinutes(1); // Set trigger time 1 minute ago
        Trigger clockTrigger = new ClockTrigger(triggerTime);

        // Act
        boolean result = clockTrigger.verify();

        // Assert
        assertTrue(result);
    }

    @Test
    void verifyShouldReturnFalseWhenCurrentTimeIsBeforeTriggerTime() {
        // Arrange
        LocalTime triggerTime = LocalTime.now().plusMinutes(1); // Set trigger time 1 minute in the future
        Trigger clockTrigger = new ClockTrigger(triggerTime);

        // Act
        boolean result = clockTrigger.verify();

        // Assert
        assertFalse(result);
    }

    @Test
    void getTypeShouldReturnClockTriggerType() {
        // Arrange
        Trigger clockTrigger = new ClockTrigger(LocalTime.now());

        // Act
        String triggerType = clockTrigger.getType().toString();

        // Assert
        assertEquals("CLOCKTRIGGER", triggerType);
    }

    @Test
    void toStringShouldReturnFormattedString() {
        // Arrange
        LocalTime triggerTime = LocalTime.now();
        Trigger clockTrigger = new ClockTrigger(triggerTime);

        // Act
        String triggerString = clockTrigger.toString();

        // Assert
        assertEquals("CLOCKTRIGGER " + triggerTime.getHour() + ":" + triggerTime.getMinute(), triggerString);
    }
}
