package Configs.EnemyPackage.EnemyBehaviors;

import ActiveConfigs.Cell;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * gives enemies the ability to move according to an AI algorithm
 */
public enum AIOptions {
    SHORTEST_PATH(Cell::getShortestDistanceHeuristic, (cell, inte) ->cell.setShortestDistanceHeuristic(inte), false, false, "Shortest Path AI"),
    SHORTEST_IGNORE_PATH(Cell::getShortestDistanceHeuristicIgnorePath, (cell, inte) -> cell.setShortestDistanceHeuristicIgnorePath(inte), true,false, "Shortest Path AI, Ignore Path "),
    SHORTEST_PATH_AVOID_WEAPON(Cell::getShortestDistanceHeuristicAvoidWeapons, (cell, inte) -> cell.setShortestDistanceHeuristicAvoidWeapons(inte), false,true, "Shortest Path, Avoid Weapons"),
    SHORTEST_IGNORE_PATH_AVOID_WEAPON(Cell::getShortestDistanceHeuristicAvoidWeaponsIgnorePath, (cell, inte) -> cell.setShortestDistanceHeuristicAvoidWeaponsIgnorePath(inte), true,true, "Shortest Path, Avoid Weapons, Ignore Path");

    private Function< Cell, Integer> getter;
    private BiConsumer<Cell, Integer> setter;
    private boolean ignorePath;
    private boolean updateOnWeaponPlacement;
    private String stringRep;

    AIOptions(Function< Cell, Integer> getter, BiConsumer<Cell, Integer> setter, boolean ignorePath, boolean updateOnWeaponPlacement, String stringRep){
        this.getter = getter;
        this.setter = setter;
        this.ignorePath = ignorePath;
        this.updateOnWeaponPlacement = updateOnWeaponPlacement;
        this.stringRep = stringRep;
    }

    public BiConsumer<Cell, Integer> getSetter() {
        return setter;
    }

    public Function<Cell, Integer> getGetter() {
        return getter;
    }

    public boolean isIgnorePath(){
        return ignorePath;
    }

    public boolean isUpdateOnWeaponPlacement() {
        return updateOnWeaponPlacement;
    }

    @Override
    public String toString() {
        return stringRep;
    }
}