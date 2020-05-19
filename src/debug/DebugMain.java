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
import javafx.stage.Stage;
import main.MovementComponent;
import main.Simulation;
import main.customActors.Marble;
import misc.Vec3d;

import static java.lang.System.out;


public class DebugMain extends Application {

    static Simulation simulation;

    static Marble marble1, marble2, marble3;

    private static final double tickLength = 100.0;


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



        marble1 = new Marble(simulation, 1, 100, new Vec3d());
        marble2 = new Marble(simulation, 1, 100, new Vec3d());
        marble3 = new Marble(simulation, 1, 100, new Vec3d());
        marble1.getMovementComponent().setPosition(new Vec3d(100, 0, -300));
        marble2.getMovementComponent().setPosition(new Vec3d(400, 0, -300));
        marble3.getMovementComponent().setPosition(new Vec3d(250, 0, -800));
        simulation.addActors(marble1, marble2, marble3);

        marble1.getMovementComponent().setMovementVector(new Vec3d(80, 0, -80));
        marble2.getMovementComponent().setMovementVector(new Vec3d(-80, 0, -80));
        marble3.getMovementComponent().setMovementVector(new Vec3d(50, 0, 50));

        new AnimationTimer() {
            long lastTick = 0;

            public void handle(long now) {
                if (lastTick == 0) {
                    lastTick = now;

                    return;
                }

                if (now - lastTick > 1000000000 / tickLength) {
                    lastTick = now;
                    tick(1 / tickLength, gc);
                }
            }
        }.start();


    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void tick(double deltaTick, GraphicsContext gc) {


        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        drawShapes(gc);

        /*
        MovementComponent ma1Mc = marble1.getMovementComponent();
        MovementComponent ma2Mc = marble2.getMovementComponent();

        gc.setFill(Color.RED);
        gc.fillOval(ma1Mc.getPosition().x - ma1Mc.getDiameter() / 2, ma1Mc.getPosition().y - ma1Mc.getDiameter() / 2,
                ma1Mc.getDiameter() / 2, ma1Mc.getDiameter() / 2);
        gc.fillOval(ma2Mc.getPosition().x - ma2Mc.getDiameter() / 2, ma2Mc.getPosition().y - ma2Mc.getDiameter() / 2,
                ma2Mc.getDiameter() / 2, ma2Mc.getDiameter() / 2);


         */
        //out.println("Marble1 pos: " + marble1.getMovementComponent().getPosition() + ".");
        //out.println("Marble2 pos: " + marble2.getMovementComponent().getPosition() + ". \n");

        simulation.tick(deltaTick);
    }

    static void drawShapes(GraphicsContext gc) {



        MovementComponent ma1Mc = marble1.getMovementComponent();
        MovementComponent ma2Mc = marble2.getMovementComponent();
        MovementComponent ma3Mc = marble3.getMovementComponent();

        gc.setFill(Color.RED);
        gc.fillOval(ma1Mc.getPosition().x - ma1Mc.getDiameter() / 2, (ma1Mc.getPosition().z*-1) - ma1Mc.getDiameter() / 2,
                ma1Mc.getDiameter() , ma1Mc.getDiameter() );
        gc.setFill(Color.GREEN);
        gc.fillOval(ma2Mc.getPosition().x - ma2Mc.getDiameter() / 2, (ma2Mc.getPosition().z*-1) - ma2Mc.getDiameter() / 2,
                ma2Mc.getDiameter() , ma2Mc.getDiameter() );
        gc.setFill(Color.BLUE);
        gc.fillOval(ma3Mc.getPosition().x - ma3Mc.getDiameter() / 2, (ma3Mc.getPosition().z*-1) - ma3Mc.getDiameter() / 2,
                ma3Mc.getDiameter() , ma3Mc.getDiameter() );
    }

}
