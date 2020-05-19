package debug;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Actor;
import main.MovementComponent;
import main.Simulation;
import main.customActors.Marble;
import misc.Vec3d;


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
        marble3.getMovementComponent().setPosition(new Vec3d(250, 0, -600));
        simulation.addActors(marble1, marble2, marble3);

        marble1.getMovementComponent().setMovementVector(new Vec3d(80, 0, -80));
        marble2.getMovementComponent().setMovementVector(new Vec3d(-80, 0, -80));
        marble3.getMovementComponent().setMovementVector(new Vec3d(0, 0, 95));

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
        drawAllShapes(gc, simulation);


        simulation.tick(deltaTick);
    }

    static void drawShapes(GraphicsContext gc) {


        MovementComponent ma1Mc = marble1.getMovementComponent();
        MovementComponent ma2Mc = marble2.getMovementComponent();
        MovementComponent ma3Mc = marble3.getMovementComponent();

        drawMarble(gc, marble1, Color.RED);
        drawMarble(gc, marble2, Color.GREEN);
        drawMarble(gc, marble3, Color.BLUE);
    }

    static void drawMarble(GraphicsContext gc, Marble m) {
        MovementComponent ma1Mc = m.getMovementComponent();
        gc.fillOval(ma1Mc.getPosition().x - ma1Mc.getDiameter() / 2, (ma1Mc.getPosition().z * -1) - ma1Mc.getDiameter() / 2,
                ma1Mc.getDiameter(), ma1Mc.getDiameter());
    }

    static void drawMarble(GraphicsContext gc, Marble m, Color color) {
        Color oldColor = (Color) gc.getFill();
        gc.setFill(color);
        MovementComponent ma1Mc = m.getMovementComponent();
        gc.fillOval(ma1Mc.getPosition().x - ma1Mc.getDiameter() / 2, (ma1Mc.getPosition().z * -1) - ma1Mc.getDiameter() / 2,
                ma1Mc.getDiameter(), ma1Mc.getDiameter());
        gc.setFill(oldColor);
    }

    public static void drawAllShapes(GraphicsContext gc, Simulation sim) {

        for (Actor a : sim.getActors()) {
            MovementComponent mc = a.getMovementComponent();
            switch (a.getObjectType()) {
                case Sphere -> {
                    gc.setFill(Color.LIGHTSKYBLUE);
                    gc.fillOval(mc.getPosition().x - mc.getDiameter() / 2, (mc.getPosition().z * -1) - mc.getDiameter() / 2,
                            mc.getDiameter(), mc.getDiameter());
                }
                case Rectangle -> {
                    gc.setFill(Color.DARKOLIVEGREEN);
                    gc.fillRect(mc.getPosition().x, mc.getPosition().z * -1, mc.getPosition2().x - mc.getPosition().x,
                            mc.getPosition2().z * -1 - mc.getPosition().z * -1);
                }

                default -> {
                }
            }
        }

    }

}
