package entities;

import main.Simulation;
import misc.Utils;
import misc.Vec3d;

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
            Sphere collider = (Sphere)other;

            Vec3d collVec = collider.getPos().sub( this.getPos() ).norm();

            if (Math.acos( this.getVelo().dot(collVec) / this.getVelo().length() ) <= Math.PI/2) {
                // kann geschwindigkeit abgeben (this -> collider)

                Vec3d orthVel1 = collVec.scalarMul( collVec.dot(this.getVelo()) );
                Vec3d orthVel2 = collVec.scalarMul( collVec.dot(collider.getVelo()) );

                Vec3d numerator = orthVel1
                        .scalarMul(this.getMass())
                        .add(orthVel2
                                .scalarMul(collider.getMass()));

                double denominator = this.getMass() + collider.getMass();

                Vec3d v1New = numerator.scalarMul(2/denominator).sub(orthVel1);
                Vec3d v2New = numerator.scalarMul(2/denominator).sub(orthVel2);

                System.out.println("this mov: " + this.getVelo().length());

                this.setVelo(v1New.add( this.getVelo().sub(orthVel1) ));
                collider.setVelo(v2New.add( collider.getVelo().sub(orthVel2) ));
            }
        } else {
            System.err.println("sphere can only collide with spheres atm");
        }
    }

    @Override
    public Entity findColliding(Simulation sim) {
        for (Entity e : sim.getEntitesWithoutSelf(this)) {
            if (e instanceof Sphere) {
                Sphere s = (Sphere)e;

                if(Utils.distance(this.getPos(), s.getPos())
                        <= this.getDiameter()/2 + s.getDiameter()/2) {
                    return s;
                }
            } else if (e instanceof Rectangle) {
                Rectangle r = (Rectangle)e;

                System.err.println("cannot find colliding rectangles yet");
            } else if (e instanceof Point) {
                Point p = (Point)e;

                if(Utils.distance(this.getPos(), p.getPos())
                        < this.getDiameter()/2) {
                    return p;
                }
            }
        }

        return null;
    }
}
