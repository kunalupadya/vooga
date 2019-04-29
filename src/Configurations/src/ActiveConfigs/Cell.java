package ActiveConfigs;

import Configs.MapPackage.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;

public class Cell implements Comparable{
    private int X;
    private int Y;
    private ActiveWeapon myWeapon;
    private Terrain myTerrain;
    private List<ActiveEnemy> myEnemies;
    private int shortestDistanceHeuristic;
    private int shortestDistanceHeuristicIgnorePath;
    private int shortestDistanceHeuristicAvoidWeapons;
    private int shortestDistanceHeuristicAvoidWeaponsIgnorePath;
    private boolean cellBlocked;
    private int weaponCoverage;


//    public Cell(){
//
//    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Cell){
            return this.getShortestDistanceHeuristic() - ((Cell) o).getShortestDistanceHeuristic();
        }
        return 0;
    }

    public Cell(int xpos, int ypos, Terrain t){
        X = xpos;
        Y = ypos;
        myTerrain = t;
        myEnemies = new ArrayList<>();
        myWeapon = null;
        shortestDistanceHeuristic = Integer.MAX_VALUE;
        shortestDistanceHeuristicAvoidWeapons = Integer.MAX_VALUE;
        shortestDistanceHeuristicAvoidWeaponsIgnorePath = Integer.MAX_VALUE;
        shortestDistanceHeuristicIgnorePath = Integer.MAX_VALUE;
    }

    public boolean isValidWeaponPlacement(boolean isPathWeapon) {
        if (!isPathWeapon&&myTerrain.isPath()){
            return false;
        }
        return myWeapon==null;
    }

    public int getWeaponCoverage() {
        return weaponCoverage;
    }

    public void setWeaponCoverage(int weaponCoverage) {
        this.weaponCoverage = weaponCoverage;
    }


    public int getX(){
        return X;
    }
    public int getY(){
        return Y;
    }

    public Terrain getMyTerrain() {
        return myTerrain;
    }

    public List<ActiveEnemy> getMyEnemies() {
        return myEnemies;
    }

    public void setMyEnemies(List<ActiveEnemy> myEnemies) {
        this.myEnemies = myEnemies;
    }

    public void addEnemy(ActiveEnemy enemy) {
        this.myEnemies.add(enemy);
    }

    public void removeEnemy(ActiveEnemy enemy) {
        this.myEnemies.remove(enemy);
    }

    public void  setShortestDistanceHeuristic(int movementHeuristic) {
        this.shortestDistanceHeuristic = movementHeuristic;
    }

    public int getShortestDistanceHeuristic() {
        return shortestDistanceHeuristic;
    }

    public int getShortestDistanceHeuristicAvoidWeapons() {
        return shortestDistanceHeuristicAvoidWeapons;
    }

    public int getShortestDistanceHeuristicIgnorePath() {
        return shortestDistanceHeuristicIgnorePath;
    }

    public void setShortestDistanceHeuristicAvoidWeapons(int shortestDistanceHeuristicAvoidWeapons) {
        this.shortestDistanceHeuristicAvoidWeapons = shortestDistanceHeuristicAvoidWeapons;
    }

    public void setShortestDistanceHeuristicIgnorePath(int shortestDistanceHeuristicIgnorePath) {
        this.shortestDistanceHeuristicIgnorePath = shortestDistanceHeuristicIgnorePath;
    }



    public void setWeapon(ActiveWeapon activeWeapon) {
        this.myWeapon = activeWeapon;
    }

    public void removeWeapon() {
        this.myWeapon = null;
    }


    public void setShortestDistanceHeuristicAvoidWeaponsIgnorePath(int shortestDistanceHeuristicAvoidWeaponsIgnorePath) {
        this.shortestDistanceHeuristicAvoidWeaponsIgnorePath = shortestDistanceHeuristicAvoidWeaponsIgnorePath;
    }

    public int getShortestDistanceHeuristicAvoidWeaponsIgnorePath() {
        return shortestDistanceHeuristicAvoidWeaponsIgnorePath;
    }
}

