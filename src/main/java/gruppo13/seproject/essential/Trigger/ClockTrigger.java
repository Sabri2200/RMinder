package gruppo13.seproject.essential.Trigger;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockTrigger extends Trigger {
    LocalTime time;

    public ClockTrigger(TriggerType type, LocalTime time) {
        super(TriggerType.ClockTrigger);
        this.time = time;
    }

    @Override
    public Boolean verify() {

        LocalTime now = LocalTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String nowFormatted = now.format(formatter);

        if (now.equals(time)) {
            System.out.println("Le ore sono uguali.");
            return true;
        } else if (now.isBefore(time)) {
            System.out.println("L'ora attuale è precedente a 'time'.");
            return false;
        } else {
            System.out.println("L'ora attuale è successiva a 'time'.");
            return true;
        }
    }

    @Override
    public String toString() {
        return type.name() + " " + this.time;
    }

    public LocalTime getTime() {
        return time;
    }
}
