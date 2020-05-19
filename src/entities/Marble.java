package entities;

import misc.Vec3d;

public class Marble extends Sphere {
    public Marble(Vec3d pos, Vec3d velo, Vec3d accel, double mass, double diameter) {
        super(pos, velo, accel, mass, diameter);
    }
}
