package gruppo13.seproject.essential.trigger.type;

import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.TriggerType;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/*
The `ClockTrigger` class is a specific implementation of a `Trigger` in a Java application, designed to represent a time-based trigger.

1. Implements Trigger Interface:
   - `ClockTrigger` implements the `Trigger` interface, indicating that it is a specific type of trigger with defined behaviors.

2. LocalTime Attribute:
   - The class has a `LocalTime` attribute named `time`, which represents the specific time when the trigger is supposed to activate.

3. Constructor:
   - The constructor `ClockTrigger(LocalTime time)` initializes the object with a specified `LocalTime`.

4. Trigger Verification:
   - The `verify()` method is used to determine if the trigger condition is met. It compares the current time (`LocalTime.now()`) with the trigger's set time.
   - The trigger is considered active if the current time is equal to or after the set time. This means the trigger will activate once the current time reaches or surpasses the specified time.

5. Getting Trigger Type:
   - The `getType()` method returns the type of the trigger, which is `TriggerType.CLOCKTRIGGER` in this case. This helps in identifying the kind of trigger being dealt with, especially when working with multiple trigger types.

6. Getting Time:
   - The `getTime()` method provides access to the `LocalTime` object representing the time set for the trigger.

7. String Representation:
   - The `toString()` method provides a string representation of the `ClockTrigger`, combining the trigger type and the set time in a readable format.
*/

public class ClockTrigger implements Trigger {
    private LocalTime time;

    public ClockTrigger(LocalTime time) {
        this.time = time;
    }

    @Override
    public boolean verify() {
        LocalTime now = LocalTime.now();

        if (now.equals(time)) {
            return true;
        } else return !now.isBefore(time);
    }

    @Override
    public TriggerType getType() {
        return TriggerType.CLOCKTRIGGER;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return TriggerType.CLOCKTRIGGER + " " + time.getHour() + ":" + time.getMinute();
    }
}
