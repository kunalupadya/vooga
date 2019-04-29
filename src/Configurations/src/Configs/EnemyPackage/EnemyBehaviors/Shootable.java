//package Configs.EnemyPackage.EnemyBehaviors;
//
//
//import ActiveConfigs.ActiveWeapon;
//import Configs.ArsenalConfig.WeaponBehaviors.WeaponBehavior;
//import Configs.ArsenalConfig.WeaponConfig;
//import Configs.Behaviors.Behavior;
//import Configs.Configuration;
//import Configs.EnemyPackage.EnemyConfig;
//import Configs.ShooterConfig.Shooter;
//import Configs.Updatable;
//import com.thoughtworks.xstream.annotations.XStreamOmitField;
//
//public class Shootable extends EnemyBehavior{
//
//    public static final String DISPLAY_LABEL = "Shootable";
//    @Configure
//    private Shooter myShooter;
//
//    @XStreamOmitField
//    private transient Configuration myConfiguration;
//    private EnemyConfig enemyConfig;
//    @XStreamOmitField
//    private ActiveWeapon activeWeapon;
//
//    public Shootable(EnemyConfig enemyConfig){
//        super(enemyConfig);
//        myConfiguration = new Configuration(this);
//        this.enemyConfig = enemyConfig;
//    }
//
//    @Override
//    public String getName() {
//        return DISPLAY_LABEL;
//    }
//
//    @Override
//    public void update(double ms, Updatable parent) {
//        if(activeWeapon==null) activeWeapon = (ActiveWeapon) parent;
//
//        myShooter.update(ms, this);
//    }
//
//    public Shooter getShooter() {
//        return myShooter;
//    }
//
//    @Override
//    public Configuration getConfiguration() {
//        return myConfiguration;
//    }
//
//    public WeaponConfig getWeaponConfig() {
//        return weaponConfig;
//    }
//
//    public ActiveWeapon getActiveWeapon() {
//        return activeWeapon;
//    }
//
//    @Override
//    public Behavior copy() {
//        Shootable ret = new Shootable(getWeaponConfig());
//        ret.myShooter = new Shooter(myShooter, ret);
//        return ret;
//    }
//}
