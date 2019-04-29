package Configs.Waves.WaveBehaviors;

import ActiveConfigs.ActiveEnemy;
import ActiveConfigs.SpeedModifier;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.Updatable;
import Configs.Waves.Wave;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class SpeedMultiplier extends WaveBehavior{
    public static final String DISPLAY_LABEL = "Speed Multiplier";
    @Configure
    private double speedAcceleration;


    @XStreamOmitField
    private transient Configuration myConfiguration;

    public SpeedMultiplier(Wave wave) {
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
        enemy.multiplyUnitSpeedPerSecond(speedAcceleration);
    }

    @Override
    public Behavior copy() {
        SpeedMultiplier speedMultiplier = new SpeedMultiplier(getMyWave());
        speedMultiplier.speedAcceleration = speedAcceleration;
        return speedMultiplier;
    }
}
