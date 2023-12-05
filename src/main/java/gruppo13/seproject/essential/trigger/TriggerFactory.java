package gruppo13.seproject.essential.trigger;

import gruppo13.seproject.essential.ErrorLog;
import gruppo13.seproject.essential.trigger.type.ClockTrigger;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TriggerFactory {
    private static final ErrorLog errorLog = ErrorLog.getInstance();

    public static Trigger createTrigger(Map.Entry<TriggerType, List<String>> trigger) {
        if (Objects.requireNonNull(trigger.getKey()) == TriggerType.CLOCKTRIGGER) {
            return createClockTrigger(trigger.getValue());
        } else {
            return null;
        }
    }

    private static ClockTrigger createClockTrigger(List<String> params) {
        String[] t = params.get(0).split(":");

        try {
            int hour = Integer.parseInt(t[0]);
            int minute = Integer.parseInt(t[1]);
            LocalTime time = LocalTime.of(hour, minute);
            return new ClockTrigger(time);
        } catch (NumberFormatException e) {
            errorLog.addException(new Exception("Number format not valid", e));
        } catch (
        DateTimeException e) {
            errorLog.addException(new Exception("", e));
        } catch (IllegalArgumentException e) {
            errorLog.addException(e);
        }

        return null;
    }
}
