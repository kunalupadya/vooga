//package Configs.MapPackage.TerrainBehaviors;
//
//import ActiveConfigs.ActiveEnemy;
//import ActiveConfigs.ActiveLevel;
//import ActiveConfigs.Cell;
//import Configs.Behaviors.Behavior;
//import Configs.Configuration;
//import Configs.MapPackage.Terrain;
//import Configs.Updatable;
//import com.thoughtworks.xstream.annotations.XStreamOmitField;
//
//import java.util.List;
//
//public class SpeedModifier extends TerrainBehavior{
//    @XStreamOmitField
//    private transient Configuration myConfiguration;
//    private ActiveLevel myActiveLevel;
//    private final String DISPLAY_LABEL = "Modify Enemy Speed";
//
//
//
//    @Configure
//    private double speedMultiplier;
//
//    public SpeedModifier(Terrain terrain){
//        super(terrain);
//        myConfiguration = new Configuration(this);
//    }
//    @Override
//    public void update(double ms, Updatable parent) {
//    }
//
//    @Override
//    public Configuration getConfiguration() {
//        return myConfiguration;
//    }
//
//    @Override
//    public String getName() {
//        return DISPLAY_LABEL;
//    }
//
//    public double getSpeedMultiplier() {
//        return speedMultiplier;
//    }
//
//    @Override
//    public Behavior copy(Updatable updatable) {
//        SpeedModifier ret = new SpeedModifier((Terrain) updatable);
//        ret
//        return null;
//    }
//}
