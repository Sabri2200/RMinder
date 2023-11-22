module com.example.rminder {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.rminder to javafx.fxml;
    exports com.example.rminder;
}