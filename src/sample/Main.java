package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    private static final double canvasWidth = 600.0;
    private static final double canvasHeight = 400.0;

    private static final double tickLength = 0.05;

    @Override
    public void start(Stage primaryStage) throws Exception{



        AnchorPane root = new AnchorPane();
        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);


        new AnimationTimer() {
            long lastTick=0;

            public void handle(long now) {
                if(lastTick==0) {
                    lastTick = now;

                    return;
                }

                if(now - lastTick > 1000000000 / tickLength ) {
                    lastTick = now;
                }
            }
        }.start();



        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
