package Configs.ArsenalConfig;

import ActiveConfigs.ActiveWeapon;
import Configs.*;
import Configs.GamePackage.Game;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.*;


//used to hold all of the possible weapons configured in the authoring environemnt
public class Arsenal implements Configurable, Updatable {
    public static final String DISPLAY_LABEL = "Arsenal";
    private Game myGame;
    @Configure
    private WeaponConfig[] defaultWeapons;
    @Configure
    private WeaponWave[] unlockableWeapons;

    @XStreamOmitField
    private transient Configuration myConfiguration;
    private List<WeaponConfig> unlockedWeapons;
    private List<WeaponConfig> newUnlockedWeapons;


    public Arsenal(Game game) {
        myConfiguration = new Configuration(this);
        myGame = game;
        unlockedWeapons = new ArrayList<>();
        newUnlockedWeapons = new ArrayList<>();
    }

    public Game getGame() {
        return myGame;
    }

    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }

    @Override
    public void update(double ms, Updatable parent) {
        //parent is Game
        Arrays.asList(unlockableWeapons).stream().filter(weaponWave -> !weaponWave.isUnlocked()).forEach(weaponWave -> weaponWave.update(ms, this));
    }

    public WeaponConfig getWeapon(int id) {
        return unlockedWeapons.get(id-1);
    }


    public void addToUnlockedWeapons(List<WeaponConfig> weaponConfigs) {
        newUnlockedWeapons.addAll(weaponConfigs);
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }


    //note: ID is the index of the weapon+1
    public Map<Integer, Info> getAllNewWeaponConfigOptions() {

        //System.out.println(Arrays.asList(unlockedWeapons));


        if(unlockedWeapons.isEmpty()) {
            newUnlockedWeapons.addAll(Arrays.asList(defaultWeapons));
        }
        Map<Integer, Info> weaponInfoMap = new HashMap<>();
        for(int i = 0; i< newUnlockedWeapons.size(); i++) {
            weaponInfoMap.put(unlockedWeapons.size()+i+1, newUnlockedWeapons.get(i));
            newUnlockedWeapons.get(i).setWeaponId(unlockedWeapons.size()+i+1);
        }
        unlockedWeapons.addAll(newUnlockedWeapons);
        newUnlockedWeapons.clear();
        return Collections.unmodifiableMap(weaponInfoMap);
    }

    public TransferImageView generateNewWeapon(int ID, double pixelX, double pixelY, int direction){
        WeaponConfig myWeaponConfig = unlockedWeapons.get(ID-1);
        ActiveWeapon activeWeapon = new ActiveWeapon(myWeaponConfig, myGame.getActiveLevel());
        MapFeature mapFeature = new MapFeature(pixelX, pixelY, direction, myWeaponConfig.getView(), myGame.getActiveLevel().getPaneWidth(), myGame.getActiveLevel().getPaneHeight(), myGame.getActiveLevel().getGridWidth(), myGame.getActiveLevel().getGridHeight(), activeWeapon);
        activeWeapon.setMyMapFeature(mapFeature);
        myGame.getActiveLevel().addToActiveWeapons(activeWeapon);
        myGame.addToCash(-1);//TODO: DO THIS BASED ON HOW MUCH THE WEAPON COSTS
        return activeWeapon.getMapFeature().getImageView();
    }


}
