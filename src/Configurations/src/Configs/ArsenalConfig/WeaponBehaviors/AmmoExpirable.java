package Configs.ArsenalConfig.WeaponBehaviors;

import ActiveConfigs.ActiveWeapon;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.DisplayState;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Behavior for weapons that causes the weapon to die after it has shot a certain number of times
 *  * All methods inherited from superclass are commented there.
 */
public class AmmoExpirable extends WeaponBehavior {
    public static final String DISPLAY_LABEL = "Ammo-Expirable";
    @Configure
    private int ammoLimit;

    private Configuration myConfiguration;

    public AmmoExpirable(WeaponConfig weaponConfig){
        super(weaponConfig);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
        try {
//            System.out.println(((ActiveWeapon)parent).getShooter().getProjectilesFired());
            if (((ActiveWeapon)parent).getShooter().getProjectilesFired()>=ammoLimit) {
                ActiveWeapon activeWeapon = ((ActiveWeapon) parent);
                activeWeapon.getActiveLevel().removeWeapon(activeWeapon);
            }
        }
        catch (IllegalStateException e) {
            //Do nothing if there is no shooter
            //User shouldn't be able to configure ammoexpirable if there's no shooter
        }
    }
    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public Behavior copy() {
        AmmoExpirable ret = new AmmoExpirable(getMyWeaponConfig());
        ret.ammoLimit = ammoLimit;
        return ret;
    }
}