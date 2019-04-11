package ActiveConfigs;

import Configs.*;
import Configs.ArsenalConfig.Arsenal;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.EnemyPackage.EnemyConfig;
import Configs.LevelPackage.Level;
import Configs.MapPackage.Terrain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActiveLevel extends Level implements Updatable {
    public static final int DISTANCE_HEURISTIC = 1;
    private Map<Integer,ActiveWeapon> activeWeapons;
    private List<ActiveEnemy> activeEnemies;
    private List<ActiveProjectile> activeProjectiles;
    private ActiveWave activeWave;
    private Cell[][] myGrid;
    private int myScore;
    private int currentWave=0;
    private final int gridWidth;
    private final int gridHeight;

    public ActiveLevel(Level level){//, MapFeature mapFeature) {
        super(level);
        activeEnemies = new ArrayList<>();
        activeProjectiles = new ArrayList<>();
        activeWeapons = new HashMap<>();
        generateCurrentActiveWave();
        //TODO: fix active wave to be a wave spawner
        activeWave = new ActiveWave(getMyWaveConfigs()[0], this);
//        setMyGame(game);
//        myMapFeature = mapFeature;
        myGrid = createMyGrid();
        gridHeight = getMyMapConfig().getGridHeight();
        gridWidth = getMyMapConfig().getGridWidth();
    }

    private Cell[][] createMyGrid(){
        Cell[][] tempGrid = new Cell[getMyMapConfig().getGridHeight()][getMyMapConfig().getGridWidth()];
        for(Terrain t : getMyMapConfig().getTerrain()){
            tempGrid[t.getGridYPos()][t.getGridXPos()].setMyTerrain(t);
        }
        return null;
    }

    public boolean noMoreEnemiesLeft() {
        //TODO: check logic on seeing if theres no more waves

        return activeEnemies.size()==0;
    }



    public Cell getGridCell(int gridX, int gridY){
        return myGrid[gridY][gridX];
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    @Override
    public void update(long ms) {
        updateWeapons(ms);
        updateEnemies(ms);
        updateProjectiles(ms);
        updateActiveWave(ms);
    }

    private void updateEnemies(long ms){
        for(ActiveEnemy enemy : activeEnemies){
            enemy.update(ms);
        }
        if (activeWave.isFinished()) currentWave++;
        //ArrayAttributeManager.updateList(activeWave, ms); ??
    }

    private void updateActiveWave(long ms){
        if (activeWave.isFinished()) {
            currentWave++;
            generateCurrentActiveWave();
        }
        activeWave.update(ms);
    }

    private void generateCurrentActiveWave(){
        activeWave = new ActiveWave(getMyWaveConfigs()[currentWave], this);
    }

    private void updateProjectiles(long ms){
        for (ActiveProjectile projectile: activeProjectiles){
            projectile.update(ms);
        }
    }

    private void updateWeapons(long ms){
        for (int id: activeWeapons.keySet()){
            activeWeapons.get(id).update(ms);
        }
    }


    public boolean isValid(int x, int y, double weaponHeight, double weaponWidth){
        //TODO
        return false;

    }

    private ImmutableImageView evaluateViewToBeRemoved(MapFeaturable feature) {
        if(feature instanceof ActiveWeapon) activeWeapons.remove(feature);
        else if(feature instanceof ActiveProjectile) activeProjectiles.remove(feature);
        else if (feature instanceof ActiveEnemy) activeEnemies.remove(feature);
        return feature.getMapFeature().getImageView();

    }

    public List<ImmutableImageView> getViewsToBeRemoved() {
        List<MapFeaturable> viewsToRemove =Stream.of(activeWeapons.values(), activeEnemies, activeProjectiles)
                .flatMap(Collection::stream).collect(Collectors.toList());
        return viewsToRemove.stream()
                .filter(obj -> obj.getMapFeature().getDisplayState()==DisplayState.DIED)
                .map(feature-> evaluateViewToBeRemoved(feature))
                .collect(Collectors.toList());

    }

    public List<ImmutableImageView> getViewsToBeAdded() {
        List<MapFeaturable> viewsToAdd =Stream.of(activeWeapons.values(), activeEnemies, activeProjectiles)
                .flatMap(Collection::stream).collect(Collectors.toList());
        return viewsToAdd.stream().filter(obj -> obj.getMapFeature().getDisplayState()==DisplayState.NEW).map(obj-> obj.getMapFeature().getImageView()).collect(Collectors.toList());
    }


    public ActiveWeapon getActiveWeapon(int id) {
        return activeWeapons.get(id);
    }

    public int getMyScore() {
        return myScore;
    }


    //TODO: EventHandler for adding new weapon to map
    public TransferImageView generateNewWeapon(int ID, double pixelX, double pixelY){
        WeaponConfig myWeaponConfig = getMyArsenal().getConfiguredWeapons()[ID-1];
        ActiveWeapon activeWeapon = new ActiveWeapon(myWeaponConfig, new MapFeature(pixelX, pixelY, 0, myWeaponConfig.getView(),gridHeight, gridWidth), this);
        activeWeapon.getMapFeature().setDisplayState(DisplayState.NEW);
        addToActiveWeapons(activeWeapon);
        return activeWeapon.getMapFeature().getImageView();
    }

    //TODO  add EventHandler for isValid


    public void addToActiveEnemies(EnemyConfig enemy, MapFeature mapFeature) {
        activeEnemies.add(new ActiveEnemy(enemy, mapFeature,this));
    }
//
//    public void removeFromActiveEnemies(ActiveEnemy activeEnemy){
//        activeEnemies.remove(activeEnemy);
//    }

    public void addToActiveProjectiles(ActiveProjectile activeProjectile) {
        activeProjectiles.add(activeProjectile);
    }

//    public void removeFromActiveProjectiles(ActiveProjectile activeProjectile){
//        activeProjectiles.remove(activeProjectile);
//
//    }


    private void recalculateMovementHeuristic(){
        astar(myGrid[getMyMapConfig().getEnemyExitGridXPos()][getMyMapConfig().getEnemyExitGridYPos()]);
    }

    private void astar(Cell startCell){
        startCell.setMovementHeuristic(0);
        LinkedList<Cell> stack = new LinkedList<>();
        stack.addLast(startCell);
        while(!stack.isEmpty()){
            Cell expandedCell = stack.removeFirst();
            int[]xAdditions = new int[]{0,0,-1,1};
            int[]yAdditions = new int[]{1,-1,0,0};
            for (int i = 0; i < 3; i++) {
                int x = expandedCell.getX() + xAdditions[i];
                int y = expandedCell.getY() + yAdditions[i];
                if(isCellValid(x,y)){
                    if (myGrid[x][y].getMyTerrain().getIfPath()){
                        myGrid[x][y].setMovementHeuristic(Integer.MAX_VALUE);
                    }
                    int newHeuristic = expandedCell.getMovementHeuristic() + DISTANCE_HEURISTIC;
                    if (newHeuristic<myGrid[x][y].getMovementHeuristic()){
                        myGrid[x][y].setMovementHeuristic(newHeuristic);
                    }
                }
            }
        }
    }

    private boolean isCellValid(int x, int y){
        if (x<0|x>getMyMapConfig().getGridWidth()){
            return false;
        }
        if (y<0|y>getMyMapConfig().getGridHeight()){
            return false;
        }
        return true;
    }


    public void addToActiveWeapons(ActiveWeapon activeWeapon) {
        activeWeapons.put(activeWeapon.getWeaponId(), activeWeapon);
        recalculateMovementHeuristic();

    }

//    public void removeFromActiveWeapons(ActiveWeapon activeWeapon){
//        activeWeapons.remove(activeWeapon);
//    }
}
