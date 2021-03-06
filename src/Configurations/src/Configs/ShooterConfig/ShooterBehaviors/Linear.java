package Configs.ShooterConfig.ShooterBehaviors;

import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.ShooterConfig.Shooter;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Allows a shooter to shoot one weapon in a specified direction
 */
public class Linear extends ShooterBehavior {
    public static final String DISPLAY_LABEL = "Linear Shooter";
    private Configuration myConfiguration;

    @Configure
    private int direction;

    public Linear(Shooter shooter){
       super(shooter);
       myConfiguration = new Configuration(this);
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
    public void update(double ms, Updatable parent) {
        if((int)(ms/(1000/((Shooter)parent).getRateOfFire()))>startRound) {
            startRound = (int)(ms/(1000/((Shooter)parent).getRateOfFire()));
            shoot(parent, direction);
        }
    }

    @Override
    public Behavior copy() {
        return new Linear(getMyShooter());
    }
}
