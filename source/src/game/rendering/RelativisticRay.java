package game.rendering;

import engine.libs.math.Vector;
import engine.libs.types.Color.Color;

public class RelativisticRay {
    
    public Vector state;
    public boolean didHit;
    public Color color;
    
    public RelativisticRay() {
        state = new Vector(8);
        didHit = false;
        color = new Color(0, 0, 0);
    }

    public RelativisticRay(Vector state) {
        this.state = state;
        didHit = false;
        color = new Color(0, 0, 0);
    }
}
