package game;

import engine.libs.math.Vector;
import engine.libs.math.Matrix;

public class HamiltonTest {

   public static Vector f(Vector x){

       Vector y = new Vector(new double[]{
	   Math.exp(x.val[0]) * x.val[1] * x.val[1],
	   x.val[0] * x.val[0] + x.val[1] * x.val[1] - 1
       });

       return y;
   }

   public static Matrix fPrime(Vector x) {

       Matrix y = new Matrix(new double[][]{
	    {Math.exp(x.val[0]) * x.val[1] * x.val[1], Math.exp(x.val[0]) * 2 * x.val[1]},
	    {2 * x.val[0], 2 * x.val[1]}
       }, 2, 2);

       return y;
   }

   public static Vector nonLinearSolverStep(Vector x) {


       Matrix inv = fPrime(x).inverse();
       x = Vector.sub(x, inv.act(f(x)));

       return x;
   }
}
