package Configs.LevelPackage;

import Configs.*;
import Configs.ArsenalConfig.WeaponBehaviors.WeaponBehavior;
import Configs.GamePackage.Game;
import Configs.LevelPackage.LevelBehaviors.GlueWorld;
import Configs.LevelPackage.LevelBehaviors.LevelBehavior;
import Configs.LevelPackage.LevelBehaviors.Survival;
import Configs.MapPackage.MapConfig;
import Configs.Waves.Wave;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A template for the Level object that will be filled in by users using the Authoring Environment
 */
public class Level implements Configurable{
    private Game myGame;
    public static final String DISPLAY_LABEL = "Level";
    @Configure
    private String myName;
    @Configure
    private Wave[] myWaves = new Wave[0];
    @Configure
    private MapConfig myMap;

    @Configure
    private LevelBehavior[] levelBehaviors;

    private Configuration myConfiguration;

    public Level(Game game) {
        myGame = game;
        myConfiguration = new Configuration(this);
    }

    public Level(Level level){
        myWaves = level.getMyWaves();
        myMap = level.getMyMapConfig();
        levelBehaviors = level.getLevelBehaviors();
        List<LevelBehavior> arrayList= Arrays.stream(level.levelBehaviors)
                .map(behavior->(LevelBehavior) behavior.copy())
                .collect(Collectors.toList());
        levelBehaviors = new LevelBehavior[arrayList.size()];
        for (int i=0; i<arrayList.size(); i++){
            levelBehaviors[i] = arrayList.get(i);
        }
        myName = level.myName;
        myGame = level.myGame;
    }

    public LevelBehavior[] getLevelBehaviors() {
        return levelBehaviors;
    }

    public MapConfig getMyMapConfig() {
        return myMap;
    }

    public Wave[] getMyWaves() {
        return myWaves;
    }

    @Override
    public String getName() {
        return myName;
    }
    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    public Game getGame(){
        return myGame;
    }
    public void setMyGame(Game g){
        myGame = g;
    }

    public boolean isSurvival() {
        return Arrays.asList(levelBehaviors).stream().anyMatch(behavior -> behavior instanceof Survival);
    }


}
