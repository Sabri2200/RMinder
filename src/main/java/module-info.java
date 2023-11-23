module com.example.rminder {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.rminder to javafx.fxml;
    exports com.example.rminder.controller;
    opens com.example.rminder.controller to javafx.fxml;
    exports com.example.rminder.model;
    opens com.example.rminder.model to javafx.fxml;
    exports com.example.rminder;
}