package ActiveConfigs;

import Configs.GamePackage.Game;
import Configs.GamePackage.GameStatus;
import Configs.LevelPackage.Level;
import Configs.Updatable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class LevelSpawner implements Updatable {
    private List<Level> myLevels;
    private ActiveLevel currLevel;
    private int levelIndex;
    private boolean gameOver;
    private Game myGame;
    private boolean levelOver;

    public LevelSpawner(Game game, int levelIndex, Level[] levels) {
        this.myGame = game;
        this.levelIndex = levelIndex;
        myLevels = new ArrayList<>(Arrays.asList(levels));
        currLevel = new ActiveLevel(levels[levelIndex], game);
        levelOver = false;
        gameOver = false;
    }

    @Override
    public void update(double ms, Updatable parent) {
        if(myGame.getGameStatus()== GameStatus.PLAYING) {
            currLevel.update(ms, this);
        }
    }

    public boolean isLevelOver() {
        return levelOver;
    }

    public int startNextLevel() throws IllegalStateException{
        if(gameOver) throw new IllegalStateException();
        levelIndex++;
        currLevel = new ActiveLevel(myLevels.get(levelIndex), myGame);
        myGame.setGameStatus(GameStatus.PLAYING);
        return levelIndex;
    }

    public ActiveLevel getCurrLevel() {
        return currLevel;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getLevelIndex() {
        return levelIndex;
    }
}
