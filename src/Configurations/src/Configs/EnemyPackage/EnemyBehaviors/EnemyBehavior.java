package Configs.EnemyPackage.EnemyBehaviors;

import Configs.Behaviors.Behavior;
import Configs.EnemyPackage.EnemyConfig;

import java.util.List;

/**
 * A superclass that defines behaviors for enemies
 */
public abstract class EnemyBehavior implements Behavior<EnemyConfig>{
    public static final List<Class> IMPLEMENTING_BEHAVIORS = List.of(InvulnerableTo.class, ShootableEnemy.class);
    private EnemyConfig myEnemyConfig;
    public static final String DISPLAY_LABEL = "Enemy Behavior";

    EnemyBehavior(EnemyConfig enemyConfig){
        myEnemyConfig = enemyConfig;
    }

    public EnemyConfig getMyEnemyConfig() {
        return myEnemyConfig;
    }

    public void setMyEnemyConfig(EnemyConfig myEnemyConfig) {
        this.myEnemyConfig = myEnemyConfig;
    }

    @Override
    public List<Class> getBehaviorOptions() {
        return IMPLEMENTING_BEHAVIORS;
    }
}
