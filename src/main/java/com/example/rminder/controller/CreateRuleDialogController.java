package com.example.rminder.controller;

import com.example.rminder.model.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.io.File;

public class CreateRuleDialogController implements Initializable {

    @FXML
    private TextField insertRuleTitleTextField;

    private RuleManager ruleManager = new RuleManager();
    @FXML
    private ComboBox<Trigger> selectTriggerComboBox = new ComboBox<>();
    @FXML
    private ComboBox<Action> selectActionComboBox = new ComboBox<>();
    private Stage stage;

    @FXML
    private Button fileBtn = new Button();

    @FXML
    private TextField hourField = new TextField();

    @FXML
    private TextField minuteField = new TextField();

    @FXML
    private TextField titleField = new TextField();

    @FXML
    private TextField messageField = new TextField();

    private String pathToFile = "";

    public CreateRuleDialogController() {
    }


    // Metodo per ricevere il riferimento alla finestra popup
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void fileSelector() {
    }

    public String action = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Trigger> triggerList = List.of(new ClockTrigger(null));
        ObservableList<Trigger> triggerObservableList = FXCollections.observableArrayList(triggerList);
        selectTriggerComboBox.setItems(triggerObservableList);
        selectTriggerComboBox.valueProperty().addListener(selectTriggerComboBoxChangeListener);

        //da implementare nel settaggio delle azioni
        List<Action> actionList = List.of(new AudioAction(""),new MessageAction("",""));
        ObservableList<Action> actionObservableList = FXCollections.observableArrayList(actionList);
        selectActionComboBox.setItems(actionObservableList);

        ChangeListener<Action> selectActionComboBoxChangeListener = (observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Qui puoi gestire l'elemento selezionato
                System.out.println("Elemento selezionato: " + newValue);
                if (newValue.toString() == "Message Action") {
                    action = "Message Action";
                    titleField.setVisible(true);
                    messageField.setVisible(true);
                    fileBtn.setVisible(false);
                } else if (newValue.toString() == "Audio Action") {
                    action = "Audio Action";
                    fileBtn.setVisible(true);
                    titleField.setVisible(false);
                    messageField.setVisible(false);
                } else {
                    fileBtn.setVisible(false);
                    titleField.setVisible(false);
                    messageField.setVisible(false);
                }
            }
        };
        selectActionComboBox.valueProperty().addListener(selectActionComboBoxChangeListener);

    }

    @FXML
    protected void onClickSaveAndConfirmRuleButton() {

        LocalTime lt = LocalTime.of(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText()));

        Trigger ct = new ClockTrigger(lt);
        Action a = null;

        if (action == "Message Action") {
            a = new MessageAction(titleField.getText(), messageField.getText());
        } else if (action == "Audio Action") {
            a = new AudioAction(pathToFile);
        } else {
            System.out.println("null");
        }

        // Rule rule = new Rule(insertRuleTitleTextField.getText(), selectTriggerComboBox.getValue(), selectActionComboBox.getValue(), true);
        Rule rule = new Rule(insertRuleTitleTextField.getText(), ct, a, true);

        new MessageAction("Regola creata", "Regola creata con successo").executeAction();

        ruleManager.subscribeRule(rule);
        Stage primaryStage = (Stage) stage.getOwner();

        Scene mainScene = primaryStage.getScene();
        FXMLLoader loader = (FXMLLoader) mainScene.getUserData();
        MainWindowController mainWindowController = loader.getController();

        mainWindowController.addRuleToTable(rule);
        stage.close();
    }


    ChangeListener<Trigger> selectTriggerComboBoxChangeListener = (observableValue, oldString, newString) -> {

    };

    ChangeListener<Action> selectActionComboBoxChangeListener = (observableValue, oldString, newString) -> {

    };



}
