package Configs.ProjectilePackage.ProjectileBehaviors;

import Configs.Behaviors.Behavior;
import Configs.LevelPackage.Level;
import Configs.ProjectilePackage.ProjectileConfig;

import java.util.List;

/**
 * Template for special behaviors that can be attached to projectiles and can be configured in the authoring environment
 */
public abstract class ProjectileBehavior implements Behavior<ProjectileConfig> {
    public static final String DISPLAY_LABEL = "Projectile Behavior";
    ProjectileConfig myProjectileConfig;
    public static final List<Class> IMPLEMENTING_BEHAVIORS = List.of(Explosive.class, Homing.class, SpeedModifier.class, Wind.class);

    ProjectileBehavior(ProjectileConfig projectileConfig){
        myProjectileConfig = projectileConfig;
    }

    /**
     * retrns the configuration of the parent object
     * @return
     */
    public ProjectileConfig getMyProjectileConfig() {
        return myProjectileConfig;
    }

    public void setMyProjectileConfig(ProjectileConfig myProjectileConfig) {
        this.myProjectileConfig = myProjectileConfig;
    }

    @Override
    public List<Class> getBehaviorOptions() {
        return IMPLEMENTING_BEHAVIORS;
    }
}
