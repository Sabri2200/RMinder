package com.example.rminder.controller;

import com.example.rminder.model.Action;
import com.example.rminder.model.Rule;
import com.example.rminder.model.RuleManager;
import com.example.rminder.model.Trigger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CreateRuleDialogController implements Initializable {
    @FXML
    private TextField insertRuleTitleTextField;

    private RuleManager ruleManager = new RuleManager();
    private Rule rule = new Rule("",null,null,false);
    @FXML
    private ComboBox<String> selectTriggerComboBox = new ComboBox<>();
    @FXML
    private ComboBox<String> selectActionComboBox = new ComboBox<>();

    public CreateRuleDialogController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> triggerList = Arrays.asList("Clock Trigger", "Trigger2");
        ObservableList<String> triggerObservableList = FXCollections.observableArrayList(triggerList);
        selectTriggerComboBox.setItems(triggerObservableList);
        selectTriggerComboBox.valueProperty().addListener(selectTriggerComboBoxChangeListener);

        insertRuleTitleTextField.setOnAction(event -> onInsertRuleTitleTextField());
/*
        List<String> actionList = Arrays.asList("Message Action", "Action2");
        ObservableList<String> actionObservableList = FXCollections.observableArrayList(actionList);
        selectActionComboBox.setItems(actionObservableList);
        selectActionComboBox.valueProperty().addListener(selectActionComboBoxChangeListener);

 */
    }

    @FXML
    protected void onClickSaveAndConfirmRuleButton() {
       ruleManager.addRule(rule);
        System.out.println(rule);
    }
    @FXML
    protected void onInsertRuleTitleTextField(){
        if (insertRuleTitleTextField != null) {
            rule.setName(insertRuleTitleTextField.getText());
            System.out.println(rule.getName());
        }
    }
    ChangeListener<String> selectTriggerComboBoxChangeListener = (observableValue, oldString, newString) -> {
        if (newString != null) {
            Trigger trigger = ruleManager.getTriggerType(newString);
            System.out.println("Tipo di trigger per " + newString + ": " + trigger);
        } else {
            System.out.println("Valore selezionato è nullo.");
        }

    };

    /*
    ChangeListener<String> selectActionComboBoxChangeListener = (observableValue, oldString, newString) -> {
        if (newString != null) {
            Action action = ruleManager.getActionType(newString);
            System.out.println("Tipo di action per " + newString + ": " + action);
        } else {
            System.out.println("Valore selezionato è nullo.");
        }

    };

     */







}
