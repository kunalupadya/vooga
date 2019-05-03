package Configs.ProjectilePackage.ProjectileBehaviors;

import ActiveConfigs.ActiveEnemy;
import ActiveConfigs.ActiveProjectile;
import ActiveConfigs.Cell;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.ProjectilePackage.ProjectileConfig;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Explosive extends ProjectileBehavior{
    @Configure
    private int explosiveRange;

    @XStreamOmitField
    private transient Configuration myConfiguration;
    public static final String DISPLAY_LABEL = "Explosive Behavior";


    public Explosive (ProjectileConfig projectileConfig){
        super(projectileConfig);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
        ActiveProjectile activeProjectile = (ActiveProjectile) parent;
        int x = activeProjectile.getMapFeature().getGridXPos();
        int y = activeProjectile.getMapFeature().getGridYPos();
        for(int i = x ; i<x+explosiveRange;i++) {
            for (int j = y ; j<y+explosiveRange;j++) {
                Cell currCell = activeProjectile.getActiveLevel().getMyGrid()[i][j];
                for(ActiveEnemy enemy : currCell.getMyEnemies()) {
                    activeProjectile.getActiveLevel().killEnemy(enemy);
                }
            }
        }



//TODO: FILL IN
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public Behavior copy() {
        return new Explosive(getMyProjectileConfig());

    }

    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }
}
