package main;

import misc.Utils;
import misc.Vec3d;

import java.util.ArrayList;


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
    private double mass;  // in g
    private double diameter;  // in mm used only for Spheres


    private double speed;
    private double acceleration;

    public void setPosition(Vec3d position) {
        this.position = position;
    }

    public void setPosition2(Vec3d position2) {
        this.position2 = position2;
    }

    public void setMass(double mass) {
        this.mass = mass;
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

    public double getMass() {
        return this.mass;
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

        var colliders = this.checkForCollidingActors();

        if (colliders.iterator().hasNext()) {
            this.movementVector = this.velocityAfterCollision(colliders);
            this.accelerationVector = new Vec3d(0, 0, 0);
        } else {

            double fr;
            //Vec3d gp = new Vec3d(movementVector).mul();
            Vec3d fg = new Vec3d(0, gravitationVec.length() / 1000 * (mass / 1000), 0);

            position = position.add(
                    movementVector.scalarMul(deltaTick));

            movementVector = movementVector.add(
                    accelerationVector.add(gravitationVec).scalarMul(deltaTick));
        }

        this.speed = movementVector.length();
        this.acceleration = accelerationVector.length();

        return this;
    }


    public Vec3d velocityAfterCollision(Iterable<Actor> colliders) {
        var newVel = new Vec3d(0, 0, 0);

        for (var a : colliders) {
            var mC = a.getMovementComponent();

            var numerator = this.getMovementVector()
                    .scalarMul(this.getMass())
                    .add(mC.getMovementVector()
                            .scalarMul(mC.getMass()));

            var denominator = this.getMass() + mC.getMass();

            newVel = newVel.add( numerator.scalarMul(2/denominator).sub(this.getMovementVector()) );
        }

        return newVel;
    }

    public Iterable<Actor> checkForCollidingActors() {
        var cActors = new ArrayList<Actor>();

<<<<<<< Updated upstream
        for(Actor o : parent.getSimulation().getActors()) {
            switch (o.getObjectType()) {

                // Other Object is Sphere
                case Sphere -> {
                    switch (parent.getObjectType()) {
                        // Other is Sphere + This is Sphere Collision
                        case Sphere -> {
                            if(Utils.distance(this.getPosition(), o.getMovementComponent().getPosition())
                                    <= this.getDiameter()/2 + o.getMovementComponent().getDiameter()/2) {
                                cActors.add(o);
=======
        /*for(Actor o : parent.getSimulation().getActors()) {
            if (o != parent) {
                switch (o.getObjectType()) {

                    // Other Object is Sphere
                    case Sphere -> {
                        switch (parent.getObjectType()) {
                            // Other is Sphere + This is Sphere Collision
                            case Sphere -> {
                                if(Utils.distance(this.getPosition(), o.getMovementComponent().getPosition())
                                        <= this.getDiameter()/2 + o.getMovementComponent().getDiameter()/2) {
                                    cActors.add(o);
                                }
>>>>>>> Stashed changes
                            }
                        }
                        // Other is Sphere + This is Rectangle Collision
                        case Rectangle -> {

                        }
                        // Other is Sphere + This is Point Collision
                        case Point -> {
                            if(Utils.distance(o.getMovementComponent().getPosition(), this.getPosition())
                                    < o.getMovementComponent().getDiameter()/2) {
                                cActors.add(o);
                            }
                        }
                        default -> {}
                    }
                }

                // Other Object is Rectangle
                case Rectangle -> {
                    switch (parent.getObjectType()) {
                        // Other is Rectangle + This is Sphere Collision
                        case Sphere -> {

                        }
                        // Other is Rectangle + This is Rectangle Collision
                        case Rectangle -> {
                            if(Utils.rectIntersect(o, parent)) {
                                cActors.add(o);
                            }
                        }
                        // Other is Rectangle + This is Point Collision
                        case Point -> {
                            if(Utils.pointInRect(this.getPosition(), o)) {
                                cActors.add(o);
                            }
                        }
                    }

                }

                // Other Object is Point
                case Point -> {
                    switch (parent.getObjectType()) {
                        // Other is Point + This is Sphere Collision
                        case Sphere -> {
                            if(Utils.distance(this.getPosition(), o.getMovementComponent().getPosition())
                                    < this.getDiameter()/2) {
                                cActors.add(o);
                            }
                        }
                        // Other is Point + This is Rectangle Collision
                        case Rectangle -> {
                            if(Utils.pointInRect(o.getMovementComponent().getPosition(), parent)) {
                                cActors.add(o);
                            }
                        }
                        // Other is Point + This is Point Collision
                        case Point -> {
                            if(Utils.distance(this.getPosition(), o.getMovementComponent().getPosition()) == 0.0) {
                                cActors.add(o);
                            }
                        }
                    }
                }

                default -> {
                    System.err.println("Actor has invalid collision type of " + o.getObjectType());
                    throw new EnumConstantNotPresentException(CollisionObjectType.class, o.getObjectType().toString());
                }

            }
        }*/

        System.err.println("checkForCollidingActors disabled");
        return cActors;
    }

}
