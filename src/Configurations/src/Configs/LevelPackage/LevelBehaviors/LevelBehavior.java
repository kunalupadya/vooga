package Configs.LevelPackage.LevelBehaviors;

import Configs.Behaviors.Behavior;
import Configs.LevelPackage.Level;

import java.util.List;

public abstract class LevelBehavior implements Behavior<Level> {

    private Level myLevel;
    public static final List<Class> IMPLEMENTING_BEHAVIORS = List.of(Deflation.class, Survival.class, GlueWorld.class);
    public static final String DISPLAY_LABEL = "Level Behavior";

    public LevelBehavior(Level level) {
        myLevel = level;
    }

    public Level getMyLevel() {
        return myLevel;
    }

    public void setMyLevel(Level myLevel) {
        this.myLevel = myLevel;
    }

    @Override
    public List<Class> getBehaviorOptions() {
        return IMPLEMENTING_BEHAVIORS;
    }
}
