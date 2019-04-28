package Configs.Waves;

import ActiveConfigs.ActiveLevel;
import Configs.*;
import Configs.EnemyPackage.EnemyConfig;
import Configs.LevelPackage.Level;
import Configs.Waves.WaveBehaviors.WaveBehavior;

import java.awt.Point;
import java.util.List;
import java.util.Random;


public class Wave implements Updatable, Configurable {
    private Level myLevel;
    public static final String DISPLAY_LABEL = "Wave";
    @Configure
    private String myName;
    @Configure
    private double timeToReleaseInMs;
    @Configure
    private double rateOfReleaseEnemiesPerMs;
    @Configure
    private EnemyConfig[] enemies;
    @Configure
    private WaveBehavior[] myWaveBehaviors;

    private transient Configuration myConfiguration;


    private double[] startTimes;
    private int currentEnemyIndex = 0;
    private boolean isFinished = false;

    public Wave(Level level) {
        myLevel = level;
        myConfiguration = new Configuration(this);
    }


    public boolean isFinished() {
        return isFinished;
    }


    @Override
    public String getName() {
        return myName;
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    public double getTimeToReleaseInMs() {
        return timeToReleaseInMs;
    }

    @Override
    public void update(double ms, Updatable parent) {
        if(startTimes == null) {
            startTimes = new double[enemies.length];
            for(int i = 0;i<startTimes.length;i++) {
                startTimes[i] = ms+(i*rateOfReleaseEnemiesPerMs);
            }
        }

        while(ms>=timeToReleaseInMs && currentEnemyIndex<enemies.length && startTimes[currentEnemyIndex]<=ms) {
            ActiveLevel activeLevel = myLevel.getGame().getActiveLevel();
            List<Point> enterPositions = activeLevel.getMyMapConfig().getEnemyEnteringGridPosList();
            Random random = new Random();
            Point point = enterPositions.get(random.nextInt(enterPositions.size()));
            int direction = activeLevel.getMyMapConfig().getEnemyEnteringDirection();
            EnemyConfig enemyConfig = enemies[currentEnemyIndex];
            MapFeature newMapFeature = new MapFeature( point.x, point.y,direction,enemyConfig.getView(), activeLevel.getPaneWidth(), activeLevel.getPaneHeight(), activeLevel.getGridWidth(), activeLevel.getGridHeight());
            activeLevel.addToActiveEnemies(enemyConfig, newMapFeature);
            currentEnemyIndex++;
        }

        if(currentEnemyIndex>=enemies.length) {
            isFinished= true;
        }
    }

}
