package gruppo13.seproject;

import gruppo13.seproject.GUIExcecutor.GUIExecutor;
import gruppo13.seproject.essential.State;
import gruppo13.seproject.essential.action.Action;
import gruppo13.seproject.essential.action.ActionType;
import gruppo13.seproject.essential.action.actionType.AudioAction;
import gruppo13.seproject.essential.action.actionType.DialogBoxAction;
import gruppo13.seproject.essential.rule.*;
import gruppo13.seproject.essential.trigger.Trigger;
import gruppo13.seproject.essential.trigger.TriggerType;
import gruppo13.seproject.essential.trigger.triggerType.ClockTrigger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;

public class MainController implements Initializable {

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
    //private ActionManager actionManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setStage(new Stage());

        ruleManager = new RuleManager();
        List<Action> actions = new ArrayList<>();

        //actions.add(new DialogBoxAction("title", "content", "message"));
        actions.add(new AudioAction(new File("/Users/michelecoscarelli/Downloads/gg.mp3")));
        Trigger trigger = new ClockTrigger(LocalTime.now());

        ruleManager.addRule(new Rule("name", actions, trigger, State.ACTIVE));

        startRuleService();
    }



    public void ruleStateChange(ActionEvent actionEvent) {

    }

    public void fileSelector(ActionEvent actionEvent) {
    }

    public void addActionToRule(ActionEvent actionEvent) {
    }

    public void makeRuleSummary(Event event) {
    }

    public void resetRule(ActionEvent actionEvent) {
    }

    public void saveRule(ActionEvent actionEvent) {
    }

    @FXML
    public void editRuleAction(ActionEvent actionEvent) {
        /*
        Rule selectedRule = tableView.getSelectionModel().getSelectedItem();

        if (selectedRule != null) {
            ruleNameField.setText(selectedRule.getName());
            triggerSelector.setValue(selectedRule.getTrigger().getType());

            if (selectedRule.getTrigger().getType() == TriggerType.CLOCKTRIGGER) {
                clockTriggerVBox.setVisible(true);
                LocalTime triggerTime = ((ClockTrigger) selectedRule.getTrigger()).getTime();
                hourField.setText(String.valueOf(triggerTime.getHour()));
                minuteField.setText(String.valueOf(triggerTime.getMinute()));
            }

            if (!selectedRule.getActions().isEmpty()) {
                Action firstAction = selectedRule.getActions().get(0);
                actionSelector.setValue(firstAction.getType());

                if (firstAction.getType() == ActionType.DIALOGBOX) {
                    messageActionVBox.setVisible(true);
                    titleAlertField.setText(((DialogBoxAction) firstAction).getTitle());
                    messageAlertField.setText(((DialogBoxAction) firstAction).getMessage());
                    fileSelectorVBox.setVisible(false);
                } else if (firstAction.getType() == ActionType.MP3PLAYER) {
                    fileSelectorVBox.setVisible(true);
                    fileChosen.setText(((AudioAction) firstAction).getFilePath());
                    messageActionVBox.setVisible(false);
                }
            }
        }

         */
    }

    public void removeRulesAction(ActionEvent actionEvent) {
    }

    public void saveRulesToFile(ActionEvent actionEvent) {
    }

    public void loadRulesFromFile(ActionEvent actionEvent) {
    }

    public void turnRule(ActionEvent actionEvent) {
    }



    private void startRuleService() {
        Timer timer = new Timer();
        RuleService task = new RuleService(ruleManager);

        task.registerObserver(new GUIExecutor());

        timer.scheduleAtFixedRate(task, 0, 2000);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(this::closeApplication);
    }

    private void closeApplication(WindowEvent windowEvent) {
        Platform.exit();
    }

}