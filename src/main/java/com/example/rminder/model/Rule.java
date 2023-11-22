package com.example.rminder.model;

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
        // Implementazione della logica per eliminare la regola
        // Questo potrebbe includere la rimozione dalla struttura dati o il segnare la regola come eliminata, a seconda delle esigenze del tuo sistema.
    }

    // Metodo per salvare la regola
    public void saveRule() {
        // Implementazione della logica per salvare la regola
        // Questo potrebbe includere la scrittura della regola su un file o il salvataggio in un database, a seconda delle esigenze del tuo sistema.
    }

    // Metodo per caricare la regola
    public void loadRule() {
        // Implementazione della logica per caricare la regola
        // Questo potrebbe includere la lettura della regola da un file o il caricamento da un database, a seconda delle esigenze del tuo sistema.
    }

}

