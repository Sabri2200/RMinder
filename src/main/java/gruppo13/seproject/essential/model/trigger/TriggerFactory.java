package gruppo13.seproject.essential.model.trigger;

import java.time.LocalTime;

public class TriggerFactory {
    public TriggerFactory() {}

    public Trigger createClockTrigger(LocalTime time) {
        return new ClockTrigger(time);
    }
}
