package gruppo13.seproject;

import gruppo13.seproject.essential.model.trigger.ClockTrigger;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

public class ClockTriggerTest {

    //verify Trigger Should Return True WhenCurrent Time Is After Trigger Time
    @Test
    public void TestverifyTrigger() {

        LocalTime triggerTime = LocalTime.now().minusMinutes(1); // set trigger time one minute ago
        ClockTrigger clockTrigger = new ClockTrigger(triggerTime);

        // Act
        boolean result = clockTrigger.verify();

        // Assert
        System.out.println(result);
    }


}
