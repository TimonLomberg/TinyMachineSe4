package main;

import java.util.Stack;

public class Simulation {

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
    }
}
