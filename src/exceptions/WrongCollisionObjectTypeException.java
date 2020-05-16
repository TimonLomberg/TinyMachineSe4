package exceptions;


import enums.CollisionObjectType;


public final class WrongCollisionObjectTypeException extends Error {



    public WrongCollisionObjectTypeException(CollisionObjectType expectedType, CollisionObjectType actualType) {
        System.err.println("Err: Expected object type " + expectedType.toString() + " got type " + actualType.toString() + ".");
        this.printStackTrace();
    }
}
