package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.util.Pair;
import misc.Drawable;
import misc.Vec3d;

public class Track implements Cloneable, Drawable {
    // private Polynomial3d trackFunc;

    private double slope;
    private double zOffset;

    private double[] xInterval;

    public Track(double c, double s, double[] xInterval) {
        this.setZOffset(c);
        this.setSlope(s);
        this.xInterval = xInterval;
    }

    public Track clone() {
        return new Track(zOffset, slope, xInterval.clone());
    }

    @Override
    public Shape intoShape(Color c) {
        Line line = new Line(this.minBound(), -this.heightAt(this.minBound()),
                this.maxBound(), -this.heightAt(this.maxBound()));
        line.setStroke(c);
        line.setStrokeWidth(0.04);
        
        return line;
    }

    public Pair<Vec3d, Boolean> isColliding(Sphere sphere) {

        final Vec3d leftOfCenter = sphere.getPos().add(new Vec3d(-sphere.getDiameter()/2, 0, 0));
        final Vec3d rightOfCenter = sphere.getPos().add(new Vec3d(sphere.getDiameter()/2, 0, 0));

        if ((leftOfCenter.x < this.minBound() && rightOfCenter.x < this.minBound())
            || (leftOfCenter.x > this.maxBound() && rightOfCenter.x > this.maxBound())) {
            return null;
        }

        final Vec3d trackBeg = new Vec3d(
                this.xInterval[0],
                sphere.getPos().y,
                this.heightAt(this.xInterval[0])
        );

        final Vec3d trackEnd = new Vec3d(
                this.xInterval[1],
                sphere.getPos().y,
                this.heightAt(this.xInterval[1])
        );

        if (rightOfCenter.x > this.minBound() && sphere.getPos().x < this.minBound()) {
            // must be edge collision if any

            if (sphere.getPos().sub(trackBeg).length() <= sphere.getDiameter()/2) {
                return new Pair<>(trackBeg, true);
            } else {
                return null;
            }
        }

        if (leftOfCenter.x < this.maxBound() && sphere.getPos().x > this.maxBound()) {
            // must be edge collision if any

            if (sphere.getPos().sub(trackEnd).length() <= sphere.getDiameter()/2) {
                return new Pair<>(trackEnd, true);
            } else {
                return null;
            }
        }

        final Vec3d trackDir = trackEnd.sub(trackBeg);
        final Vec3d trackBegToSphereCenter = sphere.getPos().sub(trackBeg);

        final Vec3d projected = trackDir.scalarMul(trackBegToSphereCenter.dot(trackDir) / Math.pow(trackDir.length(), 2));

        final Vec3d collPos = trackBeg.add(projected);

        final Vec3d trackToSphere = sphere.getPos().sub(collPos);

        if (trackToSphere.length() <= sphere.getDiameter() / 2) {
            return new Pair<>(collPos, false);
        } else {
            return null;
        }
    }

    // wenn kolidiert mit cond1
    public void performCollision(Sphere sphere, Vec3d collPos, boolean wasEdgyCollision) {
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

        if (wasEdgyCollision) {
            final Vec3d vNorm = sphere.getPos().sub(collPos).norm();
            final Vec3d out = sphere.getVelo().sub( vNorm.scalarMul( sphere.getVelo().dot(vNorm) * 2) );

            sphere.setVelo(out);
        } else {
            final Vec3d sphereCenterToCollPos = collPos.sub(sphere.getPos());

            final Vec3d sphereCenterToTrackInDirOfVel = sphere.getVelo().scalarMul(
                    sphereCenterToCollPos.lengthSquared() / sphere.getVelo().norm().dot(sphereCenterToCollPos));


            final Vec3d orthVelComp = sphereCenterToCollPos.norm().scalarMul(
                    sphere.getVelo().dot(sphereCenterToCollPos.norm())
            );

            // orthogonalen anteil umkehren
            sphere.setVelo(sphere.getVelo().sub(orthVelComp.scalarMul(2)));
        }
    }

    public double heightAt(double x) {
        return (slope() * x) + zOffset();
    }

    public double minBound() {
        return xInterval[0];
    }

    public void setMinBound(double min) {
        this.xInterval[0] = min;
    }

    public double maxBound() {
        return xInterval[1];
    }

    public void setMaxBound(double max) {
        this.xInterval[1] = max;
    }

    public double slope() {
        return slope;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public double zOffset() {
        return zOffset;
    }

    public void setZOffset(double zOffset) {
        this.zOffset = zOffset;
    }
}
