package Configs.MapPackage;

import Configs.*;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.scene.image.ImageView;

import java.io.File;

public class Terrain implements Configurable, Viewable{
    private String myLabel;
    @Configure
    private View view;

    @Configure
    private int gridBlockHeight;
    @Configure
    private int gridBlockWidth;
    @Configure
    private boolean isPath;
    @Configure
    private int gridYPos;
    @Configure
    private int gridXPos;




//    @Configure
//    private TerrainBehavior[] terrainBehaviors;


    private Configuration myConfiguration;


    public Terrain(MapConfig mapConfig, String fileName, int gridYPos, int gridXPos, int gridBlockHeight, int gridBlockWidth, boolean isPath){
        view = new View(fileName,gridBlockHeight, gridBlockWidth);
        this.gridBlockHeight = gridBlockHeight;
        this.gridBlockWidth = gridBlockWidth;
        this.isPath = isPath;
        this.gridYPos = gridYPos;
        this.gridXPos = gridXPos;
        myConfiguration = new Configuration(this);
    }

    public int getGridXPos() {
        return gridXPos;
    }

    public int getGridYPos() {
        return gridYPos;
    }

    public double getGridBlockHeight() {
        return gridBlockHeight;
    }

    public double getGridBlockWidth() {
        return gridBlockWidth;
    }

    @Override
    public View getView() {
        return view;
    }


    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    public boolean getIfPath() {
        return isPath;
    }

    public double getHeight() {
        return view.getHeight();
    }

    public double getWidth() {
        return view.getWidth();
    }

    public boolean isPath() {
        return isPath;
    }

//    public TerrainBehavior[] getTerrainBehaviors() {
//        return terrainBehaviors;
//    }

    @Override
    public String getLabel() {
        return myLabel;
    }

    //    @Override
//    public Configuration getConfiguration() {
//        return myConfiguration;

//    }

    //implement special case with the View in this class to set the x and y

}
