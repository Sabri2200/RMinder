package gruppo13.seproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File("/Users/michelecoscarelli/Desktop/IdeaProjects/SE-project/src/main/resources/gruppo13/seproject/main-view.fxml").toURL());
        Scene scene = new Scene(fxmlLoader.load(), 800, 550);
        stage.setResizable(false);
        stage.setTitle("RMinder!");
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> handleWindowClose(stage));

        stage.show();

    }

    private void handleWindowClose(Stage stage) {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}