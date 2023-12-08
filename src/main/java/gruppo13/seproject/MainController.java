package gruppo13.seproject;

import gruppo13.seproject.file_manager.FileManager;
import gruppo13.seproject.service.BackgroundService;
import gruppo13.seproject.service.GUIhandler.GUIRuleList;
import gruppo13.seproject.essential.request_handler.RequestFactory;
import gruppo13.seproject.essential.request_handler.RequestPublisher;
import gruppo13.seproject.essential.Status;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionFactory;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.rule.*;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.TriggerFactory;
import gruppo13.seproject.essential.trigger.TriggerType;
import gruppo13.seproject.essential.trigger.type.ClockTrigger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;

public class MainController implements Initializable {

    public MenuItem removeActionBtn;
    public Button addActionBtn;
    public VBox audioFileSelectorVBox;
    public Button audioFileSelectorBtn;
    public Label audioFileChosenLbl;
    public VBox moveFileSelectorVBox;
    public Button moveFileSelectorBtn;
    public Button moveFileSelectorBtn1;
    public Label moveFileChosenLbl;
    public Label moveFileChosenLbl1;
    public Button deleteFileSelectorBtn;
    public VBox deleteFileSelectorVBox;
    public Label deleteFileChosenLbl;
    public Button copyFileSelectorBtn;
    public Label copyFileChosenLbl;
    public VBox copyFileSelectorVBox;
    public Button copyFileSelectorBtn1;
    public Label copyFileChosenLbl1;
    public VBox modifyTextFileSelectorVBox;
    public Button modifyFileSelectorBtn;
    public TextField modifyFileSelectorTxtFld;
    public Label modifyFileChosenLbl;
    public MenuItem removeActionSummaryBtn;
    public Button resetBtn;
    public ContextMenu contextMenu;
    public MenuItem removeBtn;
    public MenuItem saveToFileBtn;
    public MenuItem loadFromFileBtn;
    public MenuItem turnBtn;
    public CheckBox activationCheckBox;
    public TextField minuteTextField;
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
    public TextField headerAlertField;
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
    private Rule editingRule = null;
    private FileManager fileManager;
    private GUIRuleList guiRuleList;
    private RequestPublisher requestPublisher;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // initializing services

        ruleManager = RuleManager.getInstance();
        // has a list of Rule, can add, remove and change a state of a rule.

        fileManager = FileManager.getInstance();
        // implements file methods, such as writing on a file or reading from a file.
        // Moreover, it can load or save rules.

        guiRuleList = GUIRuleList.getInstance();
        // is responsible for updating the tableview of rules by using an instance of RuleManager.

        requestPublisher = RequestPublisher.getInstance();
        // An Exception, a notice of an Execution or a notice of the ruleList update is published through this object.
        // The RequestPublisher sends the message (a Request) to its handlers.

        BackgroundService backgroundService = new BackgroundService();
        backgroundService.startService();
        // starts the background tasks: RuleSaver, RuleService and sets to RequestPublisher its handlers.

        // initializing tableView
        initializeTableview();

