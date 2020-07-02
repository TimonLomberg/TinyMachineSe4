package entities;

import main.Simulation;
import misc.Vec3d;

import java.util.Optional;

public abstract class Entity {
    protected Vec3d pos;
    protected Vec3d velo;
    protected Vec3d accel;

    protected double mass;
    protected double gravMul = 1;

    public Entity(Vec3d pos, Vec3d velo, Vec3d accel, double mass) {
        this.pos = pos;
        this.velo = velo;
        this.accel = accel;
        this.mass = mass;



    }

    public Vec3d getPos() { return this.pos; }
    public Vec3d getVelo() { return this.velo; }
    public Vec3d getAccel() { return this.accel; }
    public double getGravMul() {return gravMul; }

    public void setGravMul(double gravMul) {this.gravMul = gravMul; }
    public void setPos(Vec3d pos) { this.pos = pos; }
    public void setVelo(Vec3d velo) { this.velo = velo; }
    public void setAccel(Vec3d accel) { this.accel = accel; }

    public double getMass() { return this.mass; }
    public void setMass(double mass) { this.mass = mass; }


    public void mirrorVeloComponent(Vec3d normVec, double loss) {
        this.velo = this.velo.add( normVec.scalarMul( this.velo.dot(normVec) * (-2 + loss)  ) );
    }

    public abstract void performCollision(Entity other);
    public abstract Entity findColliding(Simulation sim);

    public void update(Simulation sim, double dT) {
        Entity collider = this.findColliding(sim);

        if (collider != null) {
            this.performCollision(collider);
            this.setAccel(Vec3d.nullvec());
        }

        this.pos = this.pos.add(
                this.velo.scalarMul(dT)).add(this.accel.scalarMul(dT*dT/2));

        this.accel = Simulation.GRAV_VEC.scalarMul(gravMul);

        this.velo = this.velo.add(this.accel.scalarMul(dT));
    }
}
