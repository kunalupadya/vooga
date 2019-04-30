package Configs.GamePackage.GameBehaviors;

import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.GamePackage.Game;
import Configs.GamePackage.GameStatus;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * game mode that ends when a certain amount of enemies get through
 */
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
    public Behavior copy() {
        Lives ret = new Lives(getMyGame());
        ret.numEnemies = numEnemies;
        return ret;
    }

    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }
}
