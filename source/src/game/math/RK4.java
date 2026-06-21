package game.math;

import engine.libs.math.Vector;
import game.Metric;

public class RK4 extends Solver {

    public RK4(double h) {
        super(h);
    }

    @Override
    public Vector step(Vector y) {
        Vector k1 = Metric.getYPrime(y);
        Vector k2 = Metric.getYPrime(Vector.add(y, Vector.mul(k1, timeStep / 2)));
        Vector k3 = Metric.getYPrime(Vector.add(y, Vector.mul(k2, timeStep / 2)));
        Vector k4 = Metric.getYPrime(Vector.add(y, Vector.mul(k3, timeStep)));
        Vector p1 = Vector.mul(k2, 2);
        Vector p2 = Vector.mul(k3, 2);
        Vector sum = Vector.mul(Vector.add(k1, Vector.add(p1, Vector.add(p2, k4))), timeStep / 6);
        return Vector.add(y, sum);
    }
}
