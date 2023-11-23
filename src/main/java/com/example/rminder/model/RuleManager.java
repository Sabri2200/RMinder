package com.example.rminder.model;

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

    // Metodo per gestire il trigger delle regole
    /*
    public void handleTriggerRule() {
        // Implementazione della logica per gestire il trigger delle regole
        // Itera attraverso la lista delle regole e gestisci il trigger per ciascuna regola.
        for (Rule rule : ruleList) {
            if (rule.isActive() && rule.getTrigger().verifyTrigger()) {
                // La regola è attiva e il trigger è verificato, esegui l'azione associata alla regola.
                rule.getAction().performAction();
            }

     */
}



