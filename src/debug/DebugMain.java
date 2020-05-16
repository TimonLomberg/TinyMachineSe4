package debug;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Simulation;
import main.customActors.Marble;
import misc.Vec3d;

import static java.lang.System.out;



public class DebugMain extends Application {

    static Simulation simulation;

    static Marble marble1, marble2;

    private static final double tickLength = 100.0;

    @Override
    public void start(Stage primaryStage) throws Exception{

        simulation = new Simulation();



        AnchorPane root = new AnchorPane();
        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        marble1 = new Marble(simulation,1,1, new Vec3d());
        marble2 = new Marble(simulation,1,2, new Vec3d(10,0,1));

        simulation.addActors(marble1, marble2);

        marble1.getMovementComponent().setMovementVector(new Vec3d(50, 0, 0));

        new AnimationTimer() {
            long lastTick=0;

            public void handle(long now) {
                if(lastTick==0) {
                    lastTick = now;

                    return;
                }

                if(now - lastTick > 1000000000 / tickLength ) {
                    lastTick = now;
                    tick(1/tickLength);
                }
            }
        }.start();



        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        //primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void tick(double deltaTick) {

        out.println("Marble1 pos: " + marble1.getMovementComponent().getPosition() + ".");
        out.println("Marble2 pos: " + marble2.getMovementComponent().getPosition() + ". \n");

        simulation.tick(deltaTick);
    }

}
