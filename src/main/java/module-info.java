/*module com.example.rminder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    //requires javafx.media;


    opens com.example.rminder to javafx.fxml;
    exports com.example.rminder.controller;
    opens com.example.rminder.controller to javafx.fxml;
    exports com.example.rminder.model;
    opens com.example.rminder.model to javafx.fxml;
    exports com.example.rminder;
}*/
module gruppo13.seproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens gruppo13.seproject to javafx.fxml;
    exports gruppo13.seproject;
}