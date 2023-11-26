package com.example.rminder.model;

import java.io.*;

public class Rule {
    private String name;
    private Trigger trigger;
    private Action action;
    private boolean status;

    // Costruttore
    public Rule(String name, Trigger trigger, Action action, boolean status) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.status = status;
    }

    public void setName(String n) {
        this.name = n;
    }
    private void setTrigger(Trigger trigger){
        this.trigger = trigger;
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

    // Metodo per verificare se la regola è attiva
    public boolean isActive() {
        return status;
    }

    // Metodo per modificare la regola
    public void modifyRule(String newName, Trigger newTrigger, Action newAction, boolean newStatus) {
        this.name = newName;
        this.trigger = newTrigger;
        this.action = newAction;
        this.status = newStatus;
    }
    // Metodo per eliminare la regola
    public void deleteRule() {
        // Implementazione di base: segna la regola come eliminata (cambia lo stato o rimuovila dalla struttura dati)
        this.status = false;
    }

    // Metodo per salvare la regola
    public void saveRule() {
        // Implementazione di base: salva la regola su un file di testo
        try (PrintWriter writer = new PrintWriter(new FileWriter("regola_" + name + ".txt"))) {
            // Scrivi i dettagli della regola nel file
            writer.println(name);
            writer.println(trigger.toString());
            writer.println(action.toString());
            writer.println(status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Metodo per caricare la regola
    public void loadRule() {

    }


}

