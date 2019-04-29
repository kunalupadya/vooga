package Configs.LevelPackage.LevelBehaviors;

import ActiveConfigs.ActiveEnemy;
import ActiveConfigs.ActiveLevel;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.LevelPackage.Level;
import Configs.Updatable;
import Configs.Waves.Wave;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.Arrays;

public class GlueWorld extends LevelBehavior{
    @XStreamOmitField
    private transient Configuration myConfiguration;
    private boolean ifChanged = false;


    public GlueWorld(Level level) {
        super(level);
        myConfiguration = new Configuration(this);
    }

    @Override
    public Behavior copy() {
        return new GlueWorld(getMyLevel());
    }

    @Override
    public void update(double ms, Updatable parent) {
        if(ifChanged==false) {
            ActiveLevel activeLevel = ((ActiveLevel) parent);
            Wave[] waves = activeLevel.getMyWaves();
            for(Wave wave : waves) {
                Arrays.asList(wave.getEnemies()).stream().forEach(enemy -> enemy.multiplyUnitSpeedPerSecond(0.8));
            }
            ifChanged = true;
        }
    }

    public void apply(ActiveEnemy activeEnemy) {
        activeEnemy.multiplyUnitSpeedPerSecond(0.8);
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public String getName() {
        return null;
    }
}
