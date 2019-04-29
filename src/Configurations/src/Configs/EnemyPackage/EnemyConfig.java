package Configs.EnemyPackage;

import Configs.*;
import Configs.ArsenalConfig.WeaponBehaviors.WeaponBehavior;
import Configs.EnemyPackage.EnemyBehaviors.AIOptions;
import Configs.EnemyPackage.EnemyBehaviors.EnemyBehavior;
import Configs.Waves.Wave;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnemyConfig implements Configurable, Viewable {
    private Wave myWave;
    public static final String DISPLAY_LABEL = "Enemy";
    @Configure
    private String myName;
    @Configure
    private EnemyBehavior[] myBehaviors = new EnemyBehavior[0];
    @Slider(min = 50, max = 10000)
    @Configure
    private int health;
    @Slider(min=1,max=10)
    @Configure
    private double unitSpeedPerSecond;
    @Configure
    private int rewardForKilling;
    @Configure
    private View view;
    @Configure
    private AIOptions aiType;

    private transient Configuration myConfiguration;

    public EnemyConfig(Wave wave) {
        myWave = wave;
        myConfiguration = new Configuration(this);
    }

    public EnemyConfig(EnemyConfig enemyConfig){
        List<EnemyBehavior> arrayList= Arrays.stream(enemyConfig.getMyBehaviors())
                .map(behavior->(EnemyBehavior) behavior.copy())
                .collect(Collectors.toList());
        myBehaviors = new EnemyBehavior[arrayList.size()];
        for (int i=0; i<arrayList.size(); i++){
            myBehaviors[i] = arrayList.get(i);
        }
        myWave = enemyConfig.getMyWave();
        unitSpeedPerSecond = enemyConfig.getUnitSpeedPerSecond();
        view = enemyConfig.getView();
        myName = enemyConfig.getName();
        health = enemyConfig.health;
        rewardForKilling = enemyConfig.rewardForKilling;
        aiType = enemyConfig.aiType;
    }

    public Wave getMyWave() {
        return myWave;
    }

    //    public void setMyBehaviors(List<Behavior<EnemyConfig>> behavior) {
//        myBehaviors = behavior;
//    }

    public EnemyBehavior[] getMyBehaviors() {
        return myBehaviors;
    }

    public double getUnitSpeedPerSecond() {
        return unitSpeedPerSecond;
    }

    @Override
    public View getView() {
        return view;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public String getName() {
        return myName;
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

//    public Wave getMyWave() {
//        return myWave;
//    }


    protected int getRewardForKilling() {
        return rewardForKilling;
    }

    public void multiplyHealth(double multiplier) {
        this.health = (int) multiplier*health;
    }

    public void multiplyRewardForKilling(double multiplier) {
        this.rewardForKilling = (int) multiplier * rewardForKilling;
    }

    public void multiplyUnitSpeedPerSecond(double multiplier) {
        this.unitSpeedPerSecond = (int) multiplier * unitSpeedPerSecond;
    }

    public AIOptions getAiType() {
        return aiType;
    }
}
