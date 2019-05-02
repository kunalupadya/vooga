package Configs.ArsenalConfig;

import Configs.*;
import Configs.ArsenalConfig.WeaponBehaviors.PlaceableOnPath;
import Configs.ArsenalConfig.WeaponBehaviors.ShootableWeapon;
import Configs.ArsenalConfig.WeaponBehaviors.WeaponBehavior;
import Configs.ShooterConfig.Shooter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The template created by the authoringenvironment for how a weapon should behave
 */

public class WeaponConfig implements  Configurable, Viewable, Info {
    @XStreamOmitField
    private transient Configuration myConfiguration;
    public static final String DISPLAY_LABEL= "Weapon";
    @Configure
    private String myName;
    @Configure
    private int weaponCost;
    @Configure
    private WeaponBehavior[] behaviors = new WeaponBehavior[0];
    @Configure
    private View view;

    private Arsenal myArsenal;
    @XStreamOmitField
    private int weaponId;

    public WeaponConfig(Arsenal myArsenal) {
        myConfiguration=new Configuration(this);
        this.myArsenal = myArsenal;
    }

    public WeaponConfig(WeaponConfig weaponConfig) {
        this.myName = weaponConfig.getName();
        List<WeaponBehavior> arrayList= Arrays.stream(weaponConfig.getBehaviors())
                .map(behavior->(WeaponBehavior) behavior.copy())
                .collect(Collectors.toList());
        behaviors = new WeaponBehavior[arrayList.size()];
        for (int i=0; i<arrayList.size(); i++){
            behaviors[i] = arrayList.get(i);
        }
        this.view = weaponConfig.getView();
    }

    public boolean isPathWeapon() {
        return Arrays.asList(getBehaviors()).stream().anyMatch(behavior -> behavior instanceof PlaceableOnPath);
    }

    @Override
    public int getImage() {return view.getImage();}

    @Override
    public String getName() {return myName;}

    @Override
    public double getWidth() {return view.getWidth();}

    @Override
    public double getHeight() {return view.getHeight();}

    /**
     *
     * @return a shooter, if the weapon has one
     * @throws IllegalStateException
     */
    public Shooter getShooter() throws IllegalStateException {
        if (Arrays.asList(getBehaviors()).stream().anyMatch(behavior -> behavior instanceof ShootableWeapon)) {
            return ((ShootableWeapon) Arrays.asList(getBehaviors()).stream().filter(behavior -> behavior instanceof ShootableWeapon).collect(Collectors.toList()).get(0)).getShooter();
        }
        else throw new IllegalStateException();
    }

    /**
     *
     * @param weaponId gives a uniquely identifaible ID to be serialized
     */
    public void setWeaponId(int weaponId) {
        this.weaponId = weaponId;
    }

    public int getWeaponId() {
        return weaponId;
    }

    public int getWeaponCost() {
        return weaponCost;
    }

    public void setWeaponCost(int weaponCost) {
        this.weaponCost = weaponCost;
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    public WeaponBehavior[] getBehaviors() {
        return behaviors;
    }

    @Override
    public View getView() {
        return view;
    }

    public Arsenal getMyArsenal() {
        return myArsenal;
    }
}
