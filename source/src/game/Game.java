package game;
 
import engine.libs.math.Vector;
import engine.libs.types.Color.Color;
import engine.logic.GameRegister;
import engine.objects.Camera;
import engine.objects.Empty;
import engine.properties.CameraProperty;
import engine.properties.PropertyType;
import engine.properties.renderers.SphereRenderer;
import engine.rendering.RenderType;
import engine.types.GameInitializer;
import engine.types.Object;
import engine.types.World;

public class Game extends GameRegister {
    
    @Override
    public GameInitializer register() {
        GameInitializer init = new GameInitializer();

        init.name = "Relativity Simulation";
        init.rt = RenderType.NUMERIC;
        init.fov = 90;
        init.screenWidth = 1280;
        init.screenHeight = 720;
        init.targetFPS = 20;
        init.targetTPS = 20;

        return init;
    }

    @Override
    public void init() {

        Camera.getCurrent().transform().setPosition(new Vector(new double[]{5, 0, 0}));
        ((CameraProperty)Camera.getCurrent().findProperty(PropertyType.CAMERA)).lookAt(new Vector(new double[]{0, 0, 0}));

        Object s1 = new Empty("Sphere1");
        s1.addProperty(new SphereRenderer(3, new Color(255, 0, 0)));
        s1.transform().setPosition(new Vector(new double[]{-2, 0, 7}));
        World.getCurrent().addObject(s1);

        Object s2 = new Empty("Sphere1");
        s2.addProperty(new SphereRenderer(2, new Color(0, 255, 0)));
        s2.transform().setPosition(new Vector(new double[]{0, 2, 3}));
        World.getCurrent().addObject(s2);

        Object s3 = new Empty("Sphere1");
        s3.addProperty(new SphereRenderer(0.75, new Color(0, 0, 255)));
        s3.transform().setPosition(new Vector(new double[]{1, -3, -3}));
        World.getCurrent().addObject(s3);
    }
}
