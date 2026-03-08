package game.math;

import engine.libs.math.Matrix;
import engine.libs.math.Vector;
import game.Metric;

public class NewtonSolver {
    
    public static final double TOLERANCE = 10E-10;

    public static Vector step(Vector x, double h) {
        x = Vector.add(x, Vector.mul(Metric.getYPrime(x), h));
        double norm = Metric.getYPrime(x).magnitude();

        while (norm >= TOLERANCE) {
            Matrix inv = Metric.getYDoublePrime(x).inverse();
            x = Vector.sub(x, inv.act(Metric.getYPrime(x)));
            norm = Metric.getYPrime(x).magnitude();
        }

        return x;
    }
}
