package main.customActors;

import enums.CollisionObjectType;
import main.Actor;
import main.MovementComponent;
import main.Simulation;
import misc.Vec3d;

public class Marble extends Actor {

    Simulation simulation;

    public Marble(Simulation simulation, double mass, double diameter, Vec3d position, Vec3d movementVector, Vec3d accelerationDirection) {
        super(simulation, CollisionObjectType.Sphere);
        this.simulation = simulation;

        super.setMovementComponent( new MovementComponent(this, position,
                movementVector, accelerationDirection));
        super.getMovementComponent().setMass(mass);
        super.getMovementComponent().setDiameter(diameter);

    }

    public Marble(Simulation simulation, double mass, double diameter, Vec3d position) {
        super(simulation, CollisionObjectType.Sphere);
        this.simulation = simulation;

        super.setMovementComponent(new MovementComponent(this, position, new Vec3d(0,0,0), new Vec3d(0,0,0)));
        super.getMovementComponent().setMass(mass);
        super.getMovementComponent().setDiameter(diameter);

    }

}
