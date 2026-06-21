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
        if(y.getSize() == 8) {
            double r = y.val[0];
            double dr = y.val[1];
            double t = y.val[2];
            double dt = y.val[3];
            double h = y.val[4];
            double dh = y.val[5];
            double p = y.val[6];
            double dp = y.val[7];

            double a = r - rs;
            return new Vector(new double[]{
                    dr, -((rs / 2) * a / (r * r * r)) * dt * dt + (rs / 2) / (r * a) * dr * dr + a * dh * dh + a * Math.sin(h) * Math.sin(h) * dp * dp,
                    dt, -rs / (a * r) * dr * dt,
                    dh, -2 / r * dr * dh + Math.sin(h) * Math.cos(h) * dp * dp,
                    dp, -2 / r * dr * dp - 2 * Trig.cot(h) * dh * dp
            });
        } else {
            double r = y.val[0];
            double dr = y.val[1];
            double dt = y.val[2];
            double h = y.val[3];
            double dh = y.val[4];
            double dp = y.val[5];

            return new Vector(new double[]{
                0,0,0,0,0,0,0,0
            });
        }
    }

    public static Matrix getYDoublePrime(Vector y) {
        double r = y.val[0];
        double dr = y.val[1];
        double dt = y.val[2];
        double h = y.val[3];
        double dh = y.val[4];
        double dp = y.val[5];

        return new Matrix(new double[][]{


        }, 6, 6);
    }
}
