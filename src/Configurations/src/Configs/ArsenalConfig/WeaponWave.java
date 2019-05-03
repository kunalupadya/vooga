package Configs.ArsenalConfig;

import Configs.ArsenalConfig.WeaponWaveBehaviors.WeaponWaveBehavior;
import Configs.Configurable;
import Configs.Configuration;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.Arrays;

/**
 * A list of weapons to be involved in one set of unlocks
 */
public class WeaponWave implements Updatable, Configurable {
    public static final String DISPLAY_LABEL = "Weapon Group";
    @Configure
    private WeaponConfig[] weaponOptions;
    @Configure
    private WeaponWaveBehavior weaponWaveBehavior;

    private Arsenal myArsenal;
    private boolean unlocked;
    private Configuration myConfiguration;

    public WeaponWave(Arsenal arsenal) {
        myArsenal = arsenal;
        unlocked = false;
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
        //parent is arsenal
        weaponWaveBehavior.update(ms, this);
        if(unlocked) {
            myArsenal.addToUnlockedWeapons(Arrays.asList(weaponOptions));
        }
    }

    /**
     *
     * @return this instance variable
     */
    public Arsenal getArsenal() {
        return myArsenal;
    }

    /**
     *
     * @return this instance variable
     */
    public boolean isUnlocked() {
        return unlocked;
    }

    /**
     *
     * @return this instance variable
     */
    public void unlock() {
        unlocked = true;

    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }
}
