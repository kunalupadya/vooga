package Configs.ShooterConfig.ShooterBehaviors;

import ActiveConfigs.ActiveLevel;
import ActiveConfigs.ActiveProjectile;
import ActiveConfigs.ActiveWeapon;
import Configs.Behaviors.Behavior;
import Configs.MapFeaturable;
import Configs.MapFeature;
import Configs.ProjectilePackage.ProjectileConfig;
import Configs.ShooterConfig.Shooter;
import Configs.Updatable;

import java.util.List;

/**
 * A template for the behaviors that shooters can hold
 */
public abstract class ShooterBehavior implements Behavior<Shooter> {
    public static final String DISPLAY_LABEL = "Shooter Behavior";
    private Shooter myShooter;
    public static final List<Class> IMPLEMENTING_BEHAVIORS = List.of(Aiming.class, Linear.class, Radial.class);
    int startRound=0;

    ShooterBehavior(Shooter shooter){
        myShooter = shooter;
    }

    public Shooter getMyShooter() {
        return myShooter;
    }

    @Override
    public List<Class> getBehaviorOptions() {
        return IMPLEMENTING_BEHAVIORS;
    }

    protected void shoot(Updatable parent, double... direction){
        Shooter shooter = (Shooter) parent;
        MapFeaturable activeWeapon = ((Shooter) parent).getMyShootable().getPossibleShooter();
        ActiveLevel myActiveLevel =  activeWeapon.getActiveLevel();
        MapFeature myShooterMapFeature = activeWeapon.getMapFeature();
        double weaponX = myShooterMapFeature.getPixelXPos();
        double weaponY = myShooterMapFeature.getPixelYPos();
        double width = myShooterMapFeature.getPixelWidth();
        double height = myShooterMapFeature.getPixelHeight();
        ProjectileConfig projectileConfig = shooter.getProjectileConfig();
        double projectileStartXPos = weaponX + width/2 - projectileConfig.getView().getWidth()/2;
        double projectileStartYPos = weaponY + height/2 - projectileConfig.getView().getHeight()/2;
        for(Double dir:direction) {
            ActiveProjectile activeProjectile = new ActiveProjectile(projectileConfig, shooter.getShooterRange(), activeWeapon, myActiveLevel);
            MapFeature projectileMapFeature = new MapFeature(projectileStartXPos, projectileStartYPos,dir, shooter.getProjectileConfig().getView(), myActiveLevel.getPaneWidth(), myActiveLevel.getPaneHeight(), myActiveLevel.getGridWidth(), myActiveLevel.getGridHeight(),activeProjectile);
            activeProjectile.setMyMapFeature(projectileMapFeature);
            myActiveLevel.addToActiveProjectiles(activeProjectile);
            shooter.addToProjectilesFired();
        }
    }




}
