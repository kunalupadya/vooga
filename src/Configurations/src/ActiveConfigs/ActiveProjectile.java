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



    public ActiveProjectile(ProjectileConfig projectileConfig,double distanceLeft, ActiveLevel activeLevel){
        super(projectileConfig);
        this.distanceLeft =distanceLeft;
        myActiveLevel = activeLevel;
    }

    @Override
    public void setMyMapFeature(MapFeature myMapFeature) {
        this.myMapFeature = myMapFeature;
    }

    @Override
    public void update(double ms, Updatable parent) {
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
            myMapFeature.setDisplayState(DisplayState.DIED);
        }

    }

    @Override
    public ActiveLevel getActiveLevel() {
        return myActiveLevel;
    }

    /**
     * check each cell that the projectile is on for enemies
     */
    private void checkForCollisions() {
       // System.out.println("HERE" + myMapFeature.getMyCells().size());
//        System.out.println("PROJECTILE YMIN: " + (int)myMapFeature.returnBounds()[0] +
//                "YMAX: " + (int)myMapFeature.returnBounds()[1] +
//                "XMIN: " + (int)myMapFeature.returnBounds()[2] +
//                "XMAX: " + (int)myMapFeature.returnBounds()[3]);
        for (Cell c : myMapFeature.getMyCells()) {
            //System.out.println(c.getMyEnemies().size());
            if (c.getMyEnemies().size() > 0) {
                //System.out.println(c.getMyEnemies().size());
                for (ActiveEnemy activeEnemy: c.getMyEnemies()) {
                    handleEnemyCollision(activeEnemy);
                }
            }
        }
    }



    private void handleEnemyCollision(ActiveEnemy aes){
//        Iterator<ActiveEnemy> enemyIterator;
//        for(enemyIterator = myCell.getMyEnemies().iterator(); enemyIterator.hasNext();)
//        {
//            ActiveEnemy e = enemyIterator.next();
//                e.killMe();  //This removes student from the collection safely
//        }
        //List<ActiveEnemy> enemiesToKill = new ArrayList<>(myCell.getMyEnemies());
        if (myMapFeature.getImageView().intersects(aes.getMapFeature().getImageView().getBoundsInParent())) {
            myActiveLevel.killEnemy(aes);
            myMapFeature.setDisplayState(DisplayState.DIED);
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

    @Override
    public MapFeature getMapFeature() {
        return myMapFeature;
    }
}
