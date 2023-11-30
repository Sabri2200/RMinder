package gruppo13.seproject.essential.controller;

import gruppo13.seproject.essential.model.Action.Action;
import gruppo13.seproject.essential.FileManager;
import gruppo13.seproject.essential.Rule;
import gruppo13.seproject.essential.RuleCommand;
import gruppo13.seproject.essential.RuleManager;
import gruppo13.seproject.essential.model.Action.*;
import gruppo13.seproject.essential.model.Trigger.ClockTrigger;
import gruppo13.seproject.essential.model.Trigger.Trigger;
import gruppo13.seproject.essential.model.Trigger.TriggerFactory;
import gruppo13.seproject.essential.model.Trigger.TriggerType;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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

import java.io.File;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;

//modificare nome in Controller
public class MainController implements Initializable {

    public TextField ruleNameField;
    public ComboBox triggerSelector;
    public HBox clockTriggerHBox;
    public ComboBox actionSelector;
    public VBox messageActionVBox;
    public TextField titleAlertField;
    public TextField messageAlertField;
    public VBox fileSelectorVBox;
    public Button fileSelectorBtn;
    public Button resetBtn;
    public Button saveRuleBtn;
    public TextField hourField;
    public TextField minuteField;
    public Label fileChosen;

    @FXML
    public ContextMenu contextMenu;
    public Button ruleStateBtn;
    public VBox clockTriggerVBox;
    public TableView actionsTable;
    public TableColumn<Action, String> actionTypeClm;
    public TableColumn<Action, String> actionTypeClm1;
    public TableColumn<Action, String> paramsClm;
    public TableColumn<Action, String> paramsClm1;
    public Button addActionBtn;
    public Label ruleNameSummary;
    public Label triggerLbl;
    public TableView actionsTableSummary;
    public AnchorPane summaryAnchorPane;
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

    private TriggerFactory triggerFactory;
    private ActionFactory actionFactory;
    private RuleCommand ruleCommand;
    private ObservableList<Action> actionsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        triggerFactory = new TriggerFactory();
        actionFactory = new ActionFactory();

        ruleCommand = new RuleManager();

        // initializing tableView

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
                    actions.append(action.toString());
                }
            }

            return new ReadOnlyObjectWrapper<>(actions).asString();
        });

        stateClm.setCellValueFactory(cellData -> {
            Rule rule = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(rule.getState() ? "active" : "not active");
        });

        tableView.setItems(ruleCommand.getList());

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends Rule> change) -> {
            boolean multipleSelection = tableView.getSelectionModel().getSelectedItems().size() > 1;
            editBtn.setDisable(multipleSelection);
        });

        // initializing rule Creation Paradigm

        List<TriggerType> triggerList = List.of(TriggerType.values());
        ObservableList<TriggerType> triggerObservableList = FXCollections.observableArrayList(triggerList);
        triggerSelector.setItems(triggerObservableList);

        ChangeListener<TriggerType> selectTriggerChangeListener = (observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals(TriggerType.ClockTrigger)) {
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

        ruleCommand.execute(
                new Service<>() {
                    @Override
                    protected Task<Void> createTask() {
                        return new Task<>() {
                            @Override
                            protected Void call() throws Exception {
                                System.out.println("Action performed every 2 seconds");
                                for (Rule rule : ruleCommand.getList()) {
                                    System.out.println(rule.toString());
                                    if (rule.getState()) {
                                        if (rule.getTrigger().verify()) {
                                            Platform.runLater(rule::execute);
                                            Platform.runLater(() -> rule.setState(new SimpleBooleanProperty(false)));
                                            tableView.refresh();
                                        }
                                    }
                                }
                                return null;
                            }
                        };
                    }
                }
                );
    }

    public String fileSelector() {
        Stage fileChooserDialog = new Stage();
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showOpenDialog(fileChooserDialog);
        if (FileManager.verifyFile(file) != null) {
            fileChosen.setText(file.getAbsolutePath());
            return file.getAbsolutePath();
        } else {
            Platform.runLater(() -> actionFactory.createMessageAction("Internal Error", "This file is not supported").execute());
            return null;
        }
    }

    public void resetRule(ActionEvent actionEvent) {
        ruleNameField.setText("");
        ruleNameSummary.setText("Rule name");

        actionsList.removeAll();
        actionSelector.cancelEdit();
        actionsTable.refresh();
        actionsTableSummary.refresh();

        triggerLbl.setText("Trigger and Params");
        triggerSelector.cancelEdit();
    }

    public void saveRule(ActionEvent actionEvent) {
        String ruleName = ruleNameField.getText();
        if (!ruleCommand.getList().contains(new Rule(ruleName, null, null, new SimpleBooleanProperty(false))) || tableView.getSelectionModel().getSelectedItem() != null) {
            Trigger t = null;

            if (triggerSelector.getSelectionModel().getSelectedItem() == TriggerType.ClockTrigger) {
                String hour = hourField.getText();
                String minute = minuteField.getText();

                try {
                    int hourInt = Integer.parseInt(hour);
                    int minuteInt = Integer.parseInt(minute);

                    if (hourInt >= 0 && hourInt <= 24 && minuteInt >= 0 && minuteInt <= 60) {
                        t = triggerFactory.createClockTrigger(LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute)));
                    } else {
                        Platform.runLater(() -> actionFactory.createMessageAction("Internal Error", "Trigger time is not valid").execute());
                    }
                } catch (NumberFormatException e) {
                    Platform.runLater(() -> actionFactory.createMessageAction("Internal Error", "Trigger time is not valid").execute());
                }
            } else {
                Platform.runLater(() -> actionFactory.createMessageAction("Internal Error", "This trigger type is not supported").execute());
                return;
            }

            if (t != null) {
                Rule rule = new Rule(ruleName, actionsList, t, new SimpleBooleanProperty(ruleStateBtn.getText().equals("active")));
                ruleCommand.addRule(rule);
                tableView.refresh();
            } else {
                Platform.runLater(() -> actionFactory.createMessageAction("Internal Error", "Select a valid Trigger").execute());
            }

        } else {
            Platform.runLater(() -> actionFactory.createMessageAction("Internal Error", "This rule name is already used").execute());
        }
    }

    public void editRuleAction() {
        Rule selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            ruleNameField.setText(selectedItem.getName());

            triggerSelector.setValue(selectedItem.getTrigger().getType());

            if (selectedItem.getTrigger().getType() == TriggerType.ClockTrigger) {
                clockTriggerHBox.setVisible(true);
                hourField.setText(((ClockTrigger) selectedItem.getTrigger()).getTime().toString().split(":")[0]);
                minuteField.setText(((ClockTrigger) selectedItem.getTrigger()).getTime().toString().split(":")[1]);
            }

            actionSelector.setValue(selectedItem.getActions().get(0).getType());
            if (selectedItem.getActions().get(0).getType() == ActionType.DIALOGBOX) {
                messageActionVBox.setVisible(true);
                titleAlertField.setText(((MessageAction) selectedItem.getActions().get(0)).getTitle());
                messageAlertField.setText(((MessageAction) selectedItem.getActions().get(0)).getMessage());
            } else if (selectedItem.getActions().get(0).getType() == ActionType.MP3PLAYER) {
                fileSelectorVBox.setVisible(true);
                fileChosen.setText(((AudioAction)selectedItem.getActions().get(0)).getPath());
            }
        }
    }

    public void removeRulesAction(){
        List<Rule> selctedItems = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());
        tableView.getItems().removeAll(selctedItems);
        for(Rule rule : selctedItems){
            ruleCommand.removeRule(rule);
        }
        tableView.refresh();
    }

    public void saveRulesToFile() {
        FileManager fm = FileManager.createFileManager(new File(fileSelector()));

        if (fm == null) {
            Platform.runLater(() -> actionFactory.createMessageAction("Internal Error", "This file is not supported").execute());
        } else {
            List<Rule> selectedItems = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());

            fm.saveRulesToFile(selectedItems);

            tableView.refresh();
        }
    }

    public void loadRulesFromFile() {
        FileManager fm = FileManager.createFileManager(new File(fileSelector()));

        if (fm == null) {
            Platform.runLater(() -> actionFactory.createMessageAction("Internal Error", "This file is not supported").execute());
        } else {
            for (Rule rule : fm.loadRulesFromFile()) {
                ruleCommand.addRule(rule);
            }

            tableView.refresh();
        }
    }

    public void turnRule() {
        List<Rule> selctedItems = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());

        for (Rule rule : selctedItems) {
            Boolean sbp = rule.getState();
            rule.setState(new SimpleBooleanProperty(!sbp));
        }

        tableView.refresh();
    }

    public void addActionToRule(ActionEvent actionEvent) {
        ActionType actionType = ActionType.valueOf(actionSelector.getSelectionModel().getSelectedItem().toString());

        if (actionType == ActionType.MP3PLAYER) {
            String[] file = fileChosen.getText().split("\\.");
            String extension = file[(int) (Arrays.stream(file).count() - 1)];
            if (Objects.equals(extension, "mp3")) {
                actionsList.add(actionFactory.createAudioAction(fileChosen.getText()));
            } else {
                Platform.runLater(() -> actionFactory.createMessageAction("Internal Error", "This file type is not supported").execute());
                return;
            }
        } else if (actionType == ActionType.DIALOGBOX) {
            actionsList.add(actionFactory.createMessageAction(titleAlertField.getText(), messageAlertField.getText()));
        } else {
            Platform.runLater(() -> actionFactory.createMessageAction("Internal Error", "This action type is not supported").execute());
            return;
        }

        actionsTable.refresh();
        actionsTableSummary.refresh();
    }

    public void makeRuleSummary(Event mouseEvent) {
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

    public void ruleStateChange(ActionEvent actionEvent) {
        String s = ruleStateBtn.getText();
        boolean state = !Objects.equals(s, "Active");
        ruleStateBtn.setText(!state ? "Not Active" : "Active");
    }
}
