package entities;

import main.Simulation;
import misc.Vec3d;

import java.util.Optional;

public abstract class Entity {
    protected Vec3d pos;
    protected Vec3d velo;
    protected Vec3d accel;

    protected double mass;

    public Entity(Vec3d pos, Vec3d velo, Vec3d accel, double mass) {
        this.pos = pos;
        this.velo = velo;
        this.accel = accel;
        this.mass = mass;
    }

    public Vec3d getPos() { return this.pos; }
    public Vec3d getVelo() { return this.velo; }
    public Vec3d getAccel() { return this.accel; }

    public void setPos(Vec3d pos) { this.pos = pos; }
    public void setVelo(Vec3d velo) { this.velo = velo; }
    public void setAccel(Vec3d accel) { this.accel = accel; }

    public double getMass() { return this.mass; }
    public void setMass(double mass) { this.mass = mass; }


    public abstract void performCollision(Entity other);
    public abstract Optional<Entity> findColliding(Simulation sim);

    public void update(Simulation sim, double dT) {
        var collider = this.findColliding(sim);

        if (collider.isEmpty()) {
            this.performCollision(collider.get());
            this.setAccel(Vec3d.nullvec());
        }

        this.pos = this.pos.add(
                this.velo.scalarMul(dT));

        this.velo = this.velo.add(
                this.accel.add(sim.GRAV_VEC).scalarMul(dT));
    }
}
