package Configs.EnemyPackage.EnemyBehaviors;

import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.EnemyPackage.EnemyConfig;
import Configs.Updatable;

public class EnemyBehaviorExample extends EnemyBehavior {
    public static final String myLabel = "<insert class name here>";
    @Configure
    private int speed;

    private Configuration myConfiguration;

    public EnemyBehaviorExample(EnemyConfig enemyConfig){
        super(enemyConfig);
        myConfiguration = new Configuration(this);
    }

    @Override
    public String getName() {
        return myLabel;
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public void update(double ms, Updatable parent) {

    }

    @Override
    public Behavior copy() {
        return null;
    }
}
