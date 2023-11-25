package com.example.rminder.controller;

import com.example.rminder.model.Action;
import com.example.rminder.model.Rule;
import com.example.rminder.model.RuleManager;
import com.example.rminder.model.Trigger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Duration;

public class RMinderController implements Initializable {
    private RuleManager ruleManager;
    @FXML
    private Label welcomeText;
    @FXML
    private Button createRuleButton;
    @FXML
    private ObservableList<Rule> ruleList;

    @FXML
    private TableView<Rule> ruleTable;
    @FXML
    private TableColumn<Rule, String> ruleName;
    @FXML
    private TableColumn<Rule, String> ruleActivation;
    @FXML
    private TableColumn<Rule, String> ruleState;

    private ObservableList<Rule> list;

    public RMinderController() {

    }

    @FXML
    protected void onClickCreateRuleButton() {
        // Esempio: Creazione di una nuova regola
        Rule newRule = new Rule("NewRule", null, null, true); // Per ora imposto su null Action e Trigger
        // ruleManager.addRule(newRule);
        list.add(newRule);
    }

    private Service<Void> backgroundService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list = FXCollections.observableArrayList();

        ruleName.setCellValueFactory(new PropertyValueFactory("name"));
        ruleActivation.setCellValueFactory(cellData -> {
            Rule rule = cellData.getValue();
            Trigger trigger = rule.getTrigger();
            if (trigger != null) {
                return new ReadOnlyObjectWrapper<>(trigger.toString() + rule.getAction().toString());
            } else {
                return new ReadOnlyObjectWrapper<>(null);
            }
        });
        ruleState.setCellValueFactory(cellData -> {
            Rule rule = cellData.getValue();
                return new ReadOnlyObjectWrapper<>(rule.isActive() ? "active" : "not active");
        });

        ruleTable.setItems(list);

        backgroundService = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        // Your repeated action goes here
                        System.out.println("Action performed every 2 seconds");
                        return null;
                    }
                };
            }
        };

        // Set up the timeline to execute the service every 2 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            if (!backgroundService.isRunning()) {
                backgroundService.restart();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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
