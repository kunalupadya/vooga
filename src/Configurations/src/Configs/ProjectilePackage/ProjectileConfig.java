package Configs.ProjectilePackage;

import Configs.*;
import Configs.Behaviors.Behavior;
import Configs.ShooterConfig.ShooterConfig;

import java.util.List;

public class ProjectileConfig implements Configurable {
    private ShooterConfig myShooter;

    Configuration myConfiguration;

    @Configure
    private List<Behavior<ProjectileConfig>> myBehaviors;
//    public ProjectileConfig(){
//         ProjectileOptions.values() how to get all options of the enum
//    }

    public ProjectileConfig(ShooterConfig shooter) {
        myShooter = shooter;
        myConfiguration = new Configuration(this);
    }

    public ProjectileConfig(ProjectileConfig projectileConfig){
        myBehaviors = projectileConfig.getMyBehaviors();
        myShooter = projectileConfig.getMyShooter();
    }

    private List<Behavior<ProjectileConfig>> getMyBehaviors() {
        return myBehaviors;
    }

    private ShooterConfig getMyShooter() {
        return myShooter;
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }
}
