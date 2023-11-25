package com.example.rminder.model;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockTriggerTest {



    //verify Trigger Should Return True WhenCurrent Time Is After Trigger Time
    @Test
    public void verifyTriggerTest() {

        LocalTime triggerTime = LocalTime.now().minusMinutes(1); // set trigger time one minute ago
        ClockTrigger clockTrigger = new ClockTrigger(triggerTime);

        // Act
        boolean result = clockTrigger.verifyTrigger();

        // Assert
        System.out.println(result);
    }


    @Test
    public void testToString() {
        // Creare un'istanza di ClockTrigger con un orario specifico
        LocalTime triggerTime = LocalTime.parse("11:00");
        ClockTrigger clockTrigger = new ClockTrigger(triggerTime);

        // Verificare la rappresentazione testuale del trigger
        assertEquals("At 12:00 ", clockTrigger.toString());
    }
}
