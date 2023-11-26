package com.example.rminder.model;

import java.util.ArrayList;
import java.util.List;

public class RuleManager {
    private final List<Rule> ruleList;

    public RuleManager() {
        this.ruleList = new ArrayList<>();
    }


    // Metodo per aggiungere una regola alla lista
    public void subscribeRule(Rule rule) {
        ruleList.add(rule);
    }

    // Metodo per eliminare una regola dalla lista
    public void deleteRule(Rule rule) {
        ruleList.remove(rule);
    }


}



