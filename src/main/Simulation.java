package main;

import java.util.ArrayList;

public class Simulation {

    private ArrayList<Actor> actors;


    public Simulation() {
        actors = new ArrayList<>();
    }

    public Iterable<Actor> getActors() {
        return this.actors;
    }

    public void addActor(Actor a) {
        this.actors.add(a);
    }

    public boolean removeActor(Actor a) {
        return this.actors.remove(a);
    }
}
