package Configs.GamePackage.GameBehaviors;

import ActiveConfigs.ActiveLevel;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.GamePackage.Game;
import Configs.GamePackage.GameStatus;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * game mode where the goal is to get the most points in a certain amount of time
 */
public class TimedGame extends GameBehavior{
    public static final String DISPLAY_LABEL = "Beat the Timer";
    @Configure
    private int totalTimeInSec;

    private Configuration myConfiguration;


    public TimedGame(Game game) {
        super(game);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
        Game game = (Game) parent;
        if(ms>=totalTimeInSec*1000) {
            if(game.isLastLevel()) {
                game.setGameStatus(GameStatus.OVER);
            }
            else game.setGameStatus(GameStatus.LEVELOVER);
        }

    }

    @Override
    public Behavior copy() {
        TimedGame ret = new TimedGame(getMyGame());
        ret.totalTimeInSec = totalTimeInSec;
        return ret;
    }

    @Override
    public Map<String, Integer> getSpecialValueForDisplay() {
        Map<String, Integer> ret = new HashMap<>();
        ret.put("Time Remaining:", totalTimeInSec);
        return ret;
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
