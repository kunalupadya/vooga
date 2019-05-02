package Configs.ArsenalConfig.WeaponBehaviors;


import ActiveConfigs.ActiveWeapon;
import Configs.Behaviors.Behavior;
import Configs.MapFeaturable;
import Configs.Shootable;
import Configs.ShooterConfig.Shooter;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.Configuration;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
/**
 * Behavior for weapons that allows the weapon to shoot projectiles
 *  * All methods inherited from superclass are commented there.
 */
public class ShootableWeapon extends WeaponBehavior implements Shootable {

    public static final String DISPLAY_LABEL = "ShootableEnemy";
    @Configure
    private Shooter myShooter;

    @XStreamOmitField
    private transient Configuration myConfiguration;
    private WeaponConfig weaponConfig;
    @XStreamOmitField
    private ActiveWeapon activeWeapon;

    public ShootableWeapon(WeaponConfig weaponConfig){
        super(weaponConfig);
        myConfiguration = new Configuration(this);
        this.weaponConfig = weaponConfig;
    }

    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }

    @Override
    public void update(double ms, Updatable parent) {
        if(activeWeapon==null) activeWeapon = (ActiveWeapon) parent;

        myShooter.update(ms, this);
    }

    public Shooter getShooter() {
        return myShooter;
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    public WeaponConfig getWeaponConfig() {
        return weaponConfig;
    }

    @Override
    public MapFeaturable getPossibleShooter() {
        return activeWeapon;
    }

    @Override
    public Behavior copy() {
        ShootableWeapon ret = new ShootableWeapon(getWeaponConfig());
        ret.myShooter = new Shooter(myShooter, ret);
        return ret;
    }
}
