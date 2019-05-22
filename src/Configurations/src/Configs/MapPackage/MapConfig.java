package Configs.MapPackage;

import Configs.Configurable;
import Configs.Configuration;
import Configs.LevelPackage.Level;
import Configs.MapPackage.Terrain;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static Configs.MapPackage.Terrain.TERRAIN_SIZE;

/**
 * Template for the map for eaach level that users will configure
 */
public class MapConfig implements Configurable {
    public static final String DISPLAY_LABEL = "Map";
    public static final int GRID_HEIGHT = 20;
    public static final int GRID_WIDTH = 32;
    @Configure
    private String myName;
    @Configure
    private List<Terrain> myTerrain;
    @Configure
    private List<Point> enemyEnteringGridPosList;
    @Configure
    private int enemyEnteringDirection;
    @Configure
    private List<Point> enemyExitGridPosList;

    private Configuration configuration;
    private Level myLevel;

    public MapConfig(Level level) {
        configuration = new Configuration(this);
        myLevel = level;

    }

    public Level getLevel() {
        return myLevel;
    }

    public List<Terrain> getTerrain() {
        return myTerrain;
    }


    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    public int getEnemyEnteringDirection() {
        return enemyEnteringDirection*TERRAIN_SIZE;
    }

    public List<Point> getEnemyEnteringGridPosList() {
        return enemyEnteringGridPosList.stream().map(point -> new Point(point.x*TERRAIN_SIZE, point.y*TERRAIN_SIZE)).collect(Collectors.toList());
    }

    public List<Point> getEnemyExitGridPosList() {
        return enemyExitGridPosList.stream().map(point -> new Point(point.x*TERRAIN_SIZE, point.y*TERRAIN_SIZE)).collect(Collectors.toList());
    }


    public int getGridHeight() {
        return GRID_HEIGHT*TERRAIN_SIZE;
    }

    public int getGridWidth() {
        return GRID_WIDTH*TERRAIN_SIZE;
    }


    @Override
    public String getName() {
        return myName;
    }

}
