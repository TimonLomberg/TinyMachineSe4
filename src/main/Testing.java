package main;

import entities.Track;
import misc.Vec3d;

public class Testing {
    public static void main(String[] args) {
        Track t = new Track(new Vec3d(1, 0, 2), new Vec3d(2, 0, 3));

        t.setPoints(new Vec3d(1, 0, 2), new Vec3d(2, 0, 4));

        System.out.println("");
    }
}
