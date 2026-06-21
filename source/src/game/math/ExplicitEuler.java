package game.math;

import engine.libs.math.Vector;
import game.Metric;

public class ExplicitEuler extends Solver {
    
    public ExplicitEuler(double h) {
        super(h);
    }

    @Override
    public Vector step(Vector y) {
        return Vector.add(y, Vector.mul(Metric.getYPrime(y), timeStep));
    }
}
