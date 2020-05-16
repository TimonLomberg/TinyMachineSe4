package main;

import com.sun.javafx.geom.Vec3d;

/**
 * Movement Component for Actors
 */
public class MovementComponent {

    private Actor parent;

    private final double gravitation = 9807; // in mm
    private final Vec3d gravitationVec = new Vec3d(0,-9807,0);  // in mm

    private Vec3d position;  // in mm
    private Vec3d position2; // in mm used only for Rectangles
    private Vec3d movementVector;  // in mm
    private Vec3d accelerationVector;  // in mm
    private double weight;  // in g
    private double diameter;  // in mm used only for Spheres


    private double speed;
    private double acceleration;

    public void setPosition(Vec3d position) {
        this.position = position;
    }

    public void setPosition2(Vec3d position2) {
        this.position2 = position2;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }


    public Vec3d getMovementVector() {
        return movementVector;
    }

    public Vec3d getAccelerationVector() {
        return accelerationVector;
    }

    public double getWeight() {
        return weight;
    }

    public double getSpeed() {
        return speed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public Vec3d getPosition() {
        return this.position;
    }
    public Vec3d getPosition2() {
        return this.position2;
    }

    public double getDiameter() {
        return diameter;
    }

    public MovementComponent(Actor parent, Vec3d position, double speed, double acceleration, Vec3d movementVector, Vec3d accelerationVector) {
        this.parent = parent;
        this.speed = speed;
        this.acceleration = acceleration;
        this.movementVector = new Vec3d(movementVector);
        this.accelerationVector = new Vec3d(accelerationVector);
        this.position = new Vec3d(position);
    }



    public MovementComponent update(double deltaTick) {


        double fr;
        //Vec3d gp = new Vec3d(movementVector).mul();
        Vec3d fg = new Vec3d(0, gravitationVec.length() / 1000 * (weight / 1000),0);


        Vec3d newPosition = new Vec3d();

        position.x = position.x + (movementVector.x * deltaTick);
        position.y = position.y + (movementVector.y * deltaTick);
        position.z = position.z + (movementVector.z * deltaTick);

        movementVector.x = movementVector.x + accelerationVector.x * deltaTick;
        movementVector.y = movementVector.y + accelerationVector.y * deltaTick;
        movementVector.z = movementVector.z + accelerationVector.z * deltaTick;

        speed = movementVector.length();
        /*
        movementVector.x = movementVector.x * (speed / movementVector.length());
        movementVector.y = movementVector.y * (speed / movementVector.length());
        movementVector.z = movementVector.z * (speed / movementVector.length());
        */
        return this;
    }

}
