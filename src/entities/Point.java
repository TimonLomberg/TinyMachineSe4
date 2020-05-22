package entities;

import main.Simulation;
import misc.Utils;
import misc.Vec3d;

import java.util.Optional;

public class Point extends Entity {
    public Point(Vec3d pos, Vec3d velo, Vec3d accel, double mass) {
        super(pos, velo, accel, mass);
    }

    @Override
    public void performCollision(Entity other) {

    }

    @Override
    public Entity findColliding(Simulation sim) {
        for (Entity e : sim.getEntitesWithoutSelf(this)) {
            if (e instanceof Sphere) {
                Sphere s = (Sphere)e;

                if(Utils.distance(s.getPos(), this.getPos())
                        < s.getDiameter()/2) {
                    return s;
                }
            } else if (e instanceof Rectangle) {
                Rectangle r = (Rectangle)e;

                if(Utils.pointInRect(this.getPos(), r)) {
                    return r;
                }
            } else if (e instanceof Point) {
                Point p = (Point)e;

                if(Utils.distance(this.getPos(), p.getPos()) == 0.0) {
                    return p;
                }
            }
        }
        return null;
    }
}
