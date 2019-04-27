package ActiveConfigs;

import Configs.*;
import Configs.EnemyPackage.EnemyConfig;
import Configs.LevelPackage.Level;
import Configs.MapPackage.Terrain;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Configs.MapPackage.Terrain.TERRAIN_SIZE;

public class ActiveLevel extends Level implements Updatable {
    public static final int DISTANCE_HEURISTIC = 1;
    private List<MapFeaturable> activeWeapons;
    private List<MapFeaturable> activeEnemies;
    private List<MapFeaturable> activeProjectiles;
    private Cell[][] myGrid;
    private double paneWidth;
    private double paneHeight;
    private final int gridWidth;
    private final int gridHeight;
    private WaveSpawner myWaveSpawner;
    private List<Point> goalPositions = new ArrayList<>();
    private int escapedEnemies;

    public ActiveLevel(Level level, double paneWidth, double paneHeight){//, MapFeature mapFeature) {
        super(level);
        activeEnemies = new ArrayList<>();
        activeProjectiles = new ArrayList<>();
        activeWeapons = new ArrayList<>();
        myWaveSpawner = new WaveSpawner(getMyWaves());
        myGrid = createMyGrid();
        getMyMapConfig().getEnemyExitGridPosList().stream().forEach(obj->goalPositions.add(obj));
//        goalPositions.add(new Point(getMyMapConfig().getEnemyExitGridXPos(),getMyMapConfig().getEnemyExitGridYPos()));
        gridHeight = getMyMapConfig().getGridHeight();
        gridWidth = getMyMapConfig().getGridWidth();
        recalculateMovementHeuristic();
        this.paneHeight = paneHeight;
        this.paneWidth = paneWidth;
    }

    private Cell[][] createMyGrid(){
        Cell[][] tempGrid = new Cell[getMyMapConfig().getGridWidth()][getMyMapConfig().getGridHeight()];//cell[row][col]
        for(Terrain t: getMyMapConfig().getTerrain()) {
            for (int x = 0; x < TERRAIN_SIZE; x++) {
                for (int y = 0; y < TERRAIN_SIZE; y++) {
                    tempGrid[t.getGridXPos() + x][t.getGridYPos() + y] = new Cell(t.getGridXPos() + x, t.getGridYPos() + y, t);
                }
            }
        }

        return tempGrid;
    }


    public Cell[][] getMyGrid() {
        return myGrid;
    }

    public boolean noMoreEnemiesLeft() {
        return myWaveSpawner.isNoMoreEnemies()&&activeEnemies.isEmpty();
    }

