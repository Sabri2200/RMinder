package com.example.rminder.model;

import java.io.*;

public class Rule {
    private String name;
    private Trigger trigger;
    private Action action;
    private boolean state;

    public Rule(String name, Trigger trigger, Action action, boolean state) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.state = state;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public Action getAction() {
        return action;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public boolean getState() {
        return state;
    }

    public void modifyRule(String newName, Trigger newTrigger, Action newAction, boolean newState) {
        this.name = newName;
        this.trigger = newTrigger;
        this.action = newAction;
        this.state = newState;
    }
    // Metodo per eliminare la regola
    public void deleteRule() {
        // Implementazione di base: segna la regola come eliminata (cambia lo stato o rimuovila dalla struttura dati)
        this.state = false;
    }

    // Metodo per salvare la regola
    public void saveRule() {
        // Implementazione di base: salva la regola su un file di testo
        try (PrintWriter writer = new PrintWriter(new FileWriter("regola_" + name + ".cvc"))) {
            // Scrivi i dettagli della regola nel file
            writer.println(name);
            writer.println(trigger.toString());
            writer.println(action.toString());
            writer.println(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Metodo per caricare la regola
    public void loadRule() {

    }




}

