package entities;

import main.Simulation;
import misc.Polynomial3d;
import misc.Vec3d;

import java.util.Optional;

public class Track {
    private Polynomial3d trackFunc;

    public Track(Polynomial3d trackFunc) {
        this.trackFunc = trackFunc;
    }

    public Vec3d normalAt(double x, double y) {
        var v1 = new Vec3d(1, 0, this.trackFunc.derivedByXIgnoreY().at(x, 0));
        var v2 = new Vec3d(0, 1, this.trackFunc.derivedByYIgnoreX().at(0, y));

        return v1.cross(v2).norm();
    }
    
}
