package gruppo13.seproject.essential.trigger.type;

import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.TriggerType;

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
