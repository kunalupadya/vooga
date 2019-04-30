package Configs.EnemyPackage.EnemyBehaviors;


import ActiveConfigs.ActiveEnemy;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.EnemyPackage.EnemyConfig;
import Configs.MapFeaturable;
import Configs.Shootable;
import Configs.ShooterConfig.Shooter;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Behavor that allows enemies to shoot projectiles
 * inhereted methods are commneted in the superclass
 */
public class ShootableEnemy extends EnemyBehavior implements Shootable {

    public static final String DISPLAY_LABEL = "Shootable Enemy";
    @Configure
    private Shooter myShooter;

    @XStreamOmitField
    private transient Configuration myConfiguration;
    private ActiveEnemy activeEnemy;

    public ShootableEnemy(EnemyConfig enemyConfig){
        super(enemyConfig);
        myConfiguration = new Configuration(this);
    }

    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }

    @Override
    public void update(double ms, Updatable parent) {
        if(activeEnemy==null) activeEnemy = (ActiveEnemy) parent;

        myShooter.update(ms, this);
    }

    public Shooter getShooter() {
        return myShooter;
    }


    @Override
    public MapFeaturable getPossibleShooter() {
        return activeEnemy;
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public Behavior copy() {
        ShootableEnemy ret = new ShootableEnemy(getMyEnemyConfig());
        ret.myShooter = new Shooter(myShooter, ret);
        return ret;
    }
}
