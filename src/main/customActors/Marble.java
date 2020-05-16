package main.customActors;

import com.sun.javafx.geom.Vec3d;
import enums.CollisionObjectType;
import main.Actor;
import main.CollisionComponent;
import main.MovementComponent;
import main.Simulation;

public class Marble extends Actor {

    Simulation simulation;

    public Marble(Simulation simulation, double weight, double diameter, Vec3d position, double speed,
                       double acceleration, Vec3d movementVector, Vec3d accelerationDirection) {
        super(simulation, CollisionObjectType.Sphere);
        this.simulation = simulation;

        super.setMovementComponent( new MovementComponent(this, position, speed, acceleration,
                movementVector, accelerationDirection));
        super.setCollisionComponent(new CollisionComponent(this));
        super.getMovementComponent().setWeight(weight);
        super.getMovementComponent().setDiameter(diameter);

    }

    public Marble(Simulation simulation, double weight, double diameter, Vec3d position) {
        super(simulation, CollisionObjectType.Sphere);
        this.simulation = simulation;

        super.setMovementComponent(new MovementComponent(this, position,0, 0,
                new Vec3d(0,0,0), new Vec3d(0,0,0)));
        super.setCollisionComponent(new CollisionComponent(this));
        super.getMovementComponent().setWeight(weight);
        super.getMovementComponent().setDiameter(diameter);

    }

}
