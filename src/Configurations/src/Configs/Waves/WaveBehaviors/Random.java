package Configs.Waves.WaveBehaviors;

import ActiveConfigs.ActiveEnemy;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.Updatable;
import Configs.Waves.Wave;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Random extends WaveBehavior{
    @Configure
    @Slider(min = 0, max = 100)
    private int healthRandomization;
    @Configure
    @Slider(min = 0, max = 100)
    private int speedRandomization;
    @Configure
    @Slider(min = 0, max = 100)
    private int rewardRandomization;

    public static final String DISPLAY_LABEL = "Wave Attribute Randomizer";

    private Configuration myConfiguration;

    public Random(Wave wave) {
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
         java.util.Random randomizer = new java.util.Random();
         enemy.multiplyHealth(getRandomPercent(healthRandomization));
         enemy.multiplyRewardForKilling(getRandomPercent(rewardRandomization));
         enemy.multiplyUnitSpeedPerSecond(getRandomPercent(speedRandomization));
    }

    public double getRandomPercent(int percentToRandomize){
        java.util.Random randomizer = new java.util.Random();
        return ((randomizer.nextInt(2*percentToRandomize)-percentToRandomize)+100)/100.0;
    }

    @Override
    public Behavior copy() {
        Random ret = new Random(getMyWave());
        ret.healthRandomization = healthRandomization;
        ret.rewardRandomization = rewardRandomization;
        ret.speedRandomization = speedRandomization;
        return ret;
    }

}
