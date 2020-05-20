package entities;

import main.Simulation;
import misc.Vec3d;

import java.util.Optional;

public class Track extends Entity{

    double[] trackFunction = new double[3];

    public Track(Vec3d pos, Vec3d velo, Vec3d accel, double mass) {
        super(pos, velo, accel, mass);
    }

    @Override
    public void performCollision(Entity other) {

    }

    @Override
    public Optional<Entity> findColliding(Simulation sim) {




        return Optional.empty();
    }
}
