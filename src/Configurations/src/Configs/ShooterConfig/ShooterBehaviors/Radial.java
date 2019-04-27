package Configs.ShooterConfig.ShooterBehaviors;

import ActiveConfigs.ActiveLevel;
import ActiveConfigs.ActiveProjectile;
import ActiveConfigs.ActiveWeapon;
import Configs.*;
import Configs.ShooterConfig.Shooter;

public class Radial extends ShooterBehavior {
    public static final String DISPLAY_LABEL = "Radial Shooting";
    private transient Configuration myConfiguration;

    public Radial(Shooter shooter){
        super(shooter);
        myConfiguration = new Configuration(this);
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }

    @Override
    public void update(double ms, Updatable parent) {
        //NOTE: parent is the Shooter
        Shooter shooter = (Shooter) parent;
        if(ms%shooter.getRateOfFire()==0) {
            ActiveWeapon activeWeapon = shooter.getMyShootable().getActiveWeapon();
            ActiveLevel myActiveLevel =  activeWeapon.getActiveLevel();
            MapFeature myShooterMapFeature = activeWeapon.getMapFeature();
            double weaponX = myShooterMapFeature.getPixelXPos();
            double weaponY = myShooterMapFeature.getPixelYPos();
            View view = activeWeapon.getView();

            double width = view.getWidth();
            double height = view.getHeight();
            double projectileStartXPos = weaponX + width/2;
            double projectileStartYPos = weaponY + height/2;
            for(int i = 0 ;i<6;i++) {
                double direction = 60*i;
                MapFeature projectileMapFeature = new MapFeature(projectileStartXPos, projectileStartYPos,direction, shooter.getProjectileConfig().getView(), myActiveLevel.getPaneWidth(), myActiveLevel.getPaneHeight(), myActiveLevel.getGridWidth(), myActiveLevel.getGridWidth());
                ActiveProjectile activeProjectile = new ActiveProjectile(shooter.getProjectileConfig(), projectileMapFeature, shooter.getShooterRange(), myActiveLevel);
                myActiveLevel.addToActiveProjectiles(activeProjectile);
                ((Shooter) parent).addToProjectilesFired(1);
            }
        }
    }
}
