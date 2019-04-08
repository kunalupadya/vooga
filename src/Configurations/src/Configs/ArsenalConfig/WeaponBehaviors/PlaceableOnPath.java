package Configs.ArsenalConfig.WeaponBehaviors;

import Configs.Configuration;
import Configs.ImmutableImageView;
import Configs.View;
import Configs.ArsenalConfig.Weapon;

import java.util.List;
import java.util.Optional;

public class PlaceableOnPath extends WeaponBehavior {
    @Configure
    int rangeOnPath;

    Configuration myConfiguration;

    public PlaceableOnPath(Weapon weapon, Optional<Integer> rangeOnPath){
        super(weapon);
        myConfiguration = new Configuration(this);
        this.rangeOnPath = rangeOnPath.get();
    }

    @Override
    public void update(long ms) {

    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public List<ImmutableImageView> getViewsToBeAdded() {
        return null;
    }

    @Override
    public List<ImmutableImageView> getViewsToBeRemoved() {
        return null;
    }
}
