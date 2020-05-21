package entities;

import main.Simulation;
import misc.Polynomial3d;
import misc.Vec3d;

import java.util.Optional;

public class Track {
    protected Polynomial3d trackFunc;
    protected double[] xIntervall;
    protected double[] yIntervall;

    public Track(Polynomial3d trackFunc) {
        this.trackFunc = trackFunc;
    }

    public Vec3d normalAt(double x, double y) {
        var v1 = new Vec3d(1, 0, this.trackFunc.derivedByXIgnoreY().valueAt(x, 0));
        var v2 = new Vec3d(0, 1, this.trackFunc.derivedByYIgnoreX().valueAt(0, y));

        return v1.cross(v2).norm();
    }

    public Polynomial3d getFunc() {
        return this.trackFunc;
    }

    public double[] getXIntervall() {
        return this.xIntervall;
    }

    public boolean isColliding(Sphere sphere) {
        var pos = sphere.getPos();
        var funcX = this.trackFunc.derivedByXIgnoreY();
        var funcY = this.trackFunc.derivedByYIgnoreX();

        var epsilon = Math.sqrt(
                Math.pow(funcX.valueAt(pos.x, 0), 2)
                + Math.pow(funcY.valueAt(0, pos.y), 2)
                + 1
        );

        var funcValue = this.trackFunc.valueAt(sphere.getPos().x, sphere.getPos().y);
        var r = sphere.getDiameter()/2;

        // Sphere kann nicht kollidieren, spart (hoffentlich) rechenaufwand
        if (sphere.getPos().z - r > funcValue + (epsilon * r)) {
            return false;
        }

        return false;
    }
}
