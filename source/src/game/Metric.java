package game;

import engine.libs.math.Trig;
import engine.libs.math.Vector;

public class Metric {
    
    public static final double G = 2;
    public static final double M = 1;
    public static final double c = 2;

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
}
