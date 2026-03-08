package game.rendering;


import java.util.stream.IntStream;

import engine.libs.math.HomogenousCoords;
import engine.libs.math.PolarCoords;
import engine.libs.math.Vector;
import engine.libs.types.Texture;
import engine.libs.types.Color.Color;
import engine.properties.CameraProperty;
import engine.properties.PropertyType;
import engine.properties.renderers.SphereRenderer;
import engine.rendering.Renderer;
import engine.types.Object;
import engine.types.World;
import game.Metric;
import game.math.NewtonSolver;

public class NumericRenderer extends Renderer {
    
    private RelativisticRay[][] rays;

    private static NumericRenderer numericRenderer;
    private NumericRenderer(int width, int height) {
        super(width, height);
        rays = new RelativisticRay[width][height];
    }
    public static NumericRenderer getInstance(int width, int height) {
        if(numericRenderer == null) {
            numericRenderer = new NumericRenderer(width, height);
        }
        return numericRenderer;
    }
    public static NumericRenderer getInstance() {
        return numericRenderer;
    }


    @Override
    public void setup(Object cam, World world) {
        recalcRays(cam, world);
    }

    @Override
    public void update(Object cam, World world) {
        recalcRays(cam, world);
    }

    @Override
    public Texture render(Object cam, World world) {
        Texture tex = new Texture(width, height);

        IntStream.range(0, width).parallel().forEach(x -> {
            IntStream.range(0, height).parallel().forEach(y -> {
                if(!rays[x][y].didHit && rays[x][y].state.val[0] > 0) {
                    Vector rayPos = new Vector(new double[]{rays[x][y].state.val[0], rays[x][y].state.val[4], rays[x][y].state.val[6]});

                    if(rays[x][y].state.val[0] <= Metric.rs || rays[x][y].state.val[0] >= 50) {
                        rays[x][y].didHit = true;
                        rays[x][y].color = new Color(0, 0, 0);
                    } else {
                        for(Object obj : World.getCurrent().getObjects()) {
                            if(!obj.hasProperty(PropertyType.SPHERE_RENDERER)) continue;
                            Vector spherePos = obj.transform().getPosition();
                            double radius = ((SphereRenderer)obj.findProperty(PropertyType.SPHERE_RENDERER)).radius;
                            Color color = ((SphereRenderer)obj.findProperty(PropertyType.SPHERE_RENDERER)).color;

                            Vector relativeRayPos = PolarCoords.toSpherical(Vector.sub(PolarCoords.toCartesian(rayPos), spherePos));
                            double rad = relativeRayPos.val[0];

                            if(rad <= radius) {
                                rays[x][y].didHit = true;

                                Vector prevPos = PolarCoords.toCartesian(new Vector(new double[]{rays[x][y].state.val[0], rays[x][y].state.val[4], rays[x][y].state.val[6]}));
                                Vector relativePrevPos = Vector.sub(prevPos, spherePos);
                                Vector directionVector = Vector.sub(PolarCoords.toCartesian(relativeRayPos), relativePrevPos).normalized(1);

                                double a = directionVector.magnitude() * directionVector.magnitude();
                                double b = 2 * Vector.dot(directionVector, PolarCoords.toCartesian(relativeRayPos));
                                double c = relativePrevPos.magnitude() * relativePrevPos.magnitude() - radius * radius;

                                double d = b * b - 4 * a * c;

                                double dst;
                                
                                if(d < 0) {
                                    continue;
                                } else {

                                    if(d == 0) {
                                        dst = (-b + Math.sqrt(d)) / (2 * a);
                                    } else {
                                        double dst1 = (-b + Math.sqrt(d)) / (2 * a);
                                        double dst2 = (-b - Math.sqrt(d)) / (2 * a);

                                        dst = dst1 < dst2 ? dst1 : dst2;
                                    }
                                }

                                Vector hitPos = Vector.add(PolarCoords.toCartesian(relativeRayPos), Vector.mul(directionVector, dst));
                                Vector hitNormal = hitPos.normalized(1);
                                Vector lightDir = new Vector(new double[]{0, 1, -1}); 
                                double brightness = Vector.dot(hitNormal, lightDir.normalized(1));
                                brightness = brightness >= 0 ? brightness : 0;

                                rays[x][y].color = new Color((int)(color.r * brightness), (int)(color.g * brightness), (int)(color.b * brightness));
                                break;
                            }
                        }

                        double timeStep = 0.04;

                        rays[x][y].previous = rays[x][y].state;

                        //Explicit Euler
                        //rays[x][y].state = Vector.add(rays[x][y].state, Vector.mul(Metric.getYPrime(rays[x][y].state), timeStep));

                        //RK4
                        /*Vector k1 = Metric.getYPrime(rays[x][y].state);
                        Vector k2 = Metric.getYPrime(Vector.add(rays[x][y].state, Vector.mul(k1, timeStep / 2)));
                        Vector k3 = Metric.getYPrime(Vector.add(rays[x][y].state, Vector.mul(k2, timeStep / 2)));
                        Vector k4 = Metric.getYPrime(Vector.add(rays[x][y].state, Vector.mul(k3, timeStep)));
                        Vector p1 = Vector.mul(k2, 2);
                        Vector p2 = Vector.mul(k3, 2);
                        Vector sum = Vector.mul(Vector.add(k1, Vector.add(p1, Vector.add(p2, k4))), timeStep / 6);
                        rays[x][y].state = Vector.add(rays[x][y].state, sum);*/
                        
                        rays[x][y].state = NewtonSolver.step(rays[x][y].state, timeStep);

                        Vector velocity = new Vector(new double[]{
                            rays[x][y].state.val[1],
                            rays[x][y].state.val[3],
                            rays[x][y].state.val[5],
                            rays[x][y].state.val[7]
                        });
                        double speed = velocity.magnitude();

                        if(x == width / 2 && y == height / 2 || true) {
                            System.out.println(velocity.toString());
                        }

                        rays[x][y].state.val[1] *= (Metric.c / speed);
                        rays[x][y].state.val[3] *= (Metric.c / speed);
                        rays[x][y].state.val[5] *= (Metric.c / speed);
                        rays[x][y].state.val[7] *= (Metric.c / speed);
                    }
                }
                tex.setPixelAt(x, y, rays[x][y].color);
            });
        });
        return tex;
    }

    private void recalcRays(Object cam, World world) {
        Vector camPos = cam.transform().getPosition();
        CameraProperty camProp = ((CameraProperty)cam.findProperty(PropertyType.CAMERA));

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Vector screenPoint = new Vector(new double[]{x, y, 1});
                Vector camSpacePoint = HomogenousCoords.toHomCoords(camProp.getProjectionMatrix().inverse().act(screenPoint), 0);
                Vector worldSpacePoint = HomogenousCoords.toNormCoords(camProp.getLocalToWorldMatrix().act(camSpacePoint));
                Vector direction = Vector.sub(worldSpacePoint, camPos).normalized(1);
            
                Vector sphericalPos = PolarCoords.toSpherical(camPos);
                Vector sphericalDirection = PolarCoords.toSphericalAt(sphericalPos, direction).normalized(Metric.c);

                Vector stateVector = new Vector(new double[]{
                    /*RADIAL   */   sphericalPos.val[0], sphericalDirection.val[0],
                    /*TEMPORAL */   0, 0,
                    /*POLAR    */   sphericalPos.val[1], sphericalDirection.val[1],
                    /*AZIMUTHAL*/   sphericalPos.val[2], sphericalDirection.val[2]
                });

                rays[x][y] = new RelativisticRay(stateVector);
            }
        }
    }
}
