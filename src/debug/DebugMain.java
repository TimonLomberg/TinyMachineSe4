package debug;

import com.sun.javafx.geom.Vec3d;
import javafx.animation.AnimationTimer;
import main.Simulation;
import main.customActors.Marble;


public class DebugMain {

    static Simulation simulation;

    static Marble marble1, marble2;

    static final double tickLength = 0.05;


    public static void main(String[] args) {

        simulation = new Simulation()

        Marble marble1 = new Marble(simulation, 1, 0.03, new Vec3d(0,0,0));

        new AnimationTimer() {

            long lastTick=0;

            public void handle(long now) {
                if(lastTick==0) {
                    lastTick = now;

                    return;
                }

                if(now - lastTick > 1000000000 / tickLength ) {
                    lastTick = now;
                    tick();
                }
            }

        }.start();

    }

    public static void tick() {

    }

}
