package game.rendering;


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

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(!rays[x][y].didHit) {
                    Vector rayPos = new Vector(new double[]{rays[x][y].state.val[0], rays[x][y].state.val[4], rays[x][y].state.val[6]});

                    if(rays[x][y].state.val[0] <= Metric.rs || rays[x][y].state.val[0] >= 50) {
                        rays[x][y].didHit = true;
                        rays[x][y].color = new Color(0, 0, 0);
                        continue;
                    }

                    for(Object obj : World.getCurrent().getObjects()) {
                        if(!obj.hasProperty(PropertyType.SPHERE_RENDERER)) continue;
                        Vector spherePos = obj.transform().getPosition();
                        double radius = ((SphereRenderer)obj.findProperty(PropertyType.SPHERE_RENDERER)).radius;
                        Color color = ((SphereRenderer)obj.findProperty(PropertyType.SPHERE_RENDERER)).color;

                        Vector relativeRayPos = PolarCoords.toSpherical(Vector.sub(PolarCoords.toCartesian(rayPos), spherePos));
                        double rad = relativeRayPos.val[0];

                        if(rad <= radius) {
                            rays[x][y].didHit = true;
                            rays[x][y].color = color;
                            break;
                        }
                    }

                    double timeStep = 0.04;

                    Vector k1 = Metric.getYPrime(rays[x][y].state);
                    Vector k2 = Metric.getYPrime(Vector.add(rays[x][y].state, Vector.mul(k1, timeStep / 2)));
                    Vector k3 = Metric.getYPrime(Vector.add(rays[x][y].state, Vector.mul(k2, timeStep / 2)));
                    Vector k4 = Metric.getYPrime(Vector.add(rays[x][y].state, Vector.mul(k3, timeStep)));
                    Vector p1 = Vector.mul(k2, 2);
                    Vector p2 = Vector.mul(k3, 2);
                    Vector sum = Vector.mul(Vector.add(k1, Vector.add(p1, Vector.add(p2, k4))), timeStep / 6);
                    rays[x][y].state = Vector.add(rays[x][y].state, sum);

                    Vector velocity = new Vector(new double[]{
                        rays[x][y].state.val[1],
                        rays[x][y].state.val[3],
                        rays[x][y].state.val[5],
                        rays[x][y].state.val[7]
                    });
                    double speed = velocity.magnitude();

                    rays[x][y].state.val[1] *= (Metric.c / speed);
                    rays[x][y].state.val[3] *= (Metric.c / speed);
                    rays[x][y].state.val[5] *= (Metric.c / speed);
                    rays[x][y].state.val[7] *= (Metric.c / speed);
                }

                tex.setPixelAt(x, y, rays[x][y].color);
            }
        }
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
