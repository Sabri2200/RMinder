package gruppo13.seproject.essential.trigger;

import gruppo13.seproject.essential.trigger.triggerType.ClockTrigger;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class TriggerFactory {
    public static Trigger createTrigger(Map.Entry<TriggerType, List<String>> trigger) {
        switch (trigger.getKey()) {
            case CLOCKTRIGGER:
                return createClockTrigger(trigger.getValue());
            default:
                return null;
        }
    }

    private static ClockTrigger createClockTrigger(List<String> params) {
        /*int hour = Integer.parseInt(params.get(0));
        int minute = Integer.parseInt(params.get(1));*/
        String[] t = params.get(0).split(":");
        int hour = Integer.parseInt(t[0]);
        int minute = Integer.parseInt(t[1]);
        LocalTime time = LocalTime.of(hour, minute);

        return new ClockTrigger(time);
    }
}