    public Cell getGridCell(int gridX, int gridY){
        return myGrid[gridX][gridY];
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    @Override
    public void update(double ms, Updatable parent) {
        updateActive(ms, activeEnemies);
        updateActive(ms, activeProjectiles);
        updateActive(ms, activeWeapons);
        myWaveSpawner.update(ms, this);
        System.out.println(activeWeapons);
    }

    private void updateActive(double ms, List<MapFeaturable> activeList) {
        List<MapFeaturable> activeToRemove = new ArrayList<>();
        activeList.stream().forEach(active -> {
            ((Updatable)active).update(ms, this);
            if(active.getMapFeature().getDisplayState()==DisplayState.DIED) {
                if(active instanceof ActiveEnemy) escapedEnemies++;
                activeToRemove.add(active);
            }
        });
        activeList.removeAll(activeToRemove);
    }

    public int getEscapedEnemies() {
        return escapedEnemies;
    }

    private ImmutableImageView evaluateViewToBeRemoved(MapFeaturable feature) {
        if(feature instanceof ActiveWeapon) activeWeapons.remove(feature);
        else if(feature instanceof ActiveProjectile) activeProjectiles.remove(feature);
        else if (feature instanceof ActiveEnemy) activeEnemies.remove(feature);
        return feature.getMapFeature().getImageView();

    }

    public List<ImmutableImageView> getViewsToBeRemoved() {
        List<MapFeaturable> viewsToRemove =Stream.of(activeWeapons, activeEnemies, activeProjectiles)
                .flatMap(Collection::stream).collect(Collectors.toList());
        return viewsToRemove.stream()
                .filter(obj -> obj.getMapFeature().getDisplayState()==DisplayState.DIED)
                .map(feature-> evaluateViewToBeRemoved(feature))
                .collect(Collectors.toList());

    }

    public List<ImmutableImageView> getViewsToBeAdded() {
        List<MapFeaturable> viewsToAdd =Stream.of(activeWeapons, activeEnemies, activeProjectiles)
                .flatMap(Collection::stream).collect(Collectors.toList());
//        Stream<MapFeaturable> filteredFeatures = viewsToAdd.stream().filter(obj -> obj.getMapFeature().getDisplayState()==DisplayState.NEW);
//        List<ImmutableImageView> retList = filteredFeatures
//                .map(obj-> obj.getMapFeature().getImageView())
//                .collect(Collectors.toList())
//                ;
//        filteredFeatures.forEach(obj->obj.getMapFeature().setDisplayState(DisplayState.PRESENT));
        List<ImmutableImageView> retList = new ArrayList<>();
        for(MapFeaturable mapFeaturable: viewsToAdd){//TODO MAKE STREAM
            if (mapFeaturable.getMapFeature().getDisplayState()== DisplayState.NEW){
                retList.add(mapFeaturable.getMapFeature().getImageView());
                mapFeaturable.getMapFeature().setDisplayState(DisplayState.PRESENT);
            }
        }
        return retList;
    }





    public void addToActiveEnemies(EnemyConfig enemy, MapFeature mapFeature) {
        activeEnemies.add(new ActiveEnemy(enemy, mapFeature,this));

    }


    public void addToActiveProjectiles(ActiveProjectile activeProjectile) {
        activeProjectiles.add(activeProjectile);
    }



    public void addToActiveWeapons(ActiveWeapon activeWeapon) {
        activeWeapons.add(activeWeapon);
        recalculateMovementHeuristic();

    }



    public double getPaneHeight() {
        return paneHeight;
    }

    public double getPaneWidth() {
        return paneWidth;
    }

    private void recalculateMovementHeuristic(){
        for (Point goal:goalPositions) {
            astar(myGrid,goal.x,goal.y, "short");
            astar(myGrid,goal.x,goal.y, "shortIgnorePath");
            astar(myGrid,goal.x,goal.y, "shortAvoidWeapons");
            astar(myGrid,goal.x,goal.y, "shortAvoidWeaponsIgnorePath");
        }
    }

    private void astar(Cell[][] grid, int startX, int startY, String heuristicType){
        Cell startCell = grid[startX][startY];
        startCell.setShortestDistanceHeuristic(0);
        startCell.setShortestDistanceHeuristicAvoidWeapons(0);
        startCell.setShortestDistanceHeuristicAvoidWeaponsIgnorePath(0);
        startCell.setShortestDistanceHeuristicIgnorePath(0);
        PriorityQueue<Cell> pq = new PriorityQueue<>();
        pq.add(startCell);
        while(!pq.isEmpty()){
            popCellsAndRecalculateHeuristic(pq, heuristicType);
        }
    }

    private void popCellsAndRecalculateHeuristic(PriorityQueue<Cell> pq, String heuristicType) {
        Cell expandedCell = pq.remove();
        int[]xAdditions = new int[]{0,0,-1,1};
        int[]yAdditions = new int[]{1,-1,0,0};
        for (int i = 0; i < 3; i++) {
            int x = expandedCell.getX() + xAdditions[i];
            int y = expandedCell.getY() + yAdditions[i];
            if(isCellValid(x,y)){
                if (heuristicType.equals("short")) {
                    calculateShortestDistanceHeuristic(pq, expandedCell, myGrid[x][y], expandedCell.getShortestDistanceHeuristic() + DISTANCE_HEURISTIC);
                }
                else if (heuristicType.equals("shortIgnorePath")){
                    calculateShortestDistanceHeuristicIgnorePath(pq, myGrid[x][y], expandedCell.getShortestDistanceHeuristicIgnorePath() + DISTANCE_HEURISTIC);
                }
                else if (heuristicType.equals("shortAvoidWeapons")){
                    calculateShortestDistanceHeuristicWeapons(pq, expandedCell, myGrid[x][y], expandedCell.getShortestDistanceHeuristic() + DISTANCE_HEURISTIC + myGrid[x][y].getWeaponCoverage());
                }
                else if (heuristicType.equals("shortAvoidWeaponsIgnorePath")){
                    calculateShortestDistanceHeuristicIgnorePathWeapons(pq, myGrid[x][y], expandedCell.getShortestDistanceHeuristicIgnorePath() + DISTANCE_HEURISTIC + myGrid[x][y].getWeaponCoverage());
                }
            }
        }
    }

    private void calculateShortestDistanceHeuristic(PriorityQueue<Cell> pq, Cell expandedCell, Cell inspectedCell, int newHeuristic) {
        if (!inspectedCell.getMyTerrain().getIfPath()){
            inspectedCell.setShortestDistanceHeuristic(Integer.MAX_VALUE);
            return;
        }
        setShortestDistanceHeuristic(pq, inspectedCell, newHeuristic);
    }

    private void calculateShortestDistanceHeuristicWeapons(PriorityQueue<Cell> pq, Cell expandedCell, Cell inspectedCell, int newHeuristic) {
        if (!inspectedCell.getMyTerrain().getIfPath()){
            inspectedCell.setShortestDistanceHeuristicAvoidWeapons(Integer.MAX_VALUE);
            return;
        }
        setShortestDistanceHeuristicWeapons(pq, inspectedCell, newHeuristic);
    }

    private void setShortestDistanceHeuristicWeapons(PriorityQueue<Cell> pq, Cell inspectedCell, int newHeuristic) {
        if (newHeuristic<inspectedCell.getShortestDistanceHeuristicAvoidWeapons()){
            inspectedCell.setShortestDistanceHeuristicAvoidWeapons(newHeuristic);
            pq.add(inspectedCell);
        }
    }

    private void setShortestDistanceHeuristic(PriorityQueue<Cell> pq, Cell inspectedCell, int newHeuristic) {
        if (newHeuristic<inspectedCell.getShortestDistanceHeuristic()){
            inspectedCell.setShortestDistanceHeuristic(newHeuristic);
            pq.add(inspectedCell);
        }
    }

    private void calculateShortestDistanceHeuristicIgnorePath(PriorityQueue<Cell> pq, Cell inspectedCell, int newHeuristic) {
        if (newHeuristic<inspectedCell.getShortestDistanceHeuristicIgnorePath()){
            inspectedCell.setShortestDistanceHeuristicIgnorePath(newHeuristic);
            pq.add(inspectedCell);
        }
    }

    private void calculateShortestDistanceHeuristicIgnorePathWeapons(PriorityQueue<Cell> pq, Cell inspectedCell, int newHeuristic) {
        if (newHeuristic<inspectedCell.getShortestDistanceHeuristicAvoidWeaponsIgnorePath()){
            inspectedCell.setShortestDistanceHeuristicAvoidWeaponsIgnorePath(newHeuristic);
            pq.add(inspectedCell);
        }
    }


    private boolean isCellValid(int x, int y){
        if (x<0|x>=getMyMapConfig().getGridWidth()){
            return false;
        }
        return !(y < 0 | y >= getMyMapConfig().getGridHeight());
    }
}
