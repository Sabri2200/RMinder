package gruppo13.seproject.essential.trigger;

import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.trigger.type.ClockTrigger;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TriggerFactory {
    private static final RequestPublisher requestPublisher = RequestPublisher.getInstance();

    public static Trigger createTrigger(Map.Entry<TriggerType, List<String>> trigger) {
        if (Objects.requireNonNull(trigger.getKey()) == TriggerType.CLOCKTRIGGER) {
            return createClockTrigger(trigger.getValue());
        } else {
            return null;
        }
    }

    protected static ClockTrigger createClockTrigger(List<String> params) {
        String[] t = params.get(0).split(":");

        try {
            int hour = Integer.parseInt(t[0]);
            int minute = Integer.parseInt(t[1]);
            LocalTime time = LocalTime.of(hour, minute);
            return new ClockTrigger(time);
        } catch (Exception e) {
            System.out.println(t[0]);
            requestPublisher.publishRequest(RequestFactory.createExceptionRequest(e));
        }
        return null;
    }
}
