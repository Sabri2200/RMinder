package gruppo13.seproject.essential.trigger;

import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.trigger.type.ClockTrigger;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/*
The `TriggerFactory` class is a factory class in a Java application designed to create `Trigger` objects, specifically for handling triggers related to time-based events.

1. Request Publisher Integration:
   - The class uses a `RequestPublisher` instance, obtained via `RequestPublisher.getInstance()`, which is likely used for logging or handling exceptions.

2. Trigger Creation:
   - The `createTrigger(Map.Entry<TriggerType, List<String>> trigger)` method is the primary method for creating `Trigger` objects. It checks the type of trigger requested and delegates to a specific method for each trigger type.
   - Currently, it only supports creating `ClockTrigger` objects, which are time-based triggers.

3. ClockTrigger Creation:
   - The `createClockTrigger(List<String> params)` method is responsible for creating a `ClockTrigger` object.
   - It expects a list of parameters where the first element is a string representing time in the format "hour:minute".
   - The method splits this string, parses the hour and minute, and creates a `LocalTime` object, which is then used to instantiate a `ClockTrigger`.

4. Error Handling:
   - If there is an error during the parsing of the time (such as a `NumberFormatException`), the method catches the exception and publishes an exception request using the `RequestPublisher`.
   - If an exception occurs, the method returns `null`, indicating the failure to create a valid `ClockTrigger`.

5. Extensibility:
   - The design suggests that the factory is intended to be extended in the future to support more types of triggers, as indicated by the `else` branch returning `null` in the `createTrigger` method.
*/

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
            requestPublisher.publishRequest(RequestFactory.createExceptionRequest(e));
        }
        return null;
    }
}
