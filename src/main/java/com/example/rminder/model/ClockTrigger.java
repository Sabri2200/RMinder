package com.example.rminder.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Implementazione di un trigger basato sull'orario di sistema.
 */
public class ClockTrigger implements Trigger {

    private LocalTime time;

    /**
     * Costruttore che imposta l'orario per il trigger.
     */
    public ClockTrigger(LocalTime time) {
        this.time = time;
    }

    /**
     * Ottiene l'orario attualmente impostato per il trigger.
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Imposta un nuovo orario per il trigger.

     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Verifica se il trigger è attivo in base all'orario di sistema corrente.

     */
    @Override
    public boolean verifyTrigger() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        String timeFormatted = currentTime.format(formatter);
        String timeFormattedNow = time.format(formatter);

        // Converti le stringhe formattate in oggetti LocalTime
        LocalTime formattedLocalTime = LocalTime.parse(timeFormattedNow, formatter);
        LocalTime targetLocalTime = LocalTime.parse(timeFormatted, formatter);

        // Verifica se formattedTime è maggiore di time
        if (formattedLocalTime.isAfter(targetLocalTime)) {
           //System.out.println("formattedTime è maggiore di time");
            return true;
        } else {
            //System.out.println("formattedTime non è maggiore di time");
            return false;
        }
    }

    /**
     * Restituisce una rappresentazione testuale del trigger.
     *
     * @return Una stringa che rappresenta il trigger.
     */
    @Override
    public String toString() {
        return "ClockTrigger";
    }


}
