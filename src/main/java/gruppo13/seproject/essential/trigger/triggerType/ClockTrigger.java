package gruppo13.seproject.essential.trigger.triggerType;

import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.TriggerType;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockTrigger implements Trigger {
    private LocalTime time;

    public ClockTrigger(LocalTime time) {
        this.time = time;
    }

    public TriggerType getType(){
        return TriggerType.CLOCKTRIGGER;
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

    public LocalTime getTime() {
        return time;
    }
}
