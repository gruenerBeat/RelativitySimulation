package game;
 
import engine.logic.GameRegister;
import engine.types.GameInitializer;

public class Game extends GameRegister {
    
    @Override
    public GameInitializer register() {
        GameInitializer init = new GameInitializer();
        return init;
    }

    @Override
    public void init() {
    }
}
