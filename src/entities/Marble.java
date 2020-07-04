package entities;

import misc.Vec3d;

public class Marble extends Sphere implements Cloneable{
    public Marble(Vec3d pos, Vec3d velo, Vec3d accel, double mass, double diameter) {
        super(pos, velo, accel, mass, diameter);
    }
    public Marble(Vec3d pos, double mass, double diameter) {
        super(pos, new Vec3d(0,0,0), new Vec3d(0,0,0), mass, diameter);
    }
    public Marble(double mass, double diameter) {
        super(new Vec3d(0,0,0), new Vec3d(0,0,0), new Vec3d(0,0,0), mass, diameter);
    }

    @Override
    public Marble clone(){
        return new Marble(super.pos, super.velo, super.accel, super.mass, super.getDiameter());
    }
}
