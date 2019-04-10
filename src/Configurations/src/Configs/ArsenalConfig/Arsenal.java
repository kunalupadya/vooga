package Configs.ArsenalConfig;

import ActiveConfigs.ActiveWeapon;
import Configs.Configurable.Configure;
import Configs.Configuration;
import Configs.Info;
import Configs.LevelPackage.Level;
import Configs.MapFeature;

import java.util.*;


//used to hold all of the possible weapons configured in the authoring environemnt
public class Arsenal {
    @Configure
    private WeaponConfig[] allWeaponConfigOptions;

    private Configuration myConfiguration;
    private Level myLevel;


//    private WeaponConfig[] unlockedWeapons;

    public Arsenal(Level level) {
        myConfiguration = new Configuration(level);
        myLevel = level;
    }

    public Level getLevel() {
        return myLevel;
    }

    //    public Map<String, TransferImageView> getAllWeaponConfigOptions() {
//        WeaponConfig[] myWeapons = (WeaponConfig[]) myConfiguration.getDefinedAttributes().get(allWeaponConfigOptions.toString());
//        Map<String, TransferImageView> myMap = new ArrayList<>(Arrays.asList(myWeapons)).stream().collect(Collectors.toMap(weapon->weapon.getName(), weapon->weapon.getImageView()));
//        return Collections.unmodifiableMap(myMap);
//    }

    //note: ID is the index of the weapon+1
    public Map<Integer, Info> getAllWeaponConfigOptions() {
        WeaponConfig[] myWeaponConfigs = getConfiguredWeapons();
        Map<Integer, Info> weaponInfoMap = new HashMap<>();
        for(int i = 0; i< myWeaponConfigs.length; i++) {
            weaponInfoMap.put(i+1, new Info(myWeaponConfigs[i].getName(), myWeaponConfigs[i].getImageView()));
            myWeaponConfigs[i].setWeaponId(i+1);
        }
        return Collections.unmodifiableMap(weaponInfoMap);

    }

    //TODO: FOR SECOND SPRINT - UNLOCKABLES
//    public List<WeaponConfig> getUnlockedWeapons() {
//        return Collections.unmodifiableList(unlockedWeapons);
//    }

//    public void unlockWeapon(String name){
//        //make a weapon unlocked
//    }

    public WeaponConfig[] getConfiguredWeapons() {
        return (WeaponConfig[]) myConfiguration.getDefinedAttributes().get(allWeaponConfigOptions.toString());
    }

    //TODO: ALLOW CHANGE OF DIRECTION
//    public WeaponConfig generateNewWeapon(int ID, double pixelX, double pixelY, double direction){
    public WeaponConfig generateNewWeapon(int ID, double pixelX, double pixelY){
    WeaponConfig myWeaponConfig = getConfiguredWeapons()[ID];
        return new ActiveWeapon(myWeaponConfig, new MapFeature(pixelX, pixelY, 0, myWeaponConfig.getView()));
    }
}
