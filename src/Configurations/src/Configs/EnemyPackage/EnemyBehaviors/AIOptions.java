package Configs.EnemyPackage.EnemyBehaviors;

import ActiveConfigs.Cell;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public enum AIOptions {
    SHORTEST_PATH(Cell::getShortestDistanceHeuristic, (cell, inte) ->cell.setShortestDistanceHeuristic(inte)),
    SHORTEST_IGNORE_PATH(Cell::getShortestDistanceHeuristicIgnorePath, (cell, inte) -> cell.setShortestDistanceHeuristicIgnorePath(inte)),
    SHORTEST_PATH_AVOID_WEAPON(Cell::getShortestDistanceHeuristicAvoidWeapons, (cell, inte) -> cell.setShortestDistanceHeuristicAvoidWeapons(inte)),
    SHORTEST_IGNORE_PATH_AVOID_WEAPON(Cell::getShortestDistanceHeuristicAvoidWeaponsIgnorePath, (cell, inte) -> cell.setShortestDistanceHeuristicAvoidWeaponsIgnorePath(inte));

    Function< Cell, Integer> getter;
    BiConsumer<Cell, Integer> setter;

    AIOptions(Function< Cell, Integer> getter, BiConsumer<Cell, Integer> setter){
        this.getter = getter;
        this.setter = setter;
    }

    public BiConsumer<Cell, Integer> getSetter() {
        return setter;
    }

    public Function<Cell, Integer> getGetter() {
        return getter;
    }
}
