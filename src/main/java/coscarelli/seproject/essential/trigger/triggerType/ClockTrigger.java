package coscarelli.seproject.essential.trigger.triggerType;

import coscarelli.seproject.essential.trigger.Trigger;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockTrigger implements Trigger {
    private LocalTime time;

    public ClockTrigger(LocalTime time) {
        this.time = time;
    }

    @Override
    public boolean verify() {
        LocalTime now = LocalTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String nowFormatted = now.format(formatter);

        if (now.equals(time)) {
            return true;
        } else if (now.isBefore(time)) {
            return false;
        } else {
            return true;
        }
    }
}
