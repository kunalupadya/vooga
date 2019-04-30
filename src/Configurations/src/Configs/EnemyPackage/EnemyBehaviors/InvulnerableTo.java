package Configs.EnemyPackage.EnemyBehaviors;

/**
 * allows enemy to be impervious to certain weapons (NOT IMLEMENTED
 */

import ActiveConfigs.ActiveEnemy;
import ActiveConfigs.ActiveLevel;
import ActiveConfigs.Cell;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.EnemyPackage.EnemyConfig;
import Configs.ProjectilePackage.ProjectileConfig;
import Configs.Updatable;

public class InvulnerableTo extends EnemyBehavior{
    public static final String DISPLAY_LABEL = "InvulnerableTo";
    @Configure
    ProjectileConfig[] immuneToThese;
    private transient Configuration myConfiguration;

    public InvulnerableTo(EnemyConfig enemyConfig){
        super(enemyConfig);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
//        ActiveLevel al = ((ActiveEnemy) parent).getActiveLevel();
//        for (Cell : ((ActiveEnemy) parent).getMapFeature().getMyCells()){
//            if
//        }
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
    public Behavior copy() {
        InvulnerableTo ret = new InvulnerableTo(getMyEnemyConfig());
        ret.immuneToThese = immuneToThese;
        return ret;
    }
}
