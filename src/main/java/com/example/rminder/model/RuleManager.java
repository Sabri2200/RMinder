package com.example.rminder.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RuleManager {
    private final List<Rule> ruleList;

    // Costruttore
    public RuleManager() {
        this.ruleList = new ArrayList<>();
    }

    // Metodo per aggiungere una regola alla lista
    // Metodo per aggiungere una regola alla lista
    public void addRule(Rule rule) {
        ruleList.add(rule);
    }

    // Metodo per eliminare una regola dalla lista
    public void deleteRule(Rule rule) {
        ruleList.remove(rule);
    }
    public Trigger getTriggerType(String selectedTrigger) {
        System.out.println(selectedTrigger);
        if (selectedTrigger == null) {
            throw new IllegalArgumentException("selectedTrigger non può essere nullo");
        }

        switch (selectedTrigger) {
            case "Clock Trigger":
                System.out.println("Returning ClockTrigger");
                return new ClockTrigger(null);
            case "Trigger2":
                System.out.println("Hai selezionato il secondo elemento.\n");
                return null;
            default:
                throw new IllegalArgumentException("Tipo di trigger non gestito: " + selectedTrigger);
        }
    }

/*
    public Action getActionType(String selectedAction) {
        if (selectedAction == null) {
            throw new IllegalArgumentException("selectedAction non può essere nullo");
        }

        switch (selectedAction) {
            case "AudioAction":
                System.out.println("Returning ClockTrigger");
                return new AudioAction();
            case "Trigger2":
                System.out.println("Hai selezionato il secondo elemento.\n");
                return null;
            default:
                throw new IllegalArgumentException("Tipo di trigger non gestito: " + selectedAction);
        }
    }

 */
}



