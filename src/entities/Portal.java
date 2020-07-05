package entities;

import misc.Vec3d;

public class Portal extends Track {
    private Vec3d outgoing;
    private Portal otherEnd;

    public Portal(Vec3d start, Vec3d end, Vec3d outgoing) {
        super(start, end);
        this.outgoing = outgoing;
    }

    @Override
    public void performCollision(Sphere sphere, Vec3d _collPos, boolean _wasEdgyCollision) {
        if (otherEnd != null) {
            sphere.setVelo( otherEnd.outgoing.scalarMul( sphere.getVelo().length() ) );

            final Vec3d trackDir = otherEnd.endPoint().sub(otherEnd.startPoint());
            final Vec3d trackNormal = trackDir.cross(new Vec3d(0, 1, 0));

            sphere.setPos(
                    otherEnd.startPoint().add(
                            otherEnd.endPoint()
                                .sub(otherEnd.startPoint())
                                .scalarDiv(2)
                                .add(otherEnd.outgoing.projectOnto(trackNormal).norm().scalarMul(sphere.getDiameter()/2))
                    )
            );
        }
    }

    public Vec3d getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(Vec3d outgoing) {
        this.outgoing = outgoing;
    }

    public Portal getOtherEnd() {
        return otherEnd;
    }

    public void setOtherEnd(Portal otherEnd) {
        this.otherEnd = otherEnd;
    }
}
