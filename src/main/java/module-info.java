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
    exports gruppo13.seproject.essential.action.type;
    opens gruppo13.seproject.essential.action.type to javafx.fxml;
    exports gruppo13.seproject.essential.action.exception;
    opens gruppo13.seproject.essential.action.exception to javafx.fxml;
    exports gruppo13.seproject.essential.rule;
    opens gruppo13.seproject.essential.rule to javafx.fxml;
}