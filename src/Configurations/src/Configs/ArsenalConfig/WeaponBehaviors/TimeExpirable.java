package Configs.ArsenalConfig.WeaponBehaviors;

import ActiveConfigs.ActiveWeapon;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.DisplayState;
import Configs.ImmutableImageView;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.List;
/**
 * Behavior for weapons that causes the weapon to die after a certain amount of time elapses
 * All methods inherited from superclass are commented there.
 */
public class TimeExpirable extends WeaponBehavior{
    public static final String DISPLAY_LABEL= "Time-Expirable (in ms)";
    @Configure
    private double timeAlive;
    private double birthTime;

    @XStreamOmitField
    private transient Configuration myConfiguration;

    public TimeExpirable(WeaponConfig weaponConfig){
        super(weaponConfig);
        myConfiguration = new Configuration(this);
        this.birthTime = System.currentTimeMillis();

    }

    @Override
    public void update(double ms, Updatable parent) {
        if(ms>=birthTime+timeAlive) {
            ActiveWeapon activeWeapon = ((ActiveWeapon) parent);
            activeWeapon.getActiveLevel().removeWeapon(activeWeapon);
        }
    }

    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }


    @Override
    public Behavior copy() {
        TimeExpirable ret = new TimeExpirable(getMyWeaponConfig());
        ret.timeAlive = timeAlive;
        return ret;
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }
}
