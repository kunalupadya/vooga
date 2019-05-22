package Configs.Waves.WaveBehaviors;

import ActiveConfigs.ActiveEnemy;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.Updatable;
import Configs.Waves.Wave;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * when applied to a wave, this behavior changes the effective helath
 */
public class HealthMultiplier extends WaveBehavior{
    public static final String DISPLAY_LABEL = "Health Multiplier";
    @Configure
    private double healthMultiplier;


    private Configuration myConfiguration;

    public HealthMultiplier(Wave wave) {
        super(wave);
        myConfiguration = new Configuration(this);
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }

    @Override
    public void update(double ms, Updatable parent) {

    }

    @Override
    public void apply(ActiveEnemy enemy) {
        enemy.multiplyHealth(healthMultiplier);
    }

    @Override
    public Behavior copy() {
        HealthMultiplier ret = new HealthMultiplier(getMyWave());
        ret.healthMultiplier = healthMultiplier;
        return ret;
    }
}
