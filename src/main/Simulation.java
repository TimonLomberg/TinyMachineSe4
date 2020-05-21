package main;

import entities.Entity;
import misc.Vec3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Simulation {

    private boolean paused = true;

    public static final double GRAV_CONST = -9.807; // in m/s^2
    public static final Vec3d GRAV_VEC = new Vec3d(0, 0, GRAV_CONST);  // in m/s^2

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

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void addEntities(Entity... entities) {
        Collections.addAll(this.entities, entities);
    }

    public boolean removeEntity(Entity e) {
        return this.entities.remove(e);
    }

    public void tick(double dT) {
        for (var e : this.getEntities()) {
            e.update(this, dT);
        }
    }

    public void clearEntities() {
        entities.clear();
    }
}
