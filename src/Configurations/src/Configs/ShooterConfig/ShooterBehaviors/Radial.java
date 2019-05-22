package Configs.ShooterConfig.ShooterBehaviors;

import ActiveConfigs.ActiveLevel;
import ActiveConfigs.ActiveProjectile;
import ActiveConfigs.ActiveWeapon;
import Configs.*;
import Configs.Behaviors.Behavior;
import Configs.ShooterConfig.Shooter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows a shooter to shoot its projectile in all directions when it shoots
 */
public class Radial extends ShooterBehavior {
    public static final String DISPLAY_LABEL = "Radial Shooting";
    private Configuration myConfiguration;


    public Radial(Shooter shooter){
        super(shooter);
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

        if((int)(ms/(1000/((Shooter)parent).getRateOfFire()))>startRound) {
            startRound = (int)(ms/(1000/((Shooter)parent).getRateOfFire()));
            shoot(parent, 0,60,120,180,240,300);
        }
        }

    @Override
    public Behavior copy() {
        return new Radial(getMyShooter());
    }
}

