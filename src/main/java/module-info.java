module gruppo13.seproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    requires org.json;

    opens gruppo13.seproject to javafx.fxml;
    exports gruppo13.seproject;
    exports gruppo13.seproject.essential.action;
    opens gruppo13.seproject.essential.action to javafx.fxml;
    exports gruppo13.seproject.essential.action.actionType;
    opens gruppo13.seproject.essential.action.actionType to javafx.fxml;
    exports gruppo13.seproject.essential;
    opens gruppo13.seproject.essential to javafx.fxml;
    exports gruppo13.seproject.essential.action.ActionException;
    opens gruppo13.seproject.essential.action.ActionException to javafx.fxml;
    exports gruppo13.seproject.essential.rule.ListObserver;
    opens gruppo13.seproject.essential.rule.ListObserver to javafx.fxml;
}