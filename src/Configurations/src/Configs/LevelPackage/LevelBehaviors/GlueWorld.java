package Configs.LevelPackage.LevelBehaviors;

import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.LevelPackage.Level;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class GlueWorld extends LevelBehavior{
    @XStreamOmitField
    private transient Configuration myConfiguration;


    public GlueWorld(Level level) {
        super(level);
        myConfiguration = new Configuration(this);
    }

    @Override
    public Behavior copy() {
        return new GlueWorld(getMyLevel());
    }

    @Override
    public void update(double ms, Updatable parent) {

    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public String getName() {
        return null;
    }
}
