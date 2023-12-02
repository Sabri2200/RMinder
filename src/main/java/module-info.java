module coscarelli.seproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens coscarelli.seproject to javafx.fxml;
    exports coscarelli.seproject;
}