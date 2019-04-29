package Configs.GamePackage.GameBehaviors;

import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.GamePackage.Game;
import Configs.GamePackage.GameStatus;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class TowerAttack extends GameBehavior{
    public static final String DISPLAY_LABEL = "Tower Attack";

    @XStreamOmitField
    private transient Configuration myConfiguration;


    public TowerAttack(Game game) {
        super(game);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {
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
    public String getName() {
        return DISPLAY_LABEL;
    }

    @Override
    public Behavior copy() {
        TowerAttack towerAttack = new TowerAttack(getMyGame());
        return towerAttack;
    }
}
