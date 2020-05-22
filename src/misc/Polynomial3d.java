package misc;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.StringJoiner;

public class Polynomial3d {
    private double[] xs;
    private double[] ys;

    private Polynomial3d derived = null;

    public Polynomial3d(@NotNull double[] xs, @NotNull double[] ys) {
        this.xs = xs;
        this.ys = ys;
    }

    public double[] xFactors() {
        return this.xs;
    }

    public double[] yFactors() {
        return this.ys;
    }

    public double valueAt(double x, double y) {
        double sum = 0.0;

        for (int i = 0; i < this.xs.length; ++i) {
            sum += xs[i] * Math.pow(x, i);
        }

        for (int i = 0; i < this.ys.length; ++i) {
            sum += ys[i] * Math.pow(y, i);
        }

        return sum;
    }

    public Polynomial3d derived() {
        if (this.derived == null) {
            double[] newXs = new double[Math.max(0, this.xs.length-1)];
            double[] newYs = new double[Math.max(0, this.ys.length-1)];

            for (int i = 1; i < this.xs.length; ++i) {
                newXs[i-1] = this.xs[i] * i;
            }

            for (int i = 1; i < this.ys.length; ++i) {
                newYs[i-1] = this.ys[i] * i;
            }

            this.derived = new Polynomial3d(newXs, newYs);
        }

        return this.derived;
    }

    public Polynomial3d derivedByXIgnoreY() {
        double[] newXs = new double[Math.max(0, this.xs.length-1)];

        for (int i = 1; i < this.xs.length; ++i) {
            newXs[i-1] = this.xs[i] * i;
        }

        return new Polynomial3d(newXs, new double[]{});
    }

    public Polynomial3d derivedByYIgnoreX() {
        double[] newYs = new double[Math.max(0, this.ys.length-1)];

        for (int i = 1; i < this.ys.length; ++i) {
            newYs[i-1] = this.ys[i] * i;
        }

        return new Polynomial3d(new double[]{}, newYs);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" + ");

        for (int i = (xs.length - 1); i >= 0; --i) {
            joiner.add("" + this.xs[i] + "x^" + i);
        }

        for (int i = (ys.length - 1); i >= 0; --i) {
            joiner.add("" + this.ys[i] + "y^" + i);
        }

        return joiner.toString();
    }

}
