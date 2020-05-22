package entities;

import jdk.jshell.spi.ExecutionControl;
import main.Simulation;
import misc.Utils;
import misc.Vec3d;

import java.util.Optional;

public class Sphere extends Entity {

    private double diameter;

    public Sphere(Vec3d pos, Vec3d velo, Vec3d accel, double mass, double diameter) {
        super(pos, velo, accel, mass);
        this.diameter = diameter;
    }

    public double getDiameter() {
        return this.diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    @Override
    public void performCollision(Entity other) {
        if (other instanceof Sphere) {
            var collider = (Sphere)other;

            var collVec = collider.getPos().sub( this.getPos() ).norm();

            if (Math.acos( this.getVelo().dot(collVec) / this.getVelo().length() ) <= Math.PI/2) {
                // kann geschwindigkeit abgeben (this -> collider)

                var orthVel1 = collVec.scalarMul( collVec.dot(this.getVelo()) );
                var orthVel2 = collVec.scalarMul( collVec.dot(collider.getVelo()) );

                var numerator = orthVel1
                        .scalarMul(this.getMass())
                        .add(orthVel2
                                .scalarMul(collider.getMass()));

                var denominator = this.getMass() + collider.getMass();

                var v1New = numerator.scalarMul(2/denominator).sub(orthVel1);
                var v2New = numerator.scalarMul(2/denominator).sub(orthVel2);

                System.out.println("this mov: " + this.getVelo().length());

                this.setVelo(v1New.add( this.getVelo().sub(orthVel1) ).scalarMul(0.9)); // Added loss
                collider.setVelo(v2New.add( collider.getVelo().sub(orthVel2) ).scalarMul(0.90));
            }
        } else {
            System.err.println("sphere can only collide with spheres atm");
        }
    }

    @Override
    public Optional<Entity> findColliding(Simulation sim) {
        for (Entity e : sim.getEntitesWithoutSelf(this)) {
            if (e instanceof Sphere) {
                var s = (Sphere)e;

                if(Utils.distance(this.getPos(), s.getPos())
                        <= this.getDiameter()/2 + s.getDiameter()/2) {
                    return Optional.of(s);
                }
            } else if (e instanceof Rectangle) {
                var r = (Rectangle)e;

                System.err.println("cannot find colliding rectangles yet");
            } else if (e instanceof Point) {
                var p = (Point)e;

                if(Utils.distance(this.getPos(), p.getPos())
                        < this.getDiameter()/2) {
                    return Optional.of(p);
                }
            }
        }

        return Optional.empty();
    }
}
