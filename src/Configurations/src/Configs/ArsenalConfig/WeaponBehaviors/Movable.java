package Configs.ArsenalConfig.WeaponBehaviors;


import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.awt.*;
import java.util.List;
/**
 * Behavior for weapons that allows weapons to be moved
 *  * All methods inherited from superclass are commented there.
 */
public class Movable extends WeaponBehavior{
    public static final String DISPLAY_LABEL = "Movable";
    @Configure
    private double movingSpeed;
    @Configure
    private Point[] movingPattern;

    private Configuration myConfiguration;

    public Movable(WeaponConfig weaponConfig){
        super(weaponConfig);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
        //TODO
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
        Movable ret = new Movable(getMyWeaponConfig());
        ret.movingPattern = movingPattern;
        ret.movingSpeed = movingSpeed;
        return ret;
    }
}
