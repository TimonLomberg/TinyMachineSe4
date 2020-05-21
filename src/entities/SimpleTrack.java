package entities;

import misc.Polynomial3d;

public class SimpleTrack extends Track {

    public SimpleTrack(double s, double c) {
        super(new Polynomial3d(new double[]{ c, s }, new double[]{}));
    }

    @Override
    public boolean isColliding(Sphere sphere) {

        var p = sphere.getPos();
        var c = p.z - this.trackFunc.xFactors()[0] - this.trackFunc.xFactors()[1] * p.x;
        var d = Math.sqrt( Math.pow(c, 2) - 1 - Math.pow(this.trackFunc.xFactors()[1], 2) );

        return sphere.getDiameter()/2 > d;
    }
}
