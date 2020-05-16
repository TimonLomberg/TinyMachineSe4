package main;

import enums.CollisionObjectType;

public class Actor {

    private Simulation simulation;

    private MovementComponent movementComponent;
    private CollisionComponent collisionComponent;
    private CollisionObjectType objectType;


    public Simulation getSimulation() {
        return simulation;
    }

    public MovementComponent getMovementComponent() {
        return movementComponent;
    }

    public CollisionComponent getCollisionComponent() {
        return collisionComponent;
    }

    public CollisionObjectType getObjectType() {
        return objectType;
    }

    public void setMovementComponent(MovementComponent movementComponent) {
        this.movementComponent = movementComponent;
    }

    public void setCollisionComponent(CollisionComponent collisionComponent) {
        this.collisionComponent = collisionComponent;
    }

    public void setObjectType(CollisionObjectType objectType) {
        this.objectType = objectType;
    }

    public Actor(Simulation simulation, CollisionObjectType objectType){
        this.simulation = simulation;
        this.objectType = objectType;
        movementComponent  = new MovementComponent(this, new Vec3d(0,0,0),0.0,0.0,
                new Vec3d(0,0,0),new Vec3d(0,0,0));
        collisionComponent = new CollisionComponent(this);
    }

    public Actor(Simulation simulation, CollisionObjectType objectType,
                 MovementComponent movementComponent, CollisionComponent collisionComponent) {
        this.simulation = simulation;
        this.objectType = objectType;
        this.movementComponent = movementComponent;
        this.collisionComponent = collisionComponent;
    }
}
