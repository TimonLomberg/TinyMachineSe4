package entities;

import main.Simulation;
import misc.Utils;
import misc.Vec3d;


public class Rectangle extends Entity {
    private Vec3d dimensions;
    private Vec3d normal;
    private double normalRot;

    public Rectangle(Vec3d pos, Vec3d velo, Vec3d accel, double mass, Vec3d dimensions, Vec3d normal, double normalRot) {
        super(pos, velo, accel, mass);

        this.dimensions = dimensions;
        this.normal = normal;
        this.normalRot = normalRot;
    }

    public Vec3d getDimensions() {
        return dimensions;
    }

    public void setDimensions(Vec3d dimensions) {
        this.dimensions = dimensions;
    }

    public Vec3d getNormal() {
        return normal;
    }

    public void setNormal(Vec3d normal) {
        this.normal = normal;
    }

    public double getNormalRot() {
        return normalRot;
    }

    public void setNormalRot(double normalRot) {
        this.normalRot = normalRot;
    }



    @Override
    public void performCollision(Entity other) {
        System.err.println("Rectangle cannot perform Collision yet");
    }

    @Override
    public Entity findColliding(Simulation sim) {
        for (Entity e : sim.getEntitesWithoutSelf(this)) {
            if (e instanceof Sphere) {
                System.err.println("rectangle cannot collide with sphere yet");

            } else if (e instanceof Rectangle) {
                Rectangle r = (Rectangle)e;

                if(Utils.rectIntersect(this, r)) {
                    return r;
                }
            } else if (e instanceof Point) {
                Point p = (Point)e;

                if(Utils.pointInRect(p.getPos(), this)) {
                    return p;
                }
            }
        }

        return null;
    }
}
