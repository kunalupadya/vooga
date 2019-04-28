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

public class TimeExpirable extends WeaponBehavior{
    public static final String DISPLAY_LABEL= "Time-Expirable";
    @Configure
    private double timeAlive;

    @XStreamOmitField
    private transient Configuration myConfiguration;

    public TimeExpirable(WeaponConfig weaponConfig){
        super(weaponConfig);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
        if(ms>=timeAlive) {
            ((ActiveWeapon) parent).getMapFeature().setDisplayState(DisplayState.DIED);
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
