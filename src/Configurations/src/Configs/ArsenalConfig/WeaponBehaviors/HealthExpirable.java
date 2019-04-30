package Configs.ArsenalConfig.WeaponBehaviors;

import ActiveConfigs.ActiveWeapon;
import Configs.Behaviors.Behavior;
import Configs.Configurable;
import Configs.Configuration;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.DisplayState;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.Arrays;
import java.util.List;
/**
 * Behavior for weapons that causes the weapon to die after it has taken a certain amount of damage
 *  * All methods inherited from superclass are commented there.
 */
public class HealthExpirable extends WeaponBehavior {
    public static final String DISPLAY_LABEL = "Health-Expirable";
    @Configure
    private int amountOfHealth;

    @XStreamOmitField
    private transient Configuration myConfiguration;
    @XStreamOmitField
    private transient int damage;

    public HealthExpirable(WeaponConfig weaponConfig){
        super(weaponConfig);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
        if(damage>=amountOfHealth) {
            ((ActiveWeapon) parent).killMe();
        }
    }

    public void damage(int damage) {
        this.damage+=damage;
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
        HealthExpirable ret = new HealthExpirable(getMyWeaponConfig());
        ret.amountOfHealth = amountOfHealth;
        return ret;
    }
}
