package Configs.ArsenalConfig.WeaponBehaviors;

import ActiveConfigs.ActiveEnemy;
import ActiveConfigs.ActiveWeapon;
import Configs.Behaviors.Behavior;
import Configs.Configurable;
import Configs.Configuration;
import Configs.ImmutableImageView;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.List;
import java.util.Optional;
/**
 * Behavior for weapons that causes the weapon to be placed on the path
 *  * All methods inherited from superclass are commented there.
 */
public class PlaceableOnPath extends WeaponBehavior {
    public static final String DISPLAY_LABEL = "Placeable On Path";
    @Slider(min = 50,max = 10000)
    @Configure
    private int damage;

    private Configuration myConfiguration;

    public PlaceableOnPath(WeaponConfig weaponConfig){
        super(weaponConfig);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
//        ((ActiveWeapon) parent).getActiveLevel().getActiveEnemies().stream().forEach(enemy -> ((ActiveEnemy) enemy).attack(getDamage()));
    }

    public int getDamage() {
        return damage;
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
        PlaceableOnPath ret = new PlaceableOnPath(getMyWeaponConfig());
        ret.damage = damage;
        return ret;
    }
}