        // initializing rule Creation Paradigm
        initializeRuleCreationParadigm();
    }

    // During a rule creation, this method can change the text of ruleStateBtn, from Active to INACTIVE or viceversa.
    public void ruleStateChange(ActionEvent actionEvent) {
        // Taking the actual state.
        Status oldStatus = Status.valueOf(ruleStateBtn.getText());

        // Changing the state due the actual state.
        Status newStatus = switch (oldStatus) {
            case ACTIVE -> Status.INACTIVE;
            //case ALWAYSACTIVE -> State.NOTACTIVE;
            case INACTIVE -> Status.ACTIVE;
        };

        // Changing the state
        ruleStateBtn.setText(newStatus.name());
    }

    public void pathSelector(ActionEvent actionEvent) {
        Stage pathChooserDialog = new Stage();

        DirectoryChooser pathChooser = new DirectoryChooser();
        pathChooser.setTitle("Select a directory");

        File selectedDirectory = pathChooser.showDialog(pathChooserDialog);

        ActionType type = actionSelector.getSelectionModel().getSelectedItem();

        if (selectedDirectory != null) {
            if (type.equals(ActionType.COPYFILE)) {
                copyFileChosenLbl1.setText(selectedDirectory.getAbsolutePath());
            } else if (type.equals(ActionType.MOVEFILE)) {
                moveFileChosenLbl1.setText(selectedDirectory.getAbsolutePath());
            }
        } else {
            requestPublisher.publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in Selecting Directory. Directory unsupported or not found. Please, try again")));
        }
    }

    // The method show a dialog window from which the user can select a file.
    // In the audio file selection, it verifies through FileManager the file can be used for audio execution.
    public void fileSelector(ActionEvent actionEvent) {
        // Creating a dialog window.
        Stage fileChooserDialog = new Stage();
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showOpenDialog(fileChooserDialog);

        // Taking the type
        ActionType type = actionSelector.getSelectionModel().getSelectedItem();

        // Switching due the action type and verifying the file.
        if (type.equals(ActionType.MP3PLAYER)) {
            // verifies the file
            if (fileManager.verifyAudioFile(file)) {
                audioFileChosenLbl.setText(file.getAbsolutePath());
            } else {
                // The file cannot be supported for the action type selected and an exception request is sent to RequestPublisher.
                requestPublisher.publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in Selecting file. File unsupported or not found. Please, try again")));
            }
        } else if (type.equals(ActionType.DELETEFILE)) {
            // verifies the file
            if (fileManager.verifyFile(file)) {
                deleteFileChosenLbl.setText(file.getAbsolutePath());
            } else {
                // The file cannot be supported for the action type selected and an exception request is sent to RequestPublisher.
                requestPublisher.publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in Selecting file. File unsupported or not found. Please, try again")));
            }
        } else if (type.equals(ActionType.MOVEFILE)) {
            // verifies the file
            if (fileManager.verifyFile(file)) {
                moveFileChosenLbl.setText(file.getAbsolutePath());
            } else {
                // The file cannot be supported for the action type selected and an exception request is sent to RequestPublisher.
                requestPublisher.publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in Selecting file. File unsupported or not found. Please, try again")));
            }
        } else if (type.equals(ActionType.COPYFILE)) {
            // verifies the file
            if (fileManager.verifyFile(file)) {
                copyFileChosenLbl.setText(file.getAbsolutePath());
            } else {
                // The file cannot be supported for the action type selected and an exception request is sent to RequestPublisher.
                requestPublisher.publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in Selecting file. File unsupported or not found. Please, try again")));
            }
        } else if (type.equals(ActionType.MODIFYTEXTFILE)) {
            // verifies the file
            if (fileManager.verifyFile(file)) {
                modifyFileChosenLbl.setText(file.getAbsolutePath());
            } else {
                // The file cannot be supported for the action type selected and an exception request is sent to RequestPublisher.
                requestPublisher.publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in Selecting file. File unsupported or not found. Please, try again")));
            }
        }
    }

    // During a rule creation, Action Table View has a list of actions. This method can add actions to the list.
    public void addActionToRule(ActionEvent actionEvent) {
        // Getting the action type.
        ActionType type = (ActionType) actionSelector.getSelectionModel().getSelectedItem();

        // Initializing a list of parameters, such as:
        //  AudioAction: file;
        // DialogBoxAction: title, header, message.
        List<String> params = new ArrayList<>();

        switch (type) {
            case DIALOGBOX:
                params.add(titleAlertField.getText());
                params.add(headerAlertField.getText());
                params.add(messageAlertField.getText());
            case MP3PLAYER:
                params.add(audioFileChosenLbl.getText());
            case COPYFILE:
                params.add(copyFileChosenLbl.getText());
                params.add(copyFileChosenLbl1.getText());
            case MOVEFILE:
                params.add(moveFileChosenLbl.getText());
                params.add(moveFileChosenLbl1.getText());
            case DELETEFILE:
                params.add(deleteFileChosenLbl.getText());
            case MODIFYTEXTFILE:
                params.add(modifyFileChosenLbl.getText());
                params.add(modifyFileSelectorTxtFld.getText());
        }

        // ActionFactory can create an Action, knowing its ActionType and params.
        Map.Entry<ActionType, List<String>> actionParams = Map.entry(type, params);

        // Creating Action
        Action action = ActionFactory.createAction(actionParams);

        if (action == null) {
            // This action has invalid parameters and ActionFactory asks the RequestPublisher to publish an exception request.
            return;
        }

        // Adding an action to the Action TableView.
        actionsList.add(action);

        // Updating Action TableView.
        actionsTable.refresh();
        actionsTableSummary.refresh();
    }

    // The saveRule method takes all the required parameters from GUIObjects and adds the rule to the ruleManager.
    public void saveRule(ActionEvent actionEvent) {
        // Rule name is taken from a TextField.
        String ruleName = ruleNameField.getText();

        // Trigger type is taken from a ComboBox
        TriggerType triggerType = (TriggerType) triggerSelector.getSelectionModel().getSelectedItem();

        // Trigger parameters are taken from specific GUIObjects due the triggerType.
        List<String> triggerParams = triggerParams(triggerType);

        // Checking that everything is valid
        if (!actionsList.isEmpty() && !triggerType.name().isEmpty() && triggerParams != null) {
            // This will be the state of the rule
            Status status = Status.valueOf(ruleStateBtn.getText());

            // Trigger Factory creates trigger by knowing its type and a list of parameters.
            Map.Entry<TriggerType, List<String>> t = Map.entry(triggerType, triggerParams);

            // Creating a trigger
            Trigger trigger = TriggerFactory.createTrigger(t);

            if (trigger == null) {
                // If TriggerFactory returns null, the trigger cannot be created due invalid parameters:
                // RequestPublisher is triggered.
                return;
            }

            // RuleFactory creates a rule through its name, list of actions, trigger and a state.
            int nextActivation = 0;
            if (activationCheckBox.isSelected()) {
                nextActivation = Integer.parseInt(minuteTextField.getText());
            }

            Rule rule = !activationCheckBox.isSelected() ?
                    RuleFactory.createRule(ruleName, actionsList, trigger, status) :
                    RuleFactory.createRule(ruleName, actionsList, trigger, nextActivation, status);

            if (rule == null) {
                // If RuleFactory returns null, the trigger cannot be created due invalid parameters:
                // RequestPublisher is triggered.
                return;
            }

            // Editing a rule process works through this control:
            // If the user is editing a rule, editingRule is that rule.
            if (editingRule != null) {
                // Removing the selected rule.
                ruleManager.removeRule(editingRule);
                editingRule = null;
            }

            // Adding a rule to the RuleManager list of rules.
            ruleManager.addRule(rule);
        }

        // Cleaning all GUIObjects, such as labels or textfields.
        resetRule(null);

        actionsTable.refresh();
        actionsTableSummary.refresh();

    }

    // This method returns a list of params due the TriggerType
    private List<String> triggerParams(TriggerType type) {
        List<String> params = new ArrayList<>();

        // If TriggerType is a CLOCKTRIGGER, the questioned GUIObjects will be hourField and miunuteField.
        if (Objects.requireNonNull(type) == TriggerType.CLOCKTRIGGER) {
            params.add(hourField.getText() + ":" + minuteField.getText());
        }

        return params;
    }

    // The last tab of TabView is made by this method.
    public void makeRuleSummary(Event event) {
        String ruleName = ruleNameField.getText();
        ruleNameSummary.setText(ruleName.isEmpty() ? "Rule name" : ruleName);

        TriggerType trigger = triggerSelector.getSelectionModel().getSelectedItem();

        if (trigger != null) {
            if (trigger == TriggerType.CLOCKTRIGGER) {
                StringBuffer triggerSelected = new StringBuffer();

                triggerSelected.append(triggerSelector.getSelectionModel().getSelectedItem().toString()).append(" ");
                triggerSelected.append(hourField.getText()).append(":");
                triggerSelected.append(minuteField.getText());

                triggerLbl.setText(triggerSelected.toString());
            }
        }
    }

    // Resetting the TabView.
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

    // This method can be called by editing a rule.
    public void editRuleAction(ActionEvent actionEvent) {
        editingRule = tableView.getSelectionModel().getSelectedItem();

        ruleNameField.setText(editingRule.getName());

        Trigger trigger = editingRule.getTrigger();

        if (trigger == null) {
            requestPublisher.publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in editing this rule. ")));
            return;
        }

        TriggerType triggerType = trigger.getType();

        triggerSelector.setValue(triggerType);

        if (Objects.requireNonNull(triggerType) == TriggerType.CLOCKTRIGGER) {
            LocalTime time = ((ClockTrigger) trigger).getTime();
            hourField.setText(String.valueOf(time.getHour()));
            minuteField.setText(String.valueOf(time.getMinute()));
        }

        actionsList.addAll(editingRule.getActions());

        actionsTable.refresh();
        actionsTableSummary.refresh();
    }

    // This method can remove the selected rules from the RuleManager
    public void removeRulesAction(ActionEvent actionEvent) {
        List<Rule> rules = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());

        if (!rules.isEmpty()) {
            for (Rule rule : rules) {
                if (ruleManager.getRules().contains(rule)) {
                    ruleManager.removeRule(rule);
                }
            }
        } else {
            requestPublisher.publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in removing rules. No rule was selected. Please, try again")));
        }
    }

    public void saveRulesToFile(ActionEvent actionEvent) {
        Stage fileChooserDialog = new Stage();
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showOpenDialog(fileChooserDialog);

        List<Rule> rules = tableView.getSelectionModel().getSelectedItems();

        fileManager.saveRulesToFile(rules, file);
    }

    public void loadRulesFromFile(ActionEvent actionEvent) {
        Stage fileChooserDialog = new Stage();
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showOpenDialog(fileChooserDialog);

        for (Rule rule : fileManager.loadRulesFromFile(file)) {
            ruleManager.addRule(rule);
        }
    }

    public void turnRule(ActionEvent actionEvent) {
        List<Rule> selectedRules = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());

        if (!selectedRules.isEmpty()) {
            for (Rule rule : selectedRules) {
                Status oldStatus = rule.getStatus();
                Status newStatus = oldStatus.equals(Status.ACTIVE) ? Status.INACTIVE : Status.ACTIVE;
                if (ruleManager.getRules().contains(rule)) {
                    ruleManager.setStatus(rule, newStatus);
                }
            }
        } else {
            requestPublisher.publishRequest(RequestFactory.createExceptionRequest(new Exception("Error in removing rules. No rule was selected. Please, try again")));
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
                    actions.append(action).append(" - ");
                }
            }

            return new ReadOnlyObjectWrapper<>(actions).asString();
        });

        stateClm.setCellValueFactory(cellData -> {
            Rule rule = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(rule.getStatus().name());
        });

        tableView.setItems(guiRuleList.getList());

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends Rule> change) -> {
            int selectedCount = tableView.getSelectionModel().getSelectedItems().size();
            boolean disableButton = selectedCount != 1;
            editBtn.setDisable(disableButton);
        });

    }

    private void initializeRuleCreationParadigm() {

        // First TabView
        minuteTextField.disableProperty().bind(activationCheckBox.selectedProperty().not());

        minuteTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                minuteField.setText(oldValue); // Reimposta al valore precedente se non è numerico
            } else if (!newValue.isEmpty()) {
                int minute = Integer.parseInt(newValue);
                if (minute < 0 || minute > 59) {
                    minuteField.setText(oldValue); // Reimposta al valore precedente se non è nel range 0-59
                }
            }
        });

        //Second TabView
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

        // Third TabView
        List<ActionType> actionList = List.of(ActionType.values());
        ObservableList<ActionType> actionObservableList = FXCollections.observableArrayList(actionList);
        actionSelector.setItems(actionObservableList);

        ChangeListener<ActionType> selectActionChangeListener = (observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals(ActionType.DIALOGBOX)) {
                    messageActionVBox.setVisible(true);
                    audioFileSelectorVBox.setVisible(false);
                    copyFileSelectorVBox.setVisible(false);
                    moveFileSelectorVBox.setVisible(false);
                    deleteFileSelectorVBox.setVisible(false);
                    modifyTextFileSelectorVBox.setVisible(false);
                } else if (newValue.equals(ActionType.MP3PLAYER)) {
                    audioFileSelectorVBox.setVisible(true);
                    messageActionVBox.setVisible(false);
                    copyFileSelectorVBox.setVisible(false);
                    moveFileSelectorVBox.setVisible(false);
                    deleteFileSelectorVBox.setVisible(false);
                    modifyTextFileSelectorVBox.setVisible(false);
                } else if (newValue.equals(ActionType.COPYFILE)) {
                    copyFileSelectorVBox.setVisible(true);
                    messageActionVBox.setVisible(false);
                    audioFileSelectorVBox.setVisible(false);
                    moveFileSelectorVBox.setVisible(false);
                    deleteFileSelectorVBox.setVisible(false);
                    modifyTextFileSelectorVBox.setVisible(false);
                } else if (newValue.equals(ActionType.MOVEFILE)) {
                    moveFileSelectorVBox.setVisible(true);
                    copyFileSelectorVBox.setVisible(false);
                    messageActionVBox.setVisible(false);
                    audioFileSelectorVBox.setVisible(false);
                    deleteFileSelectorVBox.setVisible(false);
                    modifyTextFileSelectorVBox.setVisible(false);
                } else if (newValue.equals(ActionType.DELETEFILE)) {
                    deleteFileSelectorVBox.setVisible(true);
                    moveFileSelectorVBox.setVisible(false);
                    copyFileSelectorVBox.setVisible(false);
                    messageActionVBox.setVisible(false);
                    audioFileSelectorVBox.setVisible(false);
                    modifyTextFileSelectorVBox.setVisible(false);
                } else if (newValue.equals(ActionType.MODIFYTEXTFILE)) {
                    modifyTextFileSelectorVBox.setVisible(true);
                    deleteFileSelectorVBox.setVisible(false);
                    moveFileSelectorVBox.setVisible(false);
                    copyFileSelectorVBox.setVisible(false);
                    messageActionVBox.setVisible(false);
                    audioFileSelectorVBox.setVisible(false);
                } else {
                    modifyTextFileSelectorVBox.setVisible(false);
                    deleteFileSelectorVBox.setVisible(false);
                    moveFileSelectorVBox.setVisible(false);
                    copyFileSelectorVBox.setVisible(false);
                    messageActionVBox.setVisible(false);
                    audioFileSelectorVBox.setVisible(false);
                }
            }
        };

        hourField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                hourField.setText(oldValue); // Reimposta al valore precedente se non è numerico
            } else if (!newValue.isEmpty()) {
                int hour = Integer.parseInt(newValue);
                if (hour < 0 || hour > 24) {
                    hourField.setText(oldValue); // Reimposta al valore precedente se non è nel range 0-24
                }
            }
        });

        minuteField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                minuteField.setText(oldValue); // Reimposta al valore precedente se non è numerico
            } else if (!newValue.isEmpty()) {
                int minute = Integer.parseInt(newValue);
                if (minute < 0 || minute > 59) {
                    minuteField.setText(oldValue); // Reimposta al valore precedente se non è nel range 0-59
                }
            }
        });

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

        saveRuleBtn.disableProperty().bind(ruleNameSummary.textProperty().isEmpty().or(triggerLbl.textProperty().isEmpty().or(hourField.textProperty().isEmpty()).or(minuteField.textProperty().isEmpty()).or(Bindings.isEmpty(actionsList))));

    }

    public void removeAction(ActionEvent actionEvent) {
        Set<Action> actions = new HashSet<>(actionsTable.getSelectionModel().getSelectedItems());
        actions.addAll(actionsTableSummary.getSelectionModel().getSelectedItems());

        actionsList.removeAll(actions);
        actionsTable.refresh();
    }
}