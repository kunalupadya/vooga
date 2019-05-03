package ActiveConfigs;

import Configs.*;
import Configs.ProjectilePackage.ProjectileBehaviors.ProjectileBehavior;
import Configs.ProjectilePackage.ProjectileConfig;

import java.util.*;

public class ActiveProjectile extends ProjectileConfig implements Updatable, MapFeaturable {
    private MapFeature myMapFeature;
    private double distanceLeft;
    private ActiveLevel myActiveLevel;
    private double previousMs=0;
    //true if the projectile was shot by a weapon and seeks out enemies
    private boolean team;



    public ActiveProjectile(ProjectileConfig projectileConfig,double distanceLeft, MapFeaturable whatShotTheProjectile, ActiveLevel activeLevel){
        super(projectileConfig);
        this.distanceLeft =distanceLeft;
        myActiveLevel = activeLevel;
        this.team = whatShotTheProjectile instanceof ActiveWeapon;
    }

    @Override
    public void setMyMapFeature(MapFeature myMapFeature) {
        this.myMapFeature = myMapFeature;
    }

    @Override
    public void update(double ms, Updatable parent) {
        Arrays.stream(getMyBehaviors()).forEach(obj->obj.update(ms, this));
        if(distanceLeft>0 && myMapFeature.getGridXPos() < myActiveLevel.getGridWidth() && myMapFeature.getGridYPos() < myActiveLevel.getGridHeight()) {
            move(ms);

            if (getMyBehaviors()!=null) {
                for (ProjectileBehavior b : getMyBehaviors()) {
                    b.update(ms, this);
                }
            }
            checkForCollisions();
        }
        else {
            getActiveLevel().killProjectile(this);
        }

    }

    public boolean isTeam() {
        return team;
    }

    @Override
    public ActiveLevel getActiveLevel() {
        return myActiveLevel;
    }

    /**
     * check each cell that the projectile is on for enemies
     */
    private void checkForCollisions() {
        for (Cell c : myMapFeature.getMyCells()) {
            if (team&&c.getMyEnemies().size() > 0) {
                for (ActiveEnemy activeEnemy: c.getMyEnemies()) {
                    handleEnemyCollision(activeEnemy);
                }
            }
            if(!team&&c.getMyWeapon()!=null){
                handleWeaponCollision(c.getMyWeapon());
            }
        }
    }

    private void handleWeaponCollision(ActiveWeapon aes){
        if (myMapFeature.getImageView().intersects(aes.getMapFeature().getImageView().getBoundsInParent())) {
            aes.attack((int) getStrength());
//            killMe();
        }
    }

    private void handleEnemyCollision(ActiveEnemy aes){
        if (myMapFeature.getImageView().intersects(aes.getMapFeature().getImageView().getBoundsInParent())&&aes.getMapFeature().getDisplayState()!=DisplayState.DIED) {
            aes.attack((int) getStrength());
//            killMe();
        }
    }

    private void move(double ms){
        double velocityMs = getVelocityInSeconds()/1000;
        double distanceToTravel = (velocityMs*(ms-previousMs));
        if(previousMs==0) {
            distanceToTravel = distanceLeft%distanceLeft;
        }
        previousMs = ms;
        double changeX = distanceToTravel*Math.cos(Math.toRadians(myMapFeature.getTrigDirection()));
        double changeY = distanceToTravel*Math.sin(Math.toRadians(myMapFeature.getTrigDirection()));
        myMapFeature.moveRelatively(changeX,changeY);
        distanceLeft-=distanceToTravel;
    }

    public void killMe(){
        myMapFeature.setDisplayState(DisplayState.DIED);
//        getActiveLevel().removeFromActiveProjectiles(this);
    }

    @Override
    public MapFeature getMapFeature() {
        return myMapFeature;
    }
}
