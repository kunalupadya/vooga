package Configs.Waves.WaveBehaviors;

import ActiveConfigs.ActiveEnemy;
import ActiveConfigs.SpeedModifier;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.Updatable;
import Configs.Waves.Wave;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class RewardMultiplier extends WaveBehavior{
    public static final String DISPLAY_LABEL = "Reward Multiplier";
    @Configure
    private double rewardMultiplier;


    private Configuration myConfiguration;

    public RewardMultiplier(Wave wave) {
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
        enemy.multiplyRewardForKilling(rewardMultiplier);
    }

    @Override
    public Behavior copy() {
        RewardMultiplier rewardMultiplier = new RewardMultiplier(getMyWave());
        rewardMultiplier.rewardMultiplier = this.rewardMultiplier;
        return rewardMultiplier;
    }
}
