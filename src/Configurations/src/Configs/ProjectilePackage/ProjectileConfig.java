package Configs.ProjectilePackage;

import Configs.ArsenalConfig.WeaponBehaviors.WeaponBehavior;
import Configs.ShooterConfig.Shooter;
import Configs.*;
import Configs.ProjectilePackage.ProjectileBehaviors.ProjectileBehavior;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Template for projectiles that will be fired by shooters as part of weapons in the authoring environment
 */
public class ProjectileConfig implements Configurable, Viewable {
    private Shooter myShooter;

    private Configuration myConfiguration;
    public static final String DISPLAY_LABEL="Projectile";
    @Configure
    private String myName;
    @Configure
    private View view;
    @Slider(min = 5, max = 20)
    @Configure
    private double velocityInSeconds;
    @Slider(min = 50, max = 10000)
    @Configure
    private double strength;
    @Configure
    private ProjectileBehavior[] myBehaviors = new ProjectileBehavior[0];


    public ProjectileConfig(Shooter shooter) {
        myShooter = shooter;
        myConfiguration = new Configuration(this);
    }

    public ProjectileConfig(ProjectileConfig projectileConfig){
        List<ProjectileBehavior> arrayList= Arrays.stream(projectileConfig.getMyBehaviors())
                .map(behavior->(ProjectileBehavior) behavior.copy())
                .collect(Collectors.toList());
        myBehaviors = new ProjectileBehavior[arrayList.size()];
        for (int i=0; i<arrayList.size(); i++){
            myBehaviors[i] = arrayList.get(i);
        }
        myShooter = projectileConfig.getMyShooter();
        myName = projectileConfig.myName;
        view = projectileConfig.view;
        velocityInSeconds = projectileConfig.velocityInSeconds;
        strength = projectileConfig.strength;
    }

    @Override
    public View getView() {
        return view;
    }

    public double getVelocityInSeconds() {
        return velocityInSeconds;
    }

    protected ProjectileBehavior[] getMyBehaviors() {
        return myBehaviors;
    }

    public double getStrength() {
        return strength;
    }

    @Override
    public String getName() {
        return myName;
    }

    public Shooter getMyShooter() {
        return myShooter;
    }


    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }
}
