package Configs.LevelPackage.LevelBehaviors;

import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.LevelPackage.Level;
import Configs.Updatable;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Deflation extends LevelBehavior{
    private Configuration myConfiguration;


    public Deflation(Level level) {
        super(level);
        myConfiguration = new Configuration(this);
    }
    @Override
    public Behavior copy() {
        return new Deflation(getMyLevel());
    }

    @Override
    public void update(double ms, Updatable parent) {
        //unneeded, this works!
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
