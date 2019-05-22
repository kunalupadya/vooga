package Configs.MapPackage.TerrainBehaviors;

import ActiveConfigs.ActiveEnemy;
import ActiveConfigs.Cell;
import Configs.Behaviors.Behavior;
import Configs.LevelPackage.Level;
import Configs.MapPackage.Terrain;

import java.util.List;

public abstract class TerrainBehavior implements Behavior<Terrain> {
    Terrain myTerrain;
    public static final List<Class> IMPLEMENTING_BEHAVIORS = List.of();

    public TerrainBehavior(Terrain terrain){
        myTerrain = terrain;
    }

    public Terrain getMyTerrain() {
        return myTerrain;
    }

    public void setMyTerrain(Terrain myTerrain) {
        this.myTerrain = myTerrain;
    }

    protected List<ActiveEnemy>getEnemiesOnMyCell(){
        Cell myCell = myTerrain.getMyGame().getActiveLevel().getGridCell(myTerrain.getGridXPos(), myTerrain.getGridYPos());
        return myCell.getMyEnemies();
    }

    @Override
    public List<Class> getBehaviorOptions() {
        return IMPLEMENTING_BEHAVIORS;
    }
}
