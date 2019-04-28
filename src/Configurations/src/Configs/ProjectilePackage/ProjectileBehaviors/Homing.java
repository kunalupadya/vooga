package Configs.ProjectilePackage.ProjectileBehaviors;

import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.ProjectilePackage.ProjectileConfig;
import Configs.Updatable;

public class Homing extends ProjectileBehavior{
    public static final String DISPLAY_LABEL = "Homing Behavior";
    public transient Configuration myConfiguration;
    public Homing(ProjectileConfig pc){
        super(pc);
        myConfiguration = new Configuration(this);
    }
    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public void update(double ms, Updatable parent) {

    }

    @Override
    public Behavior copy(Updatable updatable) {
        Homing ret = new Homing((ProjectileConfig) updatable);
        return ret;
    }
}
