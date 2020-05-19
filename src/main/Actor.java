package main;

import enums.CollisionObjectType;
import misc.Vec3d;

public class Actor {

    private Simulation simulation;

    private MovementComponent movementComponent;
    private CollisionObjectType objectType;


    public Simulation getSimulation() {
        return simulation;
    }

    public MovementComponent getMovementComponent() {
        return movementComponent;
    }

    public CollisionObjectType getObjectType() {
        return objectType;
    }

    public void setMovementComponent(MovementComponent movementComponent) {
        this.movementComponent = movementComponent;
    }

    public void setObjectType(CollisionObjectType objectType) {
        this.objectType = objectType;
    }

    public Actor(Simulation simulation, CollisionObjectType objectType) {
        this.simulation = simulation;
        this.objectType = objectType;
        movementComponent = new MovementComponent(this, new Vec3d(0, 0, 0),
                new Vec3d(0, 0, 0), new Vec3d(0, 0, 0));
    }

    public Actor(Simulation simulation, CollisionObjectType objectType,
                 MovementComponent movementComponent) {
        this.simulation = simulation;
        this.objectType = objectType;
        this.movementComponent = movementComponent;
    }
}
