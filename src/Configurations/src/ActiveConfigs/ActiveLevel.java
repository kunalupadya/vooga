package ActiveConfigs;

import Configs.*;
import Configs.EnemyPackage.EnemyBehaviors.AIOptions;
import Configs.EnemyPackage.EnemyConfig;
import Configs.GamePackage.Game;
import Configs.GamePackage.GameBehaviors.TowerAttack;
import Configs.LevelPackage.Level;
import Configs.LevelPackage.LevelBehaviors.Deflation;
import Configs.MapPackage.Terrain;

import java.awt.*;
import java.io.CharArrayReader;
import java.security.PublicKey;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ActiveConfigs.MovementHeuristicGeneration.DISTANCE_HEURISTIC;
import static Configs.MapPackage.Terrain.TERRAIN_SIZE;

public class ActiveLevel extends Level implements Updatable {

    private List<MapFeaturable> activeWeapons;
    private List<MapFeaturable> activeEnemies;
    private List<MapFeaturable> activeProjectiles;
    private List<MapFeaturable> activeProjectilesToRemove;
    private List<ImmutableImageView> imagesToBeRemoved;
    private Cell[][] myGrid;
    private double paneWidth;
    private double paneHeight;
    private final int gridWidth;
    private final int gridHeight;
    private WaveSpawner myWaveSpawner;
    private List<ActiveWeapon> weaponsToDie = new ArrayList<>();
    private List<ActiveEnemy> enemiesToDie = new ArrayList<>();
    private List<ActiveProjectile> projectilesToDie = new ArrayList<>();
    private List<Point> goalPositions = new ArrayList<>();
    private int escapedEnemies;
    private int myScore = 0;

    public ActiveLevel(Level level, Game myGame){//, MapFeature mapFeature) {
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
        initializeMovementHeuristics();
//        recalculateMovementHeuristic(AIOptions.SHORTEST_PATH);
        this.paneHeight = myGame.getPaneHeight();
        this.paneWidth = myGame.getPaneWidth();
        imagesToBeRemoved = new ArrayList<>();
        this.setMyGame(myGame);

    }

    private void initializeMovementHeuristics() {
        for (AIOptions aiOption:AIOptions.values()) {
            recalculateMovementHeuristic(aiOption);
        }
    }

    public List<MapFeaturable> getActiveEnemies() {
        return activeEnemies;
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

    public void setGoalPositions(List<Point> goalPositions) {
        this.goalPositions = goalPositions;
    }

    public List<Point> getGoalPositions() {
        return goalPositions;
    }

    public int getGridHeight() {
        return gridHeight;
    }
    public void killEnemy(ActiveEnemy activeEnemy){
        enemiesToDie.add(activeEnemy);
    }

    public void killProjectile(ActiveProjectile activeProjectile){
        projectilesToDie.add(activeProjectile);
    }
    //
//    public void addToEnemiesKilled(Collection<ActiveEnemy> aes){
//        enemiesToDie.addAll(aes);
//    }

    @Override
    public void update(double ms, Updatable parent) {
        updateActive(ms, activeEnemies);
//        System.out.println(activeProjectiles);
        updateActive(ms, activeWeapons);
        try {
            updateActive(ms, activeProjectiles);
        }
        catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        myWaveSpawner.update(ms, this);

    }

    private void updateActive(double ms, List<MapFeaturable> activeList) {
        List<MapFeaturable> activeToRemove = new ArrayList<>();
        enemiesToDie.stream().forEach(e->e.killMe());
        enemiesToDie.clear();
        weaponsToDie.stream().forEach(w->w.killMe());
        weaponsToDie.clear();
        projectilesToDie.stream().forEach(p->p.killMe());
        projectilesToDie.clear();
        activeList.stream().forEach(active -> {
            ((Updatable)active).update(ms, this);
            if(active.getMapFeature().getDisplayState()==DisplayState.DIED) {
                activeToRemove.add(active);
//            if(active instanceof ActiveEnemy) escapedEnemies++;
            }
        });
        activeToRemove.stream().forEach(active -> imagesToBeRemoved.add(active.getMapFeature().getImageView()));
        activeList.removeAll(activeToRemove);
    }

    public int getEscapedEnemies() {
        return escapedEnemies;
    }

    public int getNumActiveEnemies() {
        return activeEnemies.size();
    }

    public List<ImmutableImageView> getViewsToBeRemoved() {
        List<ImmutableImageView> ret = new ArrayList<>(imagesToBeRemoved);
        imagesToBeRemoved.clear();
        return ret;
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





    public void addToActiveEnemies(ActiveEnemy activeEnemy) {
        activeEnemies.add(activeEnemy);

    }


    public void addToActiveProjectiles(ActiveProjectile activeProjectile) {
        activeProjectiles.add(activeProjectile);
    }

//    public void removeFromActiveProjectiles(ActiveProjectile activeProjectile){
//        activeProjectilesToRemove.add(activeProjectile);
//    }

    public void addToActiveWeapons(ActiveWeapon activeWeapon) {
        activeWeapons.add(activeWeapon);
        if (getGame().getGameType() instanceof TowerAttack){
            MapFeature weaponMapFeature = activeWeapon.getMapFeature();
            goalPositions.add(new Point(weaponMapFeature.getGridXPos(), weaponMapFeature.getGridYPos()));
//            initializeMovementHeuristics();
        }
        updateWeaponMovementHeuristics();
    }


    private void updateWeaponMovementHeuristics() {
        for(AIOptions aiOption: AIOptions.values()){
            if (aiOption.isUpdateOnWeaponPlacement()){
                recalculateMovementHeuristic(aiOption);
            }
        }
    }

    public void removeWeapon(ActiveWeapon activeWeapon){
        weaponsToDie.add(activeWeapon);
        if (getGame().getGameType() instanceof TowerAttack) {
            Point check = new Point(activeWeapon.getMapFeature().getGridXPos(), activeWeapon.getMapFeature().getGridYPos());
            Point toRemove = new Point(-100, -100);

            for (Point p : goalPositions) {
                if (p.equals(check)) {
                    toRemove = p;
                }
            }
            goalPositions.remove(toRemove);
        }
        updateWeaponMovementHeuristics();
    }

    public void incrementEscapedEnemies() {
        this.escapedEnemies++;
    }

    public double getPaneHeight() {
        return paneHeight;
    }

    public double getPaneWidth() {
        return paneWidth;
    }

    private void recalculateMovementHeuristic(AIOptions heuristicType){
        MovementHeuristicGeneration generateMovementHeuristics = new MovementHeuristicGeneration(myGrid, goalPositions, getMyMapConfig().getEnemyEnteringGridPosList(), heuristicType);
    }
    public void addGameCash(double amt){
        if (Arrays.asList(getLevelBehaviors()).stream().noneMatch(behavior -> behavior instanceof Deflation)) {
            getGame().addToCash(amt);
        }
    }
    public void addGameScore(int amt){
        getGame().addToScore(amt);
    }


    public boolean isCellValid(int x, int y){
        if (x<0|x>=getMyMapConfig().getGridWidth()){
            return false;
        }
        return !(y < 0 | y >= getMyMapConfig().getGridHeight());
    }
}
