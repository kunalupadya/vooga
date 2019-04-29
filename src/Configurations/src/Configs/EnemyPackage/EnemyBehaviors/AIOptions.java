package Configs.EnemyPackage.EnemyBehaviors;

import ActiveConfigs.Cell;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public enum AIOptions {
    SHORTEST_PATH(Cell::getShortestDistanceHeuristic, (cell, inte) ->cell.setShortestDistanceHeuristic(inte), false, false),
    SHORTEST_IGNORE_PATH(Cell::getShortestDistanceHeuristicIgnorePath, (cell, inte) -> cell.setShortestDistanceHeuristicIgnorePath(inte), true,false),
    SHORTEST_PATH_AVOID_WEAPON(Cell::getShortestDistanceHeuristicAvoidWeapons, (cell, inte) -> cell.setShortestDistanceHeuristicAvoidWeapons(inte), false,true),
    SHORTEST_IGNORE_PATH_AVOID_WEAPON(Cell::getShortestDistanceHeuristicAvoidWeaponsIgnorePath, (cell, inte) -> cell.setShortestDistanceHeuristicAvoidWeaponsIgnorePath(inte), true,true);

    private Function< Cell, Integer> getter;
    private BiConsumer<Cell, Integer> setter;
    private boolean ignorePath;
    boolean updateOnWeaponPlacement;

    AIOptions(Function< Cell, Integer> getter, BiConsumer<Cell, Integer> setter, boolean ignorePath, boolean updateOnWeaponPlacement){
        this.getter = getter;
        this.setter = setter;
        this.ignorePath = ignorePath;
        this.updateOnWeaponPlacement = updateOnWeaponPlacement;
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
}
