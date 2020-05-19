package main;

<<<<<<< Updated upstream
import java.util.Stack;
=======
import entities.Entity;
import misc.Vec3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
>>>>>>> Stashed changes

public class Simulation {
    public final double GRAV_CONST = -9.807; // in m/s^2
    public final Vec3d GRAV_VEC = new Vec3d(0, 0, GRAV_CONST);  // in m/s^2

<<<<<<< Updated upstream
    private Stack<Actor> actors;


    public Simulation() {
        actors = new Stack<Actor>();
    }

    public Stack<Actor> getActors() {
        return actors;
    }
    public Actor[] getActorsAsArray() {
        Actor[] out = new Actor[actors.size()];
        return actors.toArray(out);
=======
    private ArrayList<Entity> entities;


    public Simulation() {
        entities = new ArrayList<>();
    }

    public Iterable<Entity> getEntities() {
        return this.entities;
    }

    public Iterable<Entity> getEntitesWithoutSelf(Entity self) {
        return this.entities.stream()
                .filter((Entity e) -> e != self)
                .collect(Collectors.toList());
    }

    public void addEntities(Entity... entities) {
        Collections.addAll(this.entities, entities);
    }

    public boolean removeActor(Actor a) {
        return this.entities.remove(a);
    }

    public void tick(double dT) {
        for (var e : this.getEntities()) {
            e.update(this, dT);
        }
>>>>>>> Stashed changes
    }
}
