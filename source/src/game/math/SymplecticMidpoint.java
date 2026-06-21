package game.math;

import engine.libs.math.Matrix;
import engine.libs.math.Vector;
import game.Metric;

public class SymplecticMidpoint extends Solver {

    public int maxSteps;
    public double tolerance;

    public SymplecticMidpoint(double h, int maxSteps, double tolerance) {
        this.tolerance = tolerance;
        this.maxSteps = maxSteps;
        super(h);
    }

    @Override
    public Vector step(Vector y) {

        Vector y0 = y;
        Vector y1 = y;
        Vector y2 = y;

        Matrix id6x6 = new Matrix(new double[][] {
            {1, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 1},
        }, 6, 6);

        int i = 0;
        while (true && i < maxSteps) {
            Vector phi = Vector.sub(y1, Vector.sub(y0, Vector.mul(Metric.getYPrime(Vector.mul(Vector.add(y1, y0), 0.5)), timeStep)));
            if(phi.magnitude() < tolerance) {
                break;
            }

            Matrix phi_ = Matrix.sub(id6x6, Metric.getYDoublePrime(Vector.mul(Vector.add(y1, y0), 0.5)).multiply(0.5 * timeStep));

            y2 = Vector.sub(y1, phi_.inverse().act(phi));
            y0 = y1;
            y1 = y2;
        }
        y = y2;
        return y;
    }
}
