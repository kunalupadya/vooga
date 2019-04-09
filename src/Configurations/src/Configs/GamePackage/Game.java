package Configs.GamePackage;

import ActiveConfigs.ActiveLevel;
import Configs.*;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.Behaviors.Behavior;
import Configs.LevelPackage.Level;
import org.w3c.dom.events.Event;

import java.util.List;

public class Game implements Updatable, EventHandlable, Configurable {

    private Configuration myConfiguration;

    @Configure
    Level[] levelList;
    @Configure
    private Behavior<Game>[] gameType;
    @Configure
    private WeaponConfig[] allWeaponConfigs;
    @Configure
    private String myTitle;
    @Configure
    private String myDescription;
    @Configure
    private String myThumbnail;

    private ActiveLevel myActiveLevel;

    public Game(){
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(long ms) {

    }

    @Override
    public void handleEvent(Event e) {

    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    public ActiveLevel getActiveLevel() {
        return myActiveLevel;
    }

    public void setMyActiveLevel(int levelIndex) {
        ActiveLevel activeLevel = new ActiveLevel(levelList[levelIndex]);

    }

    public String getTitle(){
        return myTitle;
    }

    public String getDescription(){
        return myDescription;
    }

    public String getThumbnail(){
        return myThumbnail;
    }


}
