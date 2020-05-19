package debug;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.util.Scanner;
import main.Simulation;
import misc.Vec3d;

import entities.*;

import java.util.Scanner;


public class DebugMain extends Application {

    static Simulation simulation;

    static Marble marble1, marble2;

    private static final double tickLength = 100.0;
    private static final double canvasScaleX = 500;
    private static final double canvasScaleY = 500;



    @Override
    public void start(Stage primaryStage) throws Exception {

        simulation = new Simulation();


        Canvas canvas = new Canvas(1080, 970);
        Group root = new Group();
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Debug");
        primaryStage.setScene(scene);
        primaryStage.show();
        canvas.getGraphicsContext2D().scale(canvasScaleX, canvasScaleY);



        marble1 = new Marble(1, 0.1);
        marble2 = new Marble(1, 0.1);
        marble1.setPos(new Vec3d(.2, 0, -0.3));
        marble2.setPos(new Vec3d(.5, 0, -0.3));
        simulation.addEntities(marble1, marble2);

        marble1.setVelo(new Vec3d(0.05, 0, 0));


        drawShapes(gc);
        AnimationTimer timer = new AnimationTimer() {
            long lastTick = 0;

            public void handle(long now) {
                if (lastTick == 0) {
                    lastTick = now;

                    return;
                }

                if (now - lastTick > 1000000000 / tickLength && !simulation.isPaused()) {
                    lastTick = now;
                    tick(1 / tickLength, gc);
                }
            }
        };
        timer.start();


    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void tick(double deltaTick, GraphicsContext gc) {


        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        drawShapes(gc);


        simulation.tick(deltaTick);
    }

    static void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.RED);

        gc.fillOval(marble1.getPos().x - marble1.getDiameter() / 2, (marble1.getPos().z - marble1.getDiameter() / 2)* -1,
                marble1.getDiameter() , marble1.getDiameter() );

        gc.fillOval(marble2.getPos().x - marble2.getDiameter() / 2, (marble2.getPos().z - marble2.getDiameter() / 2)* -1,
                marble2.getDiameter() , marble2.getDiameter() );
    }

    static void drawSphere(GraphicsContext gc, Sphere s, Color c) {
        Paint old = gc.getFill();
        gc.setFill(c);
        gc.fillOval(s.getPos().x - s.getDiameter() / 2, (s.getPos().z - s.getDiameter() / 2)* -1,
                s.getDiameter() , s.getDiameter());
        gc.setFill(old);
    }
    static void drawRectangle(GraphicsContext gc, Rectangle s, Color c) {
        Paint old = gc.getFill();
        gc.setFill(c);

        //TODO Rectangle drawing

        gc.setFill(old);
    }

    static void drawAllShapes(GraphicsContext gc) {
        for(Entity e : simulation.getEntities()) {
            if(e instanceof Sphere) {
                drawSphere(gc, (Sphere) e, Color.LIGHTSKYBLUE);
            } else if(e instanceof Rectangle) {
                drawRectangle(gc, (Rectangle) e, Color.DARKOLIVEGREEN);
            } else {
                System.err.println("Entity is not valid!");
            }
        }
    }

}
