package Configs.ProjectilePackage.ProjectileBehaviors;

import ActiveConfigs.ActiveEnemy;
import ActiveConfigs.ActiveLevel;
import ActiveConfigs.ActiveProjectile;
import ActiveConfigs.Cell;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.MapFeature;
import Configs.ProjectilePackage.ProjectileConfig;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.w3c.dom.Node;

import java.util.List;
import java.util.stream.Collectors;


public class Explosive extends ProjectileBehavior{
    @Slider(min = 30, max = 50)
    @Configure
    private int explosiveRange;


    @XStreamOmitField
    private transient Configuration myConfiguration;
    public static final String DISPLAY_LABEL = "Explosive Behavior";

    @XStreamOmitField
    private transient ImageView imageView;


    public Explosive (ProjectileConfig projectileConfig){
        super(projectileConfig);
        myConfiguration = new Configuration(this);
    }

    @Override
    public void update(double ms, Updatable parent) {

        imageView = new ImageView();
        imageView.setFitHeight(explosiveRange);
        imageView.setFitWidth(explosiveRange);
        ActiveLevel activeLevel = ((ActiveProjectile) parent).getActiveLevel();
        activeLevel.getActiveEnemies().stream().forEach(enemy -> {
            ImageView image = enemy.getMapFeature().getImageView();
            if(imageView.intersects(image.getBoundsInParent())) activeLevel.killEnemy((ActiveEnemy) enemy);
        });

    }



    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public Behavior copy() {
        return new Explosive(getMyProjectileConfig());

    }

    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }
}
