package Configs.ArsenalConfig.WeaponWaveBehaviors;

import Configs.ArsenalConfig.WeaponWave;
import Configs.Behaviors.Behavior;

import java.util.List;

/**
 * A template for the behaviors of weaponwaves
 */
public abstract class WeaponWaveBehavior implements Behavior<WeaponWave> {
    public static final String DISPLAY_LABEL = "Arsenal Behavior";
    public static final List<Class> IMPLEMENTING_BEHAVIORS = List.of(
            LevelUnlockable.class);

    @Override
    public List<Class> getBehaviorOptions() {
        return IMPLEMENTING_BEHAVIORS;
    }
}
