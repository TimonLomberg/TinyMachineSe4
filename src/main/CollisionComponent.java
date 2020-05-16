package main;


import enums.CollisionObjectType;
import misc.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;


public class CollisionComponent {


    Actor parent;

    public void setParent(@NotNull Actor parent) {
        this.parent = parent;
    }



    public CollisionComponent(@NotNull Actor parent) {
        this.parent = parent;
    }



    public Actor[] checkForCollidingActors() {
        Stack<Actor> cActors= new Stack<Actor>();
        for(Actor o : parent.getSimulation().getActors()) {
            switch (o.getObjectType()) {

                // Other Object is Sphere
                case Sphere -> {
                    switch (parent.getObjectType()) {
                        // Other is Sphere + This is Sphere Collision
                        case Sphere -> {
                            if(Utils.distance(parent.getMovementComponent().getPosition(), o.getMovementComponent().getPosition())
                                    <= parent.getMovementComponent().getDiameter()/2 + o.getMovementComponent().getDiameter()/2) {
                                cActors.add(o);
                            }
                        }
                        // Other is Sphere + This is Rectangle Collision
                        case Rectangle -> {

                        }
                        // Other is Sphere + This is Point Collision
                        case Point -> {
                            if(Utils.distance(o.getMovementComponent().getPosition(), parent.getMovementComponent().getPosition())
                                    < o.getMovementComponent().getDiameter()/2) {
                                cActors.add(o);
                            }
                        }
                        default -> {}
                    }
                }

                // Other Object is Rectangle
                case Rectangle -> {
                    switch (parent.getObjectType()) {
                        // Other is Rectangle + This is Sphere Collision
                        case Sphere -> {

                        }
                        // Other is Rectangle + This is Rectangle Collision
                        case Rectangle -> {
                            if(Utils.rectIntersect(o, parent)) {
                                cActors.add(o);
                            }
                        }
                        // Other is Rectangle + This is Point Collision
                        case Point -> {
                            if(Utils.pointInRect(parent.getMovementComponent().getPosition(), o)) {
                                cActors.add(o);
                            }
                        }
                    }

                }

                // Other Object is Point
                case Point -> {
                    switch (parent.getObjectType()) {
                        // Other is Point + This is Sphere Collision
                        case Sphere -> {
                            if(Utils.distance(parent.getMovementComponent().getPosition(), o.getMovementComponent().getPosition())
                                    < parent.getMovementComponent().getDiameter()/2) {
                                cActors.add(o);
                            }
                        }
                        // Other is Point + This is Rectangle Collision
                        case Rectangle -> {
                            if(Utils.pointInRect(o.getMovementComponent().getPosition(), parent)) {
                                cActors.add(o);
                            }
                        }
                        // Other is Point + This is Point Collision
                        case Point -> {
                            if(Utils.distance(parent.getMovementComponent().getPosition(), o.getMovementComponent().getPosition()) == 0.0) {
                                cActors.add(o);
                            }
                        }
                    }
                }



                default -> {
                    System.err.println("Actor has invalid collision type of " + o.getObjectType());
                    throw new EnumConstantNotPresentException(CollisionObjectType.class, o.getObjectType().toString());
                }

            }
        }
        Actor[] out = new Actor[cActors.size()];
        return cActors.toArray(out);
    }

}
