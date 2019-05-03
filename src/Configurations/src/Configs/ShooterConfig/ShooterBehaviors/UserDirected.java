package Configs.ShooterConfig.ShooterBehaviors;

import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.ShooterConfig.Shooter;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class UserDirected {

    /**
     * Allows a shooter to shoot one weapon in a specified direction
     */
    public class Linear extends ShooterBehavior {
        public static final String DISPLAY_LABEL = "Linear Shooter";
        @XStreamOmitField
        private transient Configuration myConfiguration;

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
                shoot(parent,((Shooter)parent).getMyShootable().getPossibleShooter().getMapFeature().getDirection());
            }
        }

        @Override
        public Behavior copy() {
            return new Configs.ShooterConfig.ShooterBehaviors.Linear(getMyShooter());
        }
    }

}
