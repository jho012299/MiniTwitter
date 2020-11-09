package cs3560;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Mini Twitter");

        Admin admin = Admin.getInstance();
        primaryStage.setScene(new Scene(admin.showUI(), 750, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
