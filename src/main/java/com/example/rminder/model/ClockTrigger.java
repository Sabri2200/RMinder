package com.example.rminder.model;
import java.time.LocalTime;

public class ClockTrigger implements Trigger {
    private LocalTime time;

    public ClockTrigger(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public boolean verifyTrigger() {
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(time)) {
            System.out.println("ClockTrigger is active\n");
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "At" + time + " ";
    }

}