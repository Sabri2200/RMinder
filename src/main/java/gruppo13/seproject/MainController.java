package gruppo13.seproject;

import gruppo13.seproject.FileManager.FileManager;
import gruppo13.seproject.GUIExcecutor.DialogBoxExecutor;
import gruppo13.seproject.GUIExcecutor.GUIExecutor;
import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionFactory;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.actionType.AudioAction;
import gruppo13.seproject.essential.action.actionType.DialogBoxAction;
import gruppo13.seproject.essential.rule.*;
import gruppo13.seproject.essential.rule.ListObserver.ListObserver;
import gruppo13.seproject.essential.rule.ListObserver.ListSubject;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.TriggerFactory;
import gruppo13.seproject.essential.trigger.TriggerType;
import gruppo13.seproject.essential.trigger.triggerType.ClockTrigger;
import javafx.application.Platform;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainController implements Initializable, ListObserver {

    private Stage stage;

    @FXML
    private TextField ruleNameField;
    @FXML
    private ComboBox triggerSelector;
    private HBox clockTriggerHBox;
    @FXML
    private ComboBox actionSelector;
    @FXML
    private VBox messageActionVBox;
    @FXML
    private TextField titleAlertField;
    @FXML
    private TextField messageAlertField;
    @FXML
    private VBox fileSelectorVBox;
    @FXML
    private Button fileSelectorBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private Button saveRuleBtn;
    @FXML
    private TextField hourField;
    @FXML
    private TextField minuteField;
    @FXML
    private Label fileChosen;

    @FXML
    private ContextMenu contextMenu;
    @FXML
    private Button ruleStateBtn;
    @FXML
    private VBox clockTriggerVBox;
    @FXML
    private TableView actionsTable;
    @FXML
    private TableColumn<Action, String> actionTypeClm;
    @FXML
    private TableColumn<Action, String> actionTypeClm1;
    @FXML
    private TableColumn<Action, String> paramsClm;
    @FXML
    private TableColumn<Action, String> paramsClm1;
    @FXML
    private Button addActionBtn;
    @FXML
    private Label ruleNameSummary;
    @FXML
    private Label triggerLbl;
    @FXML
    private TableView actionsTableSummary;
    private AnchorPane summaryAnchorPane;
    @FXML
    private MenuItem editBtn;
    @FXML
    private MenuItem removeBtn;
    @FXML
    private MenuItem saveToFileBtn;
    @FXML
    private MenuItem loadFromFileBtn;
    @FXML
    private MenuItem turnBtn;

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
    private GUIExecutor guiExecutor;

    //private ActionManager actionManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ruleManager = new RuleManager();
        ruleManager.registerObserver(this);
        guiExecutor = new GUIExecutor();

        List<Action> actions = new ArrayList<>();

        //actions.add(new DialogBoxAction("title", "content", "message"));
        actions.add(new AudioAction(new File("/Users/michelecoscarelli/Downloads/gg.mp3")));
        Trigger trigger = new ClockTrigger(LocalTime.of(00, 00));

        ruleManager.addRule(new Rule("name", actions, trigger, State.ACTIVE));

        /*System.out.println(RuleJson.rulesToJson(ruleManager.getRules()));
        ruleManager.getRules().addAll(RuleJson.jsonToRules(RuleJson.rulesToJson(ruleManager.getRules())));
        */

        // initializing tableView
        initilizeTableview();

        // initializing rule Creation Paradigm
        initializeRuleCreationParadigm();

        startRuleService();
    }



    public void ruleStateChange(ActionEvent actionEvent) {
        State oldState = State.valueOf(ruleStateBtn.getText());
        State newState = oldState == State.ACTIVE ? State.NOTACTIVE : State.ACTIVE;

        ruleStateBtn.setText(newState.name());
    }

    public void fileSelector(ActionEvent actionEvent) {
        Stage fileChooserDialog = new Stage();
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showOpenDialog(fileChooserDialog);

        ActionType type = (ActionType) actionSelector.getSelectionModel().getSelectedItem();
        System.out.println(type.name());

        switch (type) {
            case MP3PLAYER:
                if (FileManager.verifyAudioFile(file)) {
                    fileChosen.setText(file.getAbsolutePath());
                } else {
                    createDialogBox("Internal Error", "This file is not supported", "Try again");
                }
            default:
                //createDialogBox("Internal Error", "This file is not supported", "Try again");
        }
    }

    public void addActionToRule(ActionEvent actionEvent) {
        ActionType type = (ActionType) actionSelector.getSelectionModel().getSelectedItem();
        List<String> params = new ArrayList<>();

        switch (type) {
            case DIALOGBOX:
                params.add(titleAlertField.getText());
                params.add(messageAlertField.getText());
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

        if (actionsList.isEmpty() || triggerType.name().isEmpty() || triggerParams == null) {
            System.out.println("ddvfjuvregbhw");
        } else {
            State state = State.valueOf(ruleStateBtn.getText());

            Map.Entry<TriggerType, List<String>> t = Map.entry(triggerType, triggerParams);

            Trigger trigger = TriggerFactory.createTrigger(t);

            Rule rule = RuleFactory.createRule(ruleName, actionsList, trigger, state);

            ruleManager.addRule(rule);
        }

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
        Rule rule = tableView.getSelectionModel().getSelectedItem();

        ruleNameField.setText(rule.getName());

        Trigger trigger = rule.getTrigger();
        TriggerType triggerType = trigger.getType();

        triggerSelector.setValue(triggerType);

        switch (triggerType) {
            case CLOCKTRIGGER:
                LocalTime time = ((ClockTrigger) trigger).getTime();
                hourField.setText(String.valueOf(time.getHour()));
                minuteField.setText(String.valueOf(time.getMinute()));
            default:
                System.out.println("error");
        }

        actionsList.removeAll();
        actionsList.addAll(rule.getActions());

        actionsTable.refresh();
        actionsTableSummary.refresh();
    }

    public void removeRulesAction(ActionEvent actionEvent) {
        List<Rule> rules = tableView.getSelectionModel().getSelectedItems();

        for (Rule rule : rules) {
            ruleManager.removeRule(rule);
        }
    }

    public void saveRulesToFile(ActionEvent actionEvent) {
    }

    public void loadRulesFromFile(ActionEvent actionEvent) {
    }

    public void turnRule(ActionEvent actionEvent) {
        List<Rule> rules = tableView.getSelectionModel().getSelectedItems();

        for (Rule rule : rules) {
            State oldState = rule.getState();
            State newState = oldState.equals(State.ACTIVE) ? State.NOTACTIVE : State.ACTIVE;
            ruleManager.setState(rule, newState);
        }
    }

    private void initilizeTableview() {
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

        ObservableList<Rule> rules = FXCollections.observableArrayList(ruleManager.getRules());

        tableView.setItems(rules);

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends Rule> change) -> {
            boolean multipleSelection = tableView.getSelectionModel().getSelectedItems().size() > 1;
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

    private void createDialogBox(String title, String content, String message) {
        Action action = new DialogBoxAction(title, content, message);
        guiExecutor.execute(action);
    }

    private void startRuleService() {
        Timer timer = new Timer();
        RulePerformer rulePerformer = new RulePerformer(ruleManager);
        rulePerformer.registerObserver(new GUIExecutor());

        RuleService task = new RuleService(rulePerformer);

        timer.scheduleAtFixedRate(task, 0, 2000);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(this::closeApplication);
    }

    private void closeApplication(WindowEvent windowEvent) {
        Platform.exit();
    }

    @Override
    public void update() {
        tableView.getItems().setAll(ruleManager.getRules());
        tableView.refresh();
        System.out.println("up");
    }
}