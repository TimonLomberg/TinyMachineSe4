package main;

import java.util.ArrayList;
import java.util.Collections;

public class Simulation {

    private ArrayList<Actor> actors;


    public Simulation() {
        actors = new ArrayList<>();
    }

    public Iterable<Actor> getActors() {
        return this.actors;
    }

    public void addActors(Actor... actors) {
        Collections.addAll(this.actors, actors);
    }

    public boolean removeActor(Actor a) {
        return this.actors.remove(a);
    }
}
