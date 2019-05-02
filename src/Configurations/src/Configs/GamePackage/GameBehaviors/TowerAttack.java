package Configs.GamePackage.GameBehaviors;

import ActiveConfigs.ActiveWeapon;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.GamePackage.Game;
import Configs.GamePackage.GameStatus;
import Configs.MapFeature;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.HashMap;
import java.util.Map;

/**
 * game mode for  protecting towers from enemies-- key updates for win conditions are in the update method
 */
public class TowerAttack extends GameBehavior{
    public static final String DISPLAY_LABEL = "Tower Attack";

    @Configure
    WeaponConfig towerToProtect;
    @XStreamOmitField
    private transient Configuration myConfiguration;
    @XStreamOmitField
    private transient ActiveWeapon activeWeapon;


    public TowerAttack(Game game) {
        super(game);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
        Game game = (Game)parent;
        if (activeWeapon == null){
            activeWeapon = new ActiveWeapon(towerToProtect, game.getActiveLevel());
            MapFeature mapFeature = new MapFeature(game.getPaneWidth()/2-activeWeapon.getView().getWidth(),
                    game.getPaneHeight()/2-activeWeapon.getView().getHeight(),
                    0, towerToProtect.getView(), game.getActiveLevel().getPaneWidth(),
                    game.getActiveLevel().getPaneHeight(), game.getActiveLevel().getGridWidth(),
                    game.getActiveLevel().getGridHeight(), activeWeapon);
            activeWeapon.setMyMapFeature(mapFeature);
            game.getActiveLevel().addToActiveWeapons(activeWeapon);
        }
        if (getMyGame().getActiveLevel().getGoalPositions().isEmpty()){
            getMyGame().setGameStatus(GameStatus.GAMELOST);
        }
        else if (getMyGame().getActiveLevel().noMoreEnemiesLeft()) {
            if(getMyGame().isLastLevel()) getMyGame().setGameStatus(GameStatus.GAMEWON);
            else getMyGame().setGameStatus(GameStatus.LEVELOVER);
        }
    }



    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public Map<String, Integer> getSpecialValueForDisplay() {
        Map<String, Integer> ret = new HashMap<>();
        ret.put("Number of Towers:", getMyGame().getActiveLevel().getNumActiveEnemies());
        return ret;
    }

    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }

    @Override
    public Behavior copy() {
        TowerAttack towerAttack = new TowerAttack(getMyGame());
        return towerAttack;
    }
}
