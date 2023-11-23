package com.example.rminder.controller;

import com.example.rminder.model.Action;
import com.example.rminder.model.Rule;
import com.example.rminder.model.RuleManager;
import com.example.rminder.model.Trigger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class RMinderController {
    private RuleManager ruleManager;
    @FXML
    private Label welcomeText;
    @FXML
    private Button createRuleButton;


    public RMinderController() {

    }

    @FXML
    protected void onClickCreateRuleButton() {
        // Esempio: Creazione di una nuova regola
        Rule newRule = new Rule("NewRule", null, null, true); // Per ora imposto su null Action e Trigger
        ruleManager.addRule(newRule);
    }

    /*
    @FXML
    protected void onClickModifyRuleButton() {
        // Esempio: Modifica della regola esistente
        Rule rule = new rule.modifyRule("ModifiedRule", new Trigger(), new Action(), true);
        // Aggiungi la tua logica qui, ad esempio, aggiorna la regola nella tua struttura dati
    }

    @FXML
    protected void onClickSwitchActiveRuleButton() {
        // Esempio: Cambio stato della regola esistente
        rule.modifyRule(rule.getName(), rule.getTrigger(), rule.getAction(), !rule.isActive());
        // Aggiungi la tua logica qui, ad esempio, cambia lo stato della regola nella tua struttura dati
    }

    @FXML
    protected void onClickDeleteRuleButton() {
        // Esempio: Eliminazione della regola esistente
        rule.deleteRule();
        // Aggiungi la tua logica qui, ad esempio, rimuovi la regola dalla tua struttura dati
    }

    @FXML
    protected void onMessageButtonClick() {
        // Esempio: Visualizzazione di un messaggio nel textLabel
        this.welcomeText.setText("Message Button Clicked del mio patatone bello!!!");
    }

     */
/*
    // Implementazione del caricamento della regola dal file
    @FXML
    protected void onClickLoadRuleButton() {
        try {
            rule.loadRule();
        } catch (IOException e) {
            e.printStackTrace();
            // Gestisci eventuali eccezioni di IO in modo appropriato per il tuo caso d'uso
        }
    }
    // Implementazione del salvataggio della regola su file
    @FXML
    protected void onClickSaveRuleButton() {
        rule.saveRule();
    }

 */


}
