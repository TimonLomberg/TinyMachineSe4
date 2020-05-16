package misc;

import main.Vec3d;

import enums.CollisionObjectType;
import exceptions.WrongCollisionObjectTypeException;
import main.Actor;

public final class Utils {

    private Utils(){ throw new UnsupportedOperationException();}


    public static double distance(Vec3d v1, Vec3d v2) {
        return v2.sub(v1).length();
    }

    public static boolean inRange(double value, double min, double max) {
        return value >= Math.min(min, max) && value <= Math.max(min, max);
    }

    public static boolean pointInRect(Vec3d point, Actor rect){
        try{
            if(rect.getObjectType()  == CollisionObjectType.Rectangle) {
                return Utils.inRange(point.x, rect.getMovementComponent().getPosition().x, rect.getMovementComponent().getPosition2().x) &&
                        Utils.inRange(point.y, rect.getMovementComponent().getPosition().y, rect.getMovementComponent().getPosition2().y) &&
                        Utils.inRange(point.z, rect.getMovementComponent().getPosition().z, rect.getMovementComponent().getPosition2().z);
            } else {
                throw new WrongCollisionObjectTypeException(CollisionObjectType.Rectangle, rect.getObjectType());
            }
        } catch (WrongCollisionObjectTypeException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean rangeIntersect(double min0, double max0, double min1, double max1) {
        return Math.max(min0, max0) >= Math.min(min1, max1) &&
                Math.min(min0, max0) <= Math.max(min1, max1);
    }

    public static boolean rectIntersect(Actor r0, Actor r1) {
        try{
            if(r0.getObjectType()  == CollisionObjectType.Rectangle ) {
                if(r1.getObjectType()  == CollisionObjectType.Rectangle) {
                    return rangeIntersect(r0.getMovementComponent().getPosition().x, r0.getMovementComponent().getPosition2().x,
                                    r1.getMovementComponent().getPosition().x, r1.getMovementComponent().getPosition2().x) &&
                            rangeIntersect(r0.getMovementComponent().getPosition().y, r0.getMovementComponent().getPosition2().y,
                                    r1.getMovementComponent().getPosition().y, r1.getMovementComponent().getPosition2().y) &&
                            rangeIntersect(r0.getMovementComponent().getPosition().z, r0.getMovementComponent().getPosition2().z,
                                    r1.getMovementComponent().getPosition().z, r1.getMovementComponent().getPosition2().z);

                } else {
                    throw new WrongCollisionObjectTypeException(CollisionObjectType.Rectangle, r1.getObjectType());
                }

            } else {
                throw new WrongCollisionObjectTypeException(CollisionObjectType.Rectangle, r0.getObjectType());
            }
        } catch (WrongCollisionObjectTypeException e) {
            e.printStackTrace();
            return false;
        }
    }
}
