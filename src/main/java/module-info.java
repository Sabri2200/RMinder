module coscarelli.seproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens gruppo13.seproject to javafx.fxml;
    exports gruppo13.seproject;
}