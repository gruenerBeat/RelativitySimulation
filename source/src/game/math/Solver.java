package game.math;

import engine.libs.math.Vector;

public abstract class Solver {
    
    public double timeStep;

    public Solver(double h) {
        timeStep = h;
    }

    public abstract Vector step(Vector y);
}
