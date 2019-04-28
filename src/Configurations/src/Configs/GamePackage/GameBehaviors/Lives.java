package Configs.GamePackage.GameBehaviors;

import Configs.Behaviors.Behavior;
import Configs.Configurable;
import Configs.Configuration;
import Configs.GamePackage.Game;
import Configs.GamePackage.GameStatus;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.awt.*;
import java.util.function.Predicate;

public class Lives extends GameBehavior{
    public static final String DISPLAY_LABEL = "Lives";
    @Configure
    private int numEnemies;

    @XStreamOmitField
    private transient Configuration myConfiguration;

    public Lives(Game game) {
        super(game);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
        if (getMyGame().getActiveLevel().getEscapedEnemies()>=numEnemies) {
            getMyGame().setGameStatus(GameStatus.LOST);
        }
        else if (getMyGame().getActiveLevel().noMoreEnemiesLeft()) {
            getMyGame().setGameStatus(GameStatus.WON);
        }
    }



    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public Behavior copy(Updatable updatable) {
        Lives ret = new Lives(getMyGame());
        ret.numEnemies = numEnemies;
        return ret;
    }

    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }
}
