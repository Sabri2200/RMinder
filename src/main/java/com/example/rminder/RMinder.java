package com.example.rminder;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RMinder extends Application {
    public RMinder() {
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RMinder.class.getResource("view/main-window.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load());

        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch(new String[0]);
    }
}
