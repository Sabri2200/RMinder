package gruppo13.seproject;

import gruppo13.seproject.essential.Action.*;
import gruppo13.seproject.essential.FileManager;
import gruppo13.seproject.essential.Rule;
import gruppo13.seproject.essential.RuleCommand;
import gruppo13.seproject.essential.RuleManager;
import gruppo13.seproject.essential.Trigger.ClockTrigger;
import gruppo13.seproject.essential.Trigger.Trigger;
import gruppo13.seproject.essential.Trigger.TriggerFactory;
import gruppo13.seproject.essential.Trigger.TriggerType;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    @FXML
    private MenuItem editBtn;
    @FXML
    private MenuItem removeBtn;

    @FXML
    private MenuItem saveToFileBtn;
    @FXML
    private MenuItem loadFromFileBtn;

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

    private TriggerType selectedTriggerType;
    private ActionType selectedActionType;

    private TriggerFactory triggerFactory;
    private ActionFactory actionFactory;
    private RuleCommand ruleCommand;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        triggerFactory = new TriggerFactory();
        actionFactory = new ActionFactory();

        ruleCommand = new RuleManager();

        nameClm.setCellValueFactory(cellData -> {
            Rule rule = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(rule.getName());
        });

        triggerClm.setCellValueFactory(cellData -> {
            Rule rule = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(rule.getTrigger().getType().toString());
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


        List<TriggerType> triggerList = List.of(TriggerType.values());
        ObservableList<TriggerType> triggerObservableList = FXCollections.observableArrayList(triggerList);
        triggerSelector.setItems(triggerObservableList);

        ChangeListener<TriggerType> selectTriggerChangeListener = (observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals(TriggerType.ClockTrigger)) {
                    clockTriggerHBox.setVisible(true);
                    selectedTriggerType = TriggerType.ClockTrigger;
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
                    selectedActionType = ActionType.DIALOGBOX;
                } else if (newValue.equals(ActionType.MP3PLAYER)) {
                    fileSelectorVBox.setVisible(true);
                    messageActionVBox.setVisible(false);
                    selectedActionType = ActionType.MP3PLAYER;
                } else {
                    messageActionVBox.setVisible(false);
                    fileSelectorVBox.setVisible(false);
                }
            }
        };
        actionSelector.valueProperty().addListener(selectActionChangeListener);

        //saveRuleBtn.disableProperty().bind(ruleNameField.textProperty().isEmpty().or(hourField.textProperty().isEmpty()).or(minuteField.textProperty().isEmpty()).or(fileChosen.textProperty().isEmpty()));

        ruleCommand.execute(
                new Service<>() {
                    @Override
                    protected Task<Void> createTask() {
                        return new Task<>() {
                            @Override
                            protected Void call() throws Exception {
                                System.out.println("Action performed every 2 seconds");
                                for (Rule rule : ruleCommand.getList()) {
                                    if (rule.getState()) {
                                        if (rule.getTrigger().verify()) {
                                            Platform.runLater(() -> rule.execute());
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
        fileChosen.setText(file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    public void resetRule(ActionEvent actionEvent) {
        Stage fileChooserDialog = new Stage();
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showOpenDialog(fileChooserDialog);
        FileManager fm = new FileManager(file);

        for (Rule rule : fm.loadRulesFromFile()) {
            ruleCommand.addRule(rule);
        }
        tableView.refresh();
    }

    public void saveRule(ActionEvent actionEvent) {
        String ruleName = ruleNameField.getText();
        if (!ruleCommand.getList().contains(new Rule(ruleName, null, null, new SimpleBooleanProperty(false))) || tableView.getSelectionModel().getSelectedItem() != null) {
            Trigger t = null;

            if (selectedTriggerType == TriggerType.ClockTrigger) {
                t = triggerFactory.createClockTrigger(LocalTime.of(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText())));
            } else {
                Platform.runLater(() -> actionFactory.createMessageAction("Internal Error", "This trigger type is not supported").execute());
            }

            Action a = null;

            if (selectedActionType == ActionType.DIALOGBOX) {
                a = actionFactory.createMessageAction(titleAlertField.getText(), messageAlertField.getText());
            } else if (selectedActionType == ActionType.MP3PLAYER) {
                a = actionFactory.createAudioAction(fileChosen.getText());
            } else {
                Platform.runLater(() -> actionFactory.createMessageAction("Internal Error", "This action type is not supported").execute());
            }

            Action[] as = new Action[5];
            as[0] = a;

            Rule rule = new Rule(ruleName, as, t, new SimpleBooleanProperty(true));

            ruleCommand.addRule(rule);
            //list.add(rule);
            tableView.refresh();
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

            actionSelector.setValue(selectedItem.getActions()[0].getType());
            if (selectedItem.getActions()[0].getType() == ActionType.DIALOGBOX) {
                messageActionVBox.setVisible(true);
                titleAlertField.setText(((MessageAction) selectedItem.getActions()[0]).getTitle());
                messageAlertField.setText(((MessageAction) selectedItem.getActions()[0]).getMessage());
            } else if (selectedItem.getActions()[0].getType() == ActionType.MP3PLAYER) {
                fileSelectorVBox.setVisible(true);
                fileChosen.setText(((AudioAction)selectedItem.getActions()[0]).getPath());
            }
        }
    }

    public void removeRulesAction() {
        List<Rule> selectedItems = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());
        tableView.getItems().removeAll(selectedItems);

        for (Rule rule : selectedItems) {
            ruleCommand.removeRule(rule);
        }

        tableView.refresh();
    }

    public void saveRulesToFile() {
        FileManager fm = new FileManager(new File(fileSelector()));
        List<Rule> selectedItems = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());

        fm.saveRulesToFile(selectedItems);

        tableView.refresh();
    }

    public void loadRulesFromFile() {
        FileManager fm = new FileManager(new File(fileSelector()));

        for (Rule rule : fm.loadRulesFromFile()) {
            ruleCommand.addRule(rule);
        }

        tableView.refresh();
    }

}
