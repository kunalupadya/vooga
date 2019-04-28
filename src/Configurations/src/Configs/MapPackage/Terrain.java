package Configs.MapPackage;

import ActiveConfigs.ActiveLevel;
import Configs.*;
import Configs.GamePackage.Game;
import Configs.MapPackage.TerrainBehaviors.TerrainBehavior;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.scene.image.ImageView;

import java.io.File;


public class Terrain implements Configurable, Viewable, MapFeaturable{
    public static final int TERRAIN_SIZE = 50;

    public static final String DISPLAY_LABEL = "Terrain";
    @Configure
    private String myName;
    @Configure
    private View view;
    @Configure
    private boolean isPath;
    @Configure
    private int gridYPos;
    @Configure
    private int gridXPos;
    @Configure
    private TerrainBehavior[] terrainBehaviors;

    @XStreamOmitField
    private transient Configuration myConfiguration;
    private MapConfig myMapConfig;
    private Game myGame;
    @XStreamOmitField
    private MapFeature mapFeature;


    public Terrain(MapConfig mapConfig, String fileName, int gridYPos, int gridXPos, boolean isPath){
        view = new View(fileName,TERRAIN_SIZE,TERRAIN_SIZE);
        this.isPath = isPath;
        this.gridYPos = gridYPos;
        this.gridXPos = gridXPos;
        myConfiguration = new Configuration(this);
        myMapConfig = mapConfig;
        myGame = myMapConfig.getLevel().getGame();
    }

    @Override
    public void setMyMapFeature(MapFeature mapFeature) {
        this.mapFeature = mapFeature;
    }

    public int getGridXPos() {
        return gridXPos*TERRAIN_SIZE;
    }

    public int getGridYPos() {
        return gridYPos*TERRAIN_SIZE;
    }
    public Game getMyGame(){
        return myGame;
    }
    @Override
    public View getView() {
        return view;
    }

    public ImmutableImageView getImageView(double screenWidth, double screenHeight, int gridWidth, int gridHeight) {
        mapFeature = new MapFeature(getGridXPos(), getGridYPos(), 0.0, view, screenWidth, screenHeight, gridWidth, gridHeight, this);
        return mapFeature.getImageView();
    }

    @Override
    public MapFeature getMapFeature() {
        return mapFeature;
    }

    @Override
    public ActiveLevel getActiveLevel() {
        return myMapConfig.getLevel().getGame().getActiveLevel();
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    public boolean getIfPath() {
        return isPath;
    }

    public boolean isPath() {
        return isPath;
    }

    public TerrainBehavior[] getTerrainBehaviors() {
        return terrainBehaviors;
    }


    @Override
    public String getName() {
        return myName;
    }

    //    @Override
//    public Configuration getConfiguration() {
//        return myConfiguration;

//    }

    //implement special case with the View in this class to set the x and y

}
