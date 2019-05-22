package Configs.Waves.WaveBehaviors;

import ActiveConfigs.ActiveEnemy;
import Configs.ArsenalConfig.WeaponBehaviors.HealthExpirable;
import Configs.Behaviors.Behavior;
import Configs.Waves.Wave;

import java.util.List;

//ideas for wave behavior - every enemy in a wave is stronger or colored differently, every
public abstract class WaveBehavior implements Behavior<Wave> {
    public static final String DISPLAY_LABEL = "Wave Behavior";
    Wave myWave;
    public static final List<Class> IMPLEMENTING_BEHAVIORS = List.of(Random.class, HealthMultiplier.class, RewardMultiplier.class, SpeedMultiplier.class);

    public WaveBehavior(Wave wave){
        myWave = wave;
    }

    public Wave getMyWave() {
        return myWave;
    }

    public void setMyWave(Wave myWave) {
        this.myWave = myWave;
    }

    public abstract void apply(ActiveEnemy enemy);

    @Override
    public List<Class> getBehaviorOptions() {
        return IMPLEMENTING_BEHAVIORS;
    }
}
