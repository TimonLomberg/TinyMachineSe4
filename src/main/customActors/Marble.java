package main.customActors;

import enums.CollisionObjectType;
import main.Actor;
import main.MovementComponent;
import main.Simulation;
import misc.Vec3d;

public class Marble extends Actor {

    Simulation simulation;

    public Marble(Simulation simulation, double weight, double diameter, Vec3d position, double speed,
                       double acceleration, Vec3d movementVector, Vec3d accelerationDirection) {
        super(simulation, CollisionObjectType.Sphere);
        this.simulation = simulation;

        super.setMovementComponent( new MovementComponent(this, position, speed, acceleration,
                movementVector, accelerationDirection));
        super.getMovementComponent().setMass(weight);
        super.getMovementComponent().setDiameter(diameter);

    }

    public Marble(Simulation simulation, double weight, double diameter, Vec3d position) {
        super(simulation, CollisionObjectType.Sphere);
        this.simulation = simulation;

        super.setMovementComponent(new MovementComponent(this, position,0, 0,
                new Vec3d(0,0,0), new Vec3d(0,0,0)));
        super.getMovementComponent().setMass(weight);
        super.getMovementComponent().setDiameter(diameter);

    }

}
