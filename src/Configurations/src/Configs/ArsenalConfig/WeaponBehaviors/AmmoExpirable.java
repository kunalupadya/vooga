package Configs.ArsenalConfig.WeaponBehaviors;

import Configs.Configuration;
import Configs.ArsenalConfig.WeaponConfig;

import java.util.List;

public class AmmoExpirable extends WeaponBehavior {
    @Configure
    int numberOfEnemiesPossibleToKill;

    public AmmoExpirable(WeaponConfig weaponConfig){
        super(weaponConfig);
    }

    @Override
    public void update(long ms) {


    }



    @Override
    public Configuration getConfiguration() {
        return null;
    }
}