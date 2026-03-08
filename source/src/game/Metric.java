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
        return new Matrix(new double[][]{
            {0, 1, 0, 0, 0, 0, 0, 0},
            {
                M * y.val[3] * y.val[3] * (2 * y.val[0] - 6 * M) / Math.pow(y.val[0], 4)
                + 2 * (2 * (y.val[0] - M)) * y.val[1] * y.val[1] / (y.val[0] * y.val[0] * (y.val[0] - 2 * M))
                + y.val[5] * y.val[5] + Math.pow(Math.sin(y.val[4]), 2) * y.val[7] * y.val[7],
                2 * M * y.val[1] / (y.val[0] * (y.val[0] - 2 * M)), 0,
                -2 * M * (y.val[0] - 2 * M) / Math.pow(y.val[0], 3),
                2 * (y.val[0] - 2 * M) * Math.sin(y.val[4]) * y.val[7] * y.val[7] * Math.cos(y.val[4]),
                2 * (y.val[0] - 2 * M) * y.val[5], 0,
                2 * (y.val[0] - 2 * M) * Math.pow(Math.sin(y.val[4]), 2) * y.val[7]
            },
            {0, 0, 0, 1, 0, 0, 0, 0},
            {
                4 * (M - y.val[0]) * y.val[1] * y.val[3] / (y.val[0] * y.val[0] * (y.val[0] - 2 * M)),
                -2 * M * y.val[3] / (y.val[0] * (y.val[0] - 2 * M)), 0,
                -2 * M * y.val[1] / (y.val[0] * (y.val[0] - 2 * M)), 0, 0, 0, 0
            },
            {0, 0, 0, 0, 0, 1, 0, 0},
            {
                2 * y.val[1] * y.val[5] / (y.val[0] * y.val[0]),
                -2 * y.val[5] / y.val[0], 0, 0,
                (Math.pow(Math.cos(y.val[4]), 2) - Math.pow(Math.sin(y.val[4]), 2)) * y.val[7] * y.val[7],
                -2 * y.val[1] / y.val[0], 0,
                2 * Math.sin(y.val[4]) * Math.cos(y.val[4]) * y.val[5]
            },
            {0, 0, 0, 0, 0, 0, 0, 1},
            {
                -2 * y.val[1] * y.val[7] / (y.val[0] * y.val[0]),
                -2 * y.val[7] / y.val[0], 0, 0,
                2 * (1 + Math.pow(Trig.cot(y.val[4]), 2)) * y.val[5] * y.val[7],
                -2 * Trig.cot(y.val[4]) * y.val[7], 0,
                -2 * Trig.cot(y.val[4]) * y.val[5],
            }
        }, 8, 8);
    }
}
