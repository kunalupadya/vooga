package Configs.ArsenalConfig.WeaponBehaviors;

import ActiveConfigs.Shooter;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.Configurable;
import Configs.Configuration;
import Configs.ShooterConfig.ShooterConfig;

public class Shootable extends WeaponBehavior{

    @Configure
    private String myLabel;
    @Configure
    private ShooterConfig shooterConfig;

    private Shooter myShooter;

    Configuration myConfiguration;
    private WeaponConfig weaponConfig;

    public Shootable(WeaponConfig weaponConfig){
        super(weaponConfig);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(long ms) {
        myShooter.update(ms);
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public String getLabel() {
        return myLabel;
    }

    public WeaponConfig getWeaponConfig() {
        return weaponConfig;
    }
}
