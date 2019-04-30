package Configs.Waves;

import ActiveConfigs.ActiveEnemy;
import ActiveConfigs.ActiveLevel;
import Configs.*;
import Configs.ArsenalConfig.WeaponBehaviors.WeaponBehavior;
import Configs.EnemyPackage.EnemyConfig;
import Configs.LevelPackage.Level;
import Configs.Waves.WaveBehaviors.WaveBehavior;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


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
    private EnemyConfig[] enemies = new EnemyConfig[0];
    @Configure
    private WaveBehavior[] myWaveBehaviors = new WaveBehavior[0];

    private transient Configuration myConfiguration;


    private double[] startTimes;
    private int currentEnemyIndex = 0;
    @XStreamOmitField
    private transient boolean isFinished = false;

    public Wave(Level level) {
        myLevel = level;
        myConfiguration = new Configuration(this);
    }

    public Wave(Wave wave){
        myName = wave.myName;
        timeToReleaseInMs = wave.timeToReleaseInMs;
        rateOfReleaseEnemiesPerMs = wave.rateOfReleaseEnemiesPerMs;
        enemies = wave.enemies;
        List<WaveBehavior> arrayList= Arrays.stream(myWaveBehaviors)
                .map(behavior->(WaveBehavior) behavior.copy())
                .collect(Collectors.toList());
        myWaveBehaviors = new WaveBehavior[arrayList.size()];
        for (int i=0; i<arrayList.size(); i++){
            myWaveBehaviors[i] = arrayList.get(i);
        }
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

    public EnemyConfig[] getEnemies() {
        return enemies;
    }

    public void setTimeToReleaseInMs(double ms) {
        timeToReleaseInMs = ms;
    }

    @Override
    public void update(double ms, Updatable parent) {
        if(startTimes == null) {
            startTimes = new double[enemies.length];
            for(int i = 0;i<startTimes.length;i++) {
                startTimes[i] = ms+(i*rateOfReleaseEnemiesPerMs);
            }
        }

        if(ms>=timeToReleaseInMs && currentEnemyIndex<enemies.length && startTimes[currentEnemyIndex]<=ms) {
            ActiveLevel activeLevel = myLevel.getGame().getActiveLevel();
            List<Point> enterPositions = activeLevel.getMyMapConfig().getEnemyEnteringGridPosList();
            Random random = new Random();
            Point point = enterPositions.get(random.nextInt(enterPositions.size()));
            int direction = activeLevel.getMyMapConfig().getEnemyEnteringDirection();
            EnemyConfig enemyConfig = enemies[currentEnemyIndex];
            ActiveEnemy activeEnemy = new ActiveEnemy(enemyConfig, activeLevel);
            for (WaveBehavior waveBehavior: myWaveBehaviors){
                waveBehavior.apply(activeEnemy);
            }
            MapFeature newMapFeature = new MapFeature( point.x, point.y,direction,enemyConfig.getView(), activeLevel.getPaneWidth(), activeLevel.getPaneHeight(), activeLevel.getGridWidth(), activeLevel.getGridHeight(), activeEnemy);
            activeEnemy.setMyMapFeature(newMapFeature);
            activeLevel.addToActiveEnemies(activeEnemy);
            currentEnemyIndex++;
        }

        if(currentEnemyIndex>=enemies.length) {
            isFinished= true;
        }
    }

}
