package main;

import java.util.ArrayList;
import java.util.Stack;

public class Simulation {

    private ArrayList<Actor> actors;


    public Simulation() {
        actors = new ArrayList<Actor>();
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }
    public Actor[] getActorsAsArray() {
        Actor[] out = new Actor[actors.size()];
        return actors.toArray(out);
    }
}
