package engine.libs.math;

public class Trig {
    
    public static double cot(double x) {
        return Math.tan(x) == 0 ? 1000000 : 1 / Math.tan(x);
    }

    public static double sec(double x) {
        return Math.cos(x) == 0 ? 1000000 : 1 / Math.cos(x);
    }

    public static double csc(double x) {
        return Math.sin(x) == 0 ? 1000000 : 1 / Math.sin(x);
    }

    public static double degreeToRad(double a) {
        return (Math.TAU * a) / 360;
    }

    public static double radianToDeg(double a) {
        return (360 * a) / Math.TAU;
    }
}
