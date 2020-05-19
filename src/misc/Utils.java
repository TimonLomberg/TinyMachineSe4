package misc;

<<<<<<< Updated upstream
import main.Vec3d;

import enums.CollisionObjectType;
import exceptions.WrongCollisionObjectTypeException;
import main.Actor;
=======
import jdk.jshell.spi.ExecutionControl;
import entities.Rectangle;
>>>>>>> Stashed changes

public final class Utils {

    private Utils(){ throw new UnsupportedOperationException();}


    public static double distance(Vec3d v1, Vec3d v2) {
        return v2.sub(v1).length();
    }

    public static boolean inRange(double value, double min, double max) {
        return value >= Math.min(min, max) && value <= Math.max(min, max);
    }

    public static boolean pointInRect(Vec3d point, Rectangle rect) {
        /*return Utils.inRange(point.x, rect.getPos().x, rect.getMovementComponent().getPosition2().x) &&
                Utils.inRange(point.y, rect.getPos().y, rect.getPosition2().y) &&
                Utils.inRange(point.z, rect.getPos().z, rect.getPosition2().z);*/

        System.err.println("point in rect not corrected yet");
        return false;
    }

    public static boolean rangeIntersect(double min0, double max0, double min1, double max1) {
        return Math.max(min0, max0) >= Math.min(min1, max1) &&
                Math.min(min0, max0) <= Math.max(min1, max1);
    }

    public static boolean rectIntersect(Rectangle r0, Rectangle r1) {
        // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        /*return rangeIntersect(r0.getPos().x, r0.getPosition2().x,
                r1.getMovementComponent().getPosition().x, r1.getMovementComponent().getPosition2().x) &&
                rangeIntersect(r0.getMovementComponent().getPosition().y, r0.getMovementComponent().getPosition2().y,
                        r1.getMovementComponent().getPosition().y, r1.getMovementComponent().getPosition2().y) &&
                rangeIntersect(r0.getMovementComponent().getPosition().z, r0.getMovementComponent().getPosition2().z,
                        r1.getMovementComponent().getPosition().z, r1.getMovementComponent().getPosition2().z);*/

        System.err.println("rect intersect not corrected yet");
        return false;
    }
}
