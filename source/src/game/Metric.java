package game;

import engine.libs.math.Matrix;
import engine.libs.math.Trig;
import engine.libs.math.Vector;

public class Metric {
    
    public static final double G = 1;
    public static final double M = 1;
    public static final double c = 1;

    public static final double rs = (2 * G * M) / (c * c);

    public static Vector getYPrime(Vector y) {

        double a = y.val[0] - rs;
        return new Vector(new double[]{
                y.val[1], -((rs / 2) * a / (y.val[0] * y.val[0] * y.val[0])) * y.val[3] * y.val[3] + (rs / 2) / (y.val[0] * a) * y.val[1] * y.val[1] + a * y.val[5] * y.val[5] + a * Math.sin(y.val[4]) * Math.sin(y.val[4]) * y.val[7] * y.val[7],
                y.val[3], -rs / (a * y.val[0]) * y.val[1] * y.val[3],
                y.val[5], -2 / y.val[0] * y.val[1] * y.val[5] + Math.sin(y.val[4]) * Math.cos(y.val[4]) * y.val[7] * y.val[7],
                y.val[7], -2 / y.val[0] * y.val[1] * y.val[7] - 2 * Trig.cot(y.val[4]) * y.val[5] * y.val[7]
        });
    }

    public static Matrix getYDoublePrime(Vector y) {
        double r = y.val[0];
        double dr = y.val[1];
        //double t = y.val[2];
        double dt = y.val[3];
        double h = y.val[4];
        double dh = y.val[5];
        //double p = y.val[6];
        double dp = y.val[7];

        return new Matrix(new double[][]{
            {0, 1, 0, 0, 0, 0, 0, 0},
            {
                dp * dp * Math.pow(Math.sin(h), 2) + dh * dh - (rs * (2 * r - 3 * rs)) / (2 * Math.pow(r, 4)) * y.val[3] * y.val[3]
                    + (rs * (rs - 2 * r)) / (2 * r * r * Math.pow(r - rs, 2)) * dr * dr,
                rs / (r * r - r * rs) * dr,
                0,
                (rs * (r - rs)) / (Math.pow(r, 3)) * dt,
                2 * dp * dp * Math.sin(h) * Math.cos(h) * (r - rs),
                2 * dh * (r - rs),
                0,
                2 * dp * Math.pow(Math.sin(h), 2) * (r -rs)
            },
            {0, 0, 0, 1, 0, 0, 0, 0},
            {
                (rs * (2 * r - rs)) / (r * r * Math.pow(r - rs, 2)) * dr * dt,
                -rs / (r * (r - rs)) * dt,
                0,
                -rs / (r * (r - rs)) * dr,
                0,
                0,
                0,
                0

            },
            {0, 0, 0, 0, 0, 1, 0, 0},
            {
                2 / (r * r) * dr * dh,
                -2 / r * dh,
                0,
                0,
                dp * dp * (Math.pow(Math.cos(h), 2) - Math.pow(Math.sin(h), 2)),
                -2 / r * dr,
                0,
                2 * dp * Math.sin(h) * Math.cos(h) 
            },
            {0, 0, 0, 0, 0, 0, 0, 1},
            {
                2 / (r * r) * dr * dp,
                -2 / r * dp,
                0,
                0,
                2 * dh * dp * Math.pow(Trig.csc(h), 2),
                -2 * dp * Trig.cot(h),
                0,
                -(2 * (dh * r * Trig.cot(h) + dr)) / r
            }
        }, 8, 8);
    }
}
