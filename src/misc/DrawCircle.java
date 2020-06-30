package misc;

import entities.Sphere;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class DrawCircle extends Circle {

    private Sphere sphere;

    public Sphere getSphere() {
        return sphere;
    }

    public void setSphere(Sphere sphere) {
        this.sphere = sphere;
    }

    public DrawCircle(double x, double y, double r, Paint color) {
        super(x,y,r,color);


    }
}
