package entities;

import misc.Polynomial3d;

public class SimpleTrack extends Track {
    double c;
    double s;

    SimpleTrack(double s, double c) {
        super(new Polynomial3d(new double[]{ c, s }, new double[]{}));
        this.c = c;
        this.s = s;
    }

    @Override
    public boolean isColliding(Sphere sphere) {
        var p = sphere.getPos();
        var c = p.z - this.c - this.s * p.x;
        var d = Math.sqrt( Math.pow(this.c, 2) - 1 - Math.pow(this.s, 2) );

        return sphere.getDiameter()/2 > d;
    }
}
