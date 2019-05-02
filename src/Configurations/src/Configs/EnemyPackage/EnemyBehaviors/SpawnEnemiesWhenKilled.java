package Configs.EnemyPackage.EnemyBehaviors;

/**
 * allows enemy to be impervious to certain weapons (NOT IMLEMENTED
 */

import ActiveConfigs.ActiveEnemy;
import ActiveConfigs.ActiveLevel;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.EnemyPackage.EnemyConfig;
import Configs.MapFeature;
import Configs.ProjectilePackage.ProjectileConfig;
import Configs.Updatable;

public class SpawnEnemiesWhenKilled extends EnemyBehavior{
    public static final String DISPLAY_LABEL = "SpawnEnemiesWhenKilled";
    @Configure
    ProjectileConfig[] immuneToThese;
    private transient Configuration myConfiguration;
    EnemyConfig spawnedEnemy;

    public SpawnEnemiesWhenKilled(EnemyConfig enemyConfig){
        super(enemyConfig);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
        ActiveEnemy parentEnemy = ((ActiveEnemy) parent);
        ActiveLevel activeLevel = ((ActiveEnemy) parent).getActiveLevel();
        ActiveEnemy activeEnemy = new ActiveEnemy(spawnedEnemy,activeLevel);
        MapFeature newMapFeature = new MapFeature(parentEnemy.getMapFeature().getGridXPos() ,parentEnemy.getMapFeature().getGridYPos(),parentEnemy.getMapFeature().getDirection(),spawnedEnemy.getView(), activeLevel.getPaneWidth(), activeLevel.getPaneHeight(), activeLevel.getGridWidth(), activeLevel.getGridHeight(), activeEnemy);
        activeEnemy.setMyMapFeature(newMapFeature);
        activeLevel.addToActiveEnemies(new ActiveEnemy(spawnedEnemy,activeLevel));
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
        SpawnEnemiesWhenKilled ret = new SpawnEnemiesWhenKilled(getMyEnemyConfig());
        ret.immuneToThese = immuneToThese;
        return ret;
    }
}
