package Configs.ArsenalConfig.WeaponBehaviors;

import Configs.ArsenalConfig.WeaponConfig;
import Configs.Behaviors.Behavior;

import java.util.List;

public abstract class WeaponBehavior implements Behavior<WeaponConfig> {
    WeaponConfig myWeaponConfig;
    public static final List<Class> IMPLEMENTING_BEHAVIORS = List.of(
            AmmoExpirable.class,
            HealthExpirable.class,
            Movable.class,
            PlaceableOnPath.class,
            Shootable.class,
            TimeExpirable.class);

    WeaponBehavior(WeaponConfig weaponConfig){
        myWeaponConfig = weaponConfig;
    }

    public WeaponConfig getMyWeaponConfig() {
        return myWeaponConfig;
    }

    public void setMyWeaponConfig(WeaponConfig myWeaponConfig) {
        this.myWeaponConfig = myWeaponConfig;
    }
}
