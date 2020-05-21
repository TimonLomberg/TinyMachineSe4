package entities;

import main.Simulation;
import misc.Polynomial3d;
import misc.Vec3d;

public class SimpleTrack extends Track {

    public SimpleTrack(double s, double c) {
        super(new Polynomial3d(new double[]{ c, s }, new double[]{}));
    }

    @Override
    public boolean isColliding(Sphere sphere) {

        var slope = this.trackFunc.xFactors()[1];

        var p = sphere.getPos();
        var c = p.z - this.trackFunc.xFactors()[0] - this.trackFunc.xFactors()[1] * p.x;
        var d = Math.sqrt( Math.pow(c, 2) - 1 - Math.pow(this.trackFunc.xFactors()[1], 2) );

        var cond1 = sphere.getDiameter()/2 > d;

        var add = d / Math.sqrt(1 + Math.pow(slope, 2));
        var newIntervall = new double[]{ this.xIntervall[0] + add, this.xIntervall[1] + add };

        // außerhalb bahn intervall (könnte mit ecke kollidieren)
        if (p.x < newIntervall[0] && p.x > newIntervall[1]) {
            return cond1
                    && Math.sqrt(Math.pow(p.x - this.xIntervall[0], 2)
                    + Math.pow(p.z - this.trackFunc.valueAt(this.xIntervall[0], 0), 2)) < sphere.getDiameter()/2

                    && Math.sqrt(Math.pow(p.x - this.xIntervall[1], 2)
                    + Math.pow(p.z - this.trackFunc.valueAt(this.xIntervall[1], 0), 2)) < sphere.getDiameter()/2;
        } else {
            return cond1;
        }
    }

    // wenn kolidiert mit cond1
    public void performCollision(Sphere sphere) {
        // collPos: vektor von kugel mitte auf kollisions pos (normiert)
        // x = -1 wenn steigung neg
        // x = 1  wenn steigung pos
        // z = -a wenn kugel von oben
        // z = a  wenn kugen von unten

        var p = sphere.getPos();

        var slope = this.trackFunc.derived().valueAt(p.x, 0);
        var isAbove = (p.z - sphere.getDiameter()/2) > this.trackFunc.valueAt(p.x, 0);

        var collPos = new Vec3d( slope > 0 ? 1 : -1, 0, isAbove ? -slope : slope ).norm();
        var parallel = Simulation.GRAV_VEC.sub( collPos.scalarMul( collPos.dot(Simulation.GRAV_VEC) ) );

        sphere.setAccel(sphere.getAccel().add(parallel));
        sphere.mirrorVeloComponent(this.normalAt(p.x, 0));
    }
}
