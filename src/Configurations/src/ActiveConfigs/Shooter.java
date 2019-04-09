package ActiveConfigs;

import Configs.ArsenalConfig.WeaponBehaviors.Shootable;
import Configs.MapFeature;
import Configs.ShooterConfig.ShooterConfig;
import Configs.Updatable;
import Configs.View;

public class Shooter extends ShooterConfig implements Updatable {
    //TODO after demo: implement behaviors

    public Shooter(ShooterConfig shooterConfig){
        super(shooterConfig);
    }

    @Override
    public void update(long ms) {
        //only shooting radially rn
        if(ms%getRateOfFire()==0) {
            int weaponId = getMyShootable().getWeaponConfig().getWeaponId();
            MapFeature myShooterMapFeature = getMyShootable().getWeaponConfig().getArsenal().getLevel().getParent().getActiveLevel().getActiveWeapon(weaponId).getMapFeature();
            double weaponX = myShooterMapFeature.getPixelXPos();
            double weaponY = myShooterMapFeature.getPixelYPos();
            View view = getMyShootable().getWeaponConfig().getArsenal().getLevel().getParent().getActiveLevel().getActiveWeapon(weaponId).getView();
            double width = view.getWidth();
            double height = view.getHeight();
            double projectileStartXPos = weaponX + width/2;
            double projectileStartYPos = weaponY + height/2;
            for(int i = 0 ;i<6;i++) {
                double direction = 60*i;
                MapFeature projectileMapFeature = new MapFeature(projectileStartXPos, projectileStartYPos,direction, getProjectileConfig().getView());
                ActiveProjectile activeProjectile = new ActiveProjectile(getProjectileConfig(), projectileMapFeature, getRadius());
                getMyShootable().getWeaponConfig().getArsenal().getLevel().getParent().getActiveLevel().addToActiveProjectiles(activeProjectile);
            }
        }



    }
}
