package Configs.EnemyPackage.EnemyBehaviors;

import Configs.Configuration;
import Configs.EnemyPackage.EnemyConfig;
import Configs.Updatable;

public class AI extends EnemyBehavior {
    public static final String myLabel = "AI";
    @Configure
    private int speed;

    private Configuration myConfiguration;

    public AI(EnemyConfig enemyConfig){
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


}