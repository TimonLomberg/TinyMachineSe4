package entities;

import main.Simulation;
import misc.Polynomial3d;
import misc.Vec3d;

public class SimpleTrack extends Track implements Cloneable{

    public SimpleTrack(double c, double s, double[] xInterval) {
        super(new Polynomial3d(new double[]{ c, s }, new double[]{}), xInterval);
    }

    private SimpleTrack(Polynomial3d poly, double[] xInterval) {
        super(poly, xInterval);
    }

    @Override
    public SimpleTrack clone() throws CloneNotSupportedException {
        return new SimpleTrack(super.trackFunc, super.xIntervall);
    }

    @Override
    public Vec3d isColliding(Sphere sphere) {


        /*double slope = this.trackFunc.xFactors()[1];

        Vec3d p = sphere.getPos();
        double c = p.z - this.trackFunc.valueAt(p.x, 0);
        double d = this.normalAt(p.x, 0).dot(new Vec3d(0,0,1)) * c;

        boolean cond1 = sphere.getDiameter()/2 > d && d > 0;

        double add = d / Math.sqrt(1 + Math.pow(slope, 2));
        double[] newIntervall = new double[]{ this.xIntervall[0] + add, this.xIntervall[1] + add };

        // außerhalb bahn intervall (könnte mit ecke kollidieren)
        if (p.x < newIntervall[0] || p.x > newIntervall[1]) {
            return cond1
                    && Math.sqrt(Math.pow(p.x - this.xIntervall[0], 2)
                    + Math.pow(p.z - this.trackFunc.valueAt(this.xIntervall[0], 0), 2)) < sphere.getDiameter()/2

                    && Math.sqrt(Math.pow(p.x - this.xIntervall[1], 2)
                    + Math.pow(p.z - this.trackFunc.valueAt(this.xIntervall[1], 0), 2)) < sphere.getDiameter()/2;

        } else {
            return cond1;
        }*/


        // to add collision mit seiten

        final Vec3d trackBeg = new Vec3d(
                this.xIntervall[0],
                0,
                this.trackFunc.valueAt(this.xIntervall[0], 0)
        );

        final Vec3d trackEnd = new Vec3d(
                this.xIntervall[1],
                0,
                this.trackFunc.valueAt(this.xIntervall[1], 0)
        );

        final Vec3d trackDir = trackEnd.sub(trackBeg);
        final Vec3d trackBegToSphereCenter = sphere.getPos().sub(trackBeg);

        final Vec3d projected = trackDir.scalarMul(trackBegToSphereCenter.dot(trackDir) / Math.pow(trackDir.length(), 2));

        final Vec3d trackToSphere = sphere.getPos().sub( trackBeg.add(projected) );

        if (trackToSphere.length() <= sphere.getDiameter() / 2) {
            return trackBeg.add(projected);
        } else {
            return null;
        }
    }

    // wenn kolidiert mit cond1
    public void performCollision(Sphere sphere, Vec3d collPos) {
        /*Vec3d p = sphere.getPos();

        double slope = this.trackFunc.derived().valueAt(p.x, 0);
        boolean isAbove = (p.z - sphere.getDiameter()/2) > this.trackFunc.valueAt(p.x, 0);

        Vec3d collPos = new Vec3d( slope > 0 ? 1 : -1, 0, isAbove ? -slope : slope ).norm();
        Vec3d parallel = Simulation.GRAV_VEC.sub( collPos.scalarMul( collPos.dot(Simulation.GRAV_VEC) ) );


        //TODO: Reibung fehlt noch


        sphere.setAccel(parallel);

        sphere.mirrorVeloComponent(this.normalAt(p.x, 0), 0.08);*/



        /* correct for sphere possibly being inside track */

        /* calculate correnction factor */
        final Vec3d trackToSphere = sphere.getPos().sub(collPos);
        final double correction = (sphere.getDiameter()/2) - sphere.getPos().sub(collPos).length();

        // set correct position
        sphere.setPos(sphere.getPos().add(trackToSphere.norm().scalarMul(correction)));


        // 'korregierter' Betrag ||velo|| der Geschwindigkeit
        // mit  accel = (current_velo^2 - actual_velo^2) / 2traveled_dist gelöst für start_velo
        // also actual_velo = sqrt( current_velo^2 - 2 * accel * traveled_dist )
        final double shouldBeVel = Math.sqrt( Math.pow(sphere.getVelo().length(), 2) - 2 * sphere.getAccel().length() * correction );

        sphere.setVelo(sphere.getVelo().norm().scalarMul(shouldBeVel));


        /* do actual collision calculations */
        final Vec3d sphereCenterToCollPos = collPos.sub(sphere.getPos());

        final Vec3d sphereCenterToTrackInDirOfVel = sphere.getVelo().scalarMul(
                sphereCenterToCollPos.lengthSquared() / sphere.getVelo().norm().dot(sphereCenterToCollPos));


        final Vec3d orthVelComp = sphereCenterToCollPos.norm().scalarMul(
                sphere.getVelo().dot(sphereCenterToCollPos.norm())
        );


        // orthogonalen anteil umkehren
        sphere.setVelo(sphere.getVelo().sub(orthVelComp.scalarMul(2)));


        System.out.print("");
    }
}
