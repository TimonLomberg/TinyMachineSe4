package misc;

import org.jetbrains.annotations.NotNull;

public class Vec3d implements Cloneable {
    public double x;
    public double y;
    public double z;

    public Vec3d() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3d(Vec3d other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    @Override
    public Vec3d clone() {
        return new Vec3d(x, y, z);
    }

    public static Vec3d nullvec() {
        return new Vec3d(0, 0, 0);
    }

    public static Vec3d from(@NotNull Vec3d other) {
        return new Vec3d(other.x, other.y, other.z);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public String toPrettyString() {
        return String.format("(%.2f; %.2f; %.2f)", this.x, this.y, this.z);
    }

    public double lengthSquared() {
        return this.dot(this);
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public Vec3d norm() {
        final double len = this.length();
        if (len != 0) {
            return this.scalarDiv(len);
        } else {
            return Vec3d.nullvec();
        }
    }

    public Vec3d add(@NotNull Vec3d other) {
        Vec3d ret = Vec3d.from(other);

        ret.x += this.x;
        ret.y += this.y;
        ret.z += this.z;

        return ret;
    }

    public Vec3d sub(@NotNull Vec3d other) {
        Vec3d ret = Vec3d.from(this);

        ret.x -= other.x;
        ret.y -= other.y;
        ret.z -= other.z;

        return ret;
    }

    public double dot(@NotNull Vec3d other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vec3d cross(@NotNull Vec3d other) {
        double a = this.y * other.z - this.z * other.y;
        double b = this.z * other.x - this.x * other.z;
        double c = this.x * other.y - this.y * other.x;

        return new Vec3d(a, b, c);
    }

    public Vec3d scalarMul(double lambda) {
        Vec3d ret = Vec3d.from(this);

        ret.x *= lambda;
        ret.y *= lambda;
        ret.z *= lambda;

        return ret;
    }

    public Vec3d scalarDiv(double lambda) {
        Vec3d ret = Vec3d.from(this);

        ret.x /= lambda;
        ret.y /= lambda;
        ret.z /= lambda;

        return ret;
    }

    public Vec3d projectOnto(Vec3d b) {
        // todo testing
        return b.scalarMul( this.dot( b.norm() ) );
    }
}
