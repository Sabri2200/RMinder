package gruppo13.seproject;

import gruppo13.seproject.Service.BackgroundService.BackgroundService;
import gruppo13.seproject.FileManager.FileManager;
import gruppo13.seproject.Service.GUIRuleList.GUIRuleList;
import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionFactory;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.type.*;
import gruppo13.seproject.essential.rule.*;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.TriggerFactory;
import gruppo13.seproject.essential.trigger.TriggerType;
import gruppo13.seproject.essential.trigger.type.ClockTrigger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private TextField ruleNameField;
    @FXML
    private ComboBox<TriggerType> triggerSelector;
    private HBox clockTriggerHBox;
    @FXML
    private ComboBox<ActionType> actionSelector;
    @FXML
    private VBox messageActionVBox;
    @FXML
    private TextField titleAlertField;
    @FXML
    private TextField messageAlertField;
    @FXML
    private VBox fileSelectorVBox;
    @FXML
    private Button saveRuleBtn;
    @FXML
    private TextField hourField;
    @FXML
    private TextField minuteField;
    @FXML
    private Label fileChosen;
    @FXML
    private Button ruleStateBtn;
    @FXML
    private VBox clockTriggerVBox;
    @FXML
    private TableView<Action> actionsTable;
    @FXML
    private TableColumn<Action, String> actionTypeClm;
    @FXML
    private TableColumn<Action, String> actionTypeClm1;
    @FXML
    private TableColumn<Action, String> paramsClm;
    @FXML
    private TableColumn<Action, String> paramsClm1;
    @FXML
    private Label ruleNameSummary;
    @FXML
    private Label triggerLbl;
    @FXML
    private TableView<Action> actionsTableSummary;
    @FXML
    private MenuItem editBtn;
    @FXML
    private TableView<Rule> tableView;
    @FXML
    private TableColumn<Rule, String> nameClm;
    @FXML
    private TableColumn<Rule, String> triggerClm;
    @FXML
    private TableColumn<Rule, String> actionClm;
    @FXML
    private TableColumn<Rule, String> stateClm;

    private ObservableList<Action> actionsList;

    private RuleManager ruleManager;
    private GUIRuleList guiRuleList;
    private BackgroundService backgroundService;

    private Rule editingRule = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // initializing services
        ruleManager = RuleManager.getInstance();

        guiRuleList = new GUIRuleList();
        ruleManager.registerObserver(guiRuleList);

        backgroundService = new BackgroundService();
        backgroundService.startService();

        // prova
        List<Action> actions = new ArrayList<>();

        actions.add(new DialogBoxAction("dd", "dd", "dde"));
        Trigger trigger = new ClockTrigger(LocalTime.of(00, 00));

        ruleManager.addRule(new Rule("name", actions, trigger, State.ACTIVE));

        // initializing tableView
        initializeTableview();

        // initializing rule Creation Paradigm
        initializeRuleCreationParadigm();
    }

    public void ruleStateChange(ActionEvent actionEvent) {
        State oldState = State.valueOf(ruleStateBtn.getText());

        State newState = switch (oldState) {
            case ACTIVE -> State.NOTACTIVE;
            //case ALWAYSACTIVE -> State.NOTACTIVE;
            case NOTACTIVE -> State.ACTIVE;
        };

        ruleStateBtn.setText(newState.name());
    }

    public void fileSelector(ActionEvent actionEvent) {
        Stage fileChooserDialog = new Stage();
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showOpenDialog(fileChooserDialog);

        ActionType type = (ActionType) actionSelector.getSelectionModel().getSelectedItem();

        if (Objects.requireNonNull(type) == ActionType.MP3PLAYER) {
            if (FileManager.verifyAudioFile(file)) {
                fileChosen.setText(file.getAbsolutePath());
            } else {
                // createDialogBox("Internal Error", "This file is not supported", "Try again");
            }
        }//createDialogBox("Internal Error", "This file is not supported", "Try again");
    }

    public void addActionToRule(ActionEvent actionEvent) {
        ActionType type = (ActionType) actionSelector.getSelectionModel().getSelectedItem();
        List<String> params = new ArrayList<>();

        switch (type) {
            case DIALOGBOX:
                params.add(titleAlertField.getText());
                params.add(messageAlertField.getText());
                params.add("");
            case MP3PLAYER:
                params.add(fileChosen.getText());
        }

        Map.Entry<ActionType, List<String>> action = Map.entry(type, params);

        actionsList.add(ActionFactory.createAction(action));

        actionsTable.refresh();
        actionsTableSummary.refresh();
    }

    public void saveRule(ActionEvent actionEvent) {
        String ruleName = ruleNameField.getText();
        TriggerType triggerType = (TriggerType) triggerSelector.getSelectionModel().getSelectedItem();
        List<String> triggerParams = triggerParams(triggerType);

        if (!actionsList.isEmpty() && !triggerType.name().isEmpty() && triggerParams != null) {
            State state = State.valueOf(ruleStateBtn.getText());

            Map.Entry<TriggerType, List<String>> t = Map.entry(triggerType, triggerParams);

            Trigger trigger = TriggerFactory.createTrigger(t);

            Rule rule = RuleFactory.createRule(ruleName, actionsList, trigger, state);

            if (editingRule != null) {
                ruleManager.removeRule(editingRule);
                editingRule = null;
            }

            ruleManager.addRule(rule);
            actionsList.removeAll();
        }

        resetRule(null);

        actionsTable.refresh();
        actionsTableSummary.refresh();

    }

    private List<String> triggerParams(TriggerType type) {
        List<String> params = new ArrayList<>();

        switch (type) {
            case CLOCKTRIGGER:
                params.add(hourField.getText() + ":" + minuteField.getText());
                return params;
            default:
                return null;
        }
    }

    public void makeRuleSummary(Event event) {
        String ruleName = ruleNameField.getText();
        ruleNameSummary.setText(ruleName.isEmpty() ? "Rule name" : ruleName);

        Object trigger = triggerSelector.getSelectionModel().getSelectedItem();

        if (trigger != null) {
            StringBuffer triggerSelected = new StringBuffer();

            triggerSelected.append(triggerSelector.getSelectionModel().getSelectedItem().toString()).append(" ");
            triggerSelected.append(hourField.getText()).append(":");
            triggerSelected.append(minuteField.getText());

            triggerLbl.setText(triggerSelected.toString());
        }
    }

    public void resetRule(ActionEvent actionEvent) {
        ruleNameField.setText("");
        ruleNameSummary.setText("");

        triggerSelector.getSelectionModel().clearSelection();

        hourField.setText("");
        minuteField.setText("");

        actionsList.removeAll();
        actionsTable.refresh();
        actionsTableSummary.refresh();
    }

    public void editRuleAction(ActionEvent actionEvent) {
        editingRule = tableView.getSelectionModel().getSelectedItem();

        ruleNameField.setText(editingRule.getName());

        Trigger trigger = editingRule.getTrigger();
        TriggerType triggerType = trigger.getType();

        triggerSelector.setValue(triggerType);

        if (Objects.requireNonNull(triggerType) == TriggerType.CLOCKTRIGGER) {
            LocalTime time = ((ClockTrigger) trigger).getTime();
            hourField.setText(String.valueOf(time.getHour()));
            minuteField.setText(String.valueOf(time.getMinute()));
        }

        //actionsList.removeAll();

        actionsList.addAll(editingRule.getActions());

        actionsTable.refresh();
        actionsTableSummary.refresh();
    }

    public void removeRulesAction(ActionEvent actionEvent) {
        List<Rule> rules = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());

        if (!rules.isEmpty()) {
            for (Rule rule : rules) {
                if (ruleManager.getRules().contains(rule)) {
                    ruleManager.removeRule(rule);
                }
            }
        } else {
            // Handle the case where no rules are selected or the selection is invalid
        }
    }

    public void saveRulesToFile(ActionEvent actionEvent) {
        Stage fileChooserDialog = new Stage();
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showOpenDialog(fileChooserDialog);

        List<Rule> rules = tableView.getSelectionModel().getSelectedItems();

        FileManager.saveRulesToFile(rules, file);
    }

    public void loadRulesFromFile(ActionEvent actionEvent) {
        Stage fileChooserDialog = new Stage();
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showOpenDialog(fileChooserDialog);

        for (Rule rule : FileManager.loadRulesFromFile(file)) {
            ruleManager.addRule(rule);
        }

        //update();
    }

    public void turnRule(ActionEvent actionEvent) {
        List<Rule> selectedRules = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());

        if (!selectedRules.isEmpty()) {
            for (Rule rule : selectedRules) {
                State oldState = rule.getState();
                State newState = oldState.equals(State.ACTIVE) ? State.NOTACTIVE : State.ACTIVE;
                if (ruleManager.getRules().contains(rule)) {
                    ruleManager.setState(rule, newState);
                }
            }
        } else {
            System.out.println("Nessuna regola selezionata");
        }
    }

    private void initializeTableview() {
        nameClm.setCellValueFactory(cellData -> {
            Rule rule = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(rule.getName());
        });

        triggerClm.setCellValueFactory(cellData -> {
            Rule rule = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(rule.getTrigger().toString());
        });

        actionClm.setCellValueFactory(cellData -> {
            Rule rule = cellData.getValue();
            StringBuffer actions = new StringBuffer();

            for (Action action : rule.getActions()) {
                if (action != null) {
                    actions.append(action.toString()).append(" - ");
                }
            }

            return new ReadOnlyObjectWrapper<>(actions).asString();
        });

        stateClm.setCellValueFactory(cellData -> {
            Rule rule = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(rule.getState().name());
        });

        tableView.setItems(guiRuleList.getList());

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends Rule> change) -> {
            boolean multipleSelection = !tableView.getSelectionModel().getSelectedItems().isEmpty();
            editBtn.setDisable(multipleSelection);
        });
    }

    private void initializeRuleCreationParadigm() {
        List<TriggerType> triggerList = List.of(TriggerType.values());
        ObservableList<TriggerType> triggerObservableList = FXCollections.observableArrayList(triggerList);
        triggerSelector.setItems(triggerObservableList);

        ChangeListener<TriggerType> selectTriggerChangeListener = (observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals(TriggerType.CLOCKTRIGGER)) {
                    clockTriggerVBox.setVisible(true);
                } else {
                    clockTriggerHBox.setVisible(false);
                }
            }
        };
        triggerSelector.valueProperty().addListener(selectTriggerChangeListener);

        List<ActionType> actionList = List.of(ActionType.values());
        ObservableList<ActionType> actionObservableList = FXCollections.observableArrayList(actionList);
        actionSelector.setItems(actionObservableList);

        ChangeListener<ActionType> selectActionChangeListener = (observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals(ActionType.DIALOGBOX)) {
                    messageActionVBox.setVisible(true);
                    fileSelectorVBox.setVisible(false);
                } else if (newValue.equals(ActionType.MP3PLAYER)) {
                    fileSelectorVBox.setVisible(true);
                    messageActionVBox.setVisible(false);
                } else {
                    messageActionVBox.setVisible(false);
                    fileSelectorVBox.setVisible(false);
                }
            }
        };

        actionSelector.valueProperty().addListener(selectActionChangeListener);

        actionsList = FXCollections.observableArrayList();

        actionTypeClm.setCellValueFactory(cellData -> {
                    Action action = cellData.getValue();
                    return new ReadOnlyObjectWrapper<>(action.getType().name());
                }
        );

        actionTypeClm1.setCellValueFactory(cellData -> {
                    Action action = cellData.getValue();
                    return new ReadOnlyObjectWrapper<>(action.getType().name());
                }
        );

        paramsClm.setCellValueFactory(cellData -> {
                    Action action = cellData.getValue();
                    String params = action.toString().replace(action.getType().name(), "");

                    return new ReadOnlyObjectWrapper<>(params);
                }
        );

        paramsClm1.setCellValueFactory(cellData -> {
                    Action action = cellData.getValue();
                    String params = action.toString().replace(action.getType().name(), "");

                    return new ReadOnlyObjectWrapper<>(params);
                }
        );

        actionsTable.setItems(actionsList);
        actionsTableSummary.setItems(actionsList);

        //saveRuleBtn.disableProperty().bind(ruleNameField.textProperty().isEmpty().or(hourField.textProperty().isEmpty()).or(minuteField.textProperty().isEmpty()).or(fileChosen.textProperty().isEmpty()));
        saveRuleBtn.disableProperty().bind(ruleNameSummary.textProperty().isEmpty().or(triggerLbl.textProperty().isEmpty().or(hourField.textProperty().isEmpty()).or(minuteField.textProperty().isEmpty()).or(Bindings.isEmpty(actionsList))));

    }

    /* private void startRuleService() {
        Timer timer = new Timer();
        RulePerformer rulePerformer = new RulePerformer(ruleManager);
        rulePerformer.registerObserver(new GUIExecutor());

        RuleService task = new RuleService(rulePerformer);

        timer.scheduleAtFixedRate(task, 0, 2000);
    } */

    /*@Override
    public void update() {
        tableView.getItems().setAll(ruleManager.getRules());
        tableView.refresh();
    }*/

    public void removeAction(ActionEvent actionEvent) {
        Set<Action> actions = new HashSet<>(actionsTable.getSelectionModel().getSelectedItems());
        actions.addAll(actionsTableSummary.getSelectionModel().getSelectedItems());

        actionsList.removeAll(actions);
        actionsTable.refresh();
    }
}