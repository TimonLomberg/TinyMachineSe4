package visual;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.server.ExportException;
import java.util.Vector;

public class ApplicationMain extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("TinyMachineSe4");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

    }
}
