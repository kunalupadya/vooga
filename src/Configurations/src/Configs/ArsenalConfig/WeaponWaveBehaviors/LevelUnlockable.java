package Configs.ArsenalConfig.WeaponWaveBehaviors;

import Configs.ArsenalConfig.WeaponWave;
import Configs.Configuration;
import Configs.Updatable;

public class LevelUnlockable extends WeaponWaveBehavior {
    public static final String DISPLAY_LABEL = "Level-Unlockable";
    @Configure
    private int levelToBeUnlocked;

    private WeaponWave myWeaponWave;
    private transient Configuration myConfiguration;

    public LevelUnlockable(WeaponWave weaponWave) {
        this.myWeaponWave = weaponWave;
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
        //parent is weapon wave
        if(((WeaponWave)parent).getArsenal().getGame().getLevelSpawner().getLevelIndex()==levelToBeUnlocked) {
            myWeaponWave.unlock();
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
}