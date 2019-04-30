package ActiveConfigs;

import Configs.EnemyPackage.EnemyBehaviors.AIOptions;
import javafx.concurrent.Task;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MovementHeuristicGeneration extends Task {
    public static final int DISTANCE_HEURISTIC = 1;

    private Cell[][] myGrid;
    List<Point> endPositions;

    public MovementHeuristicGeneration(Cell[][] grid, List<Point> goalPositions, List<Point> endPositions, AIOptions heuristicType){
        myGrid = grid;
        this.endPositions = endPositions;
        for (Point goal:goalPositions) {
            astar(grid,goal.x,goal.y,heuristicType);
//            astar(myGrid,goal.x,goal.y, "shortIgnorePath");
//            astar(myGrid,goal.x,goal.y, "shortAvoidWeapons");
//            astar(myGrid,goal.x,goal.y, "shortAvoidWeaponsIgnorePath");
        }
    }


    @Override
    protected Cell[][] call() {
        return null;
    }

    private void astar(Cell[][] grid, int startX, int startY, AIOptions heuristicType){
        Cell startCell = grid[startX][startY];
        startCell.setShortestDistanceHeuristic(0);
        startCell.setShortestDistanceHeuristicAvoidWeapons(0);
        startCell.setShortestDistanceHeuristicAvoidWeaponsIgnorePath(0);
        startCell.setShortestDistanceHeuristicIgnorePath(0);
        PriorityQueue<Cell> pq = new PriorityQueue<>();
        pq.add(startCell);
        Set<Point> visited = new HashSet<>();
//        visited.contains()
        visited.add(pointMaker(startCell));
        HashMap<Point, Boolean> startLocs = new HashMap<>();
        for (Point point:endPositions){
            startLocs.put(point, false);
        }
        while(!pq.isEmpty()&&startLocs.containsValue(false)){
            popCellsAndRecalculateHeuristic(pq, visited, heuristicType, startLocs);
        }
    }

    private Point pointMaker(Cell cell){
        return new Point(cell.getX(), cell.getY());
    }

    private void popCellsAndRecalculateHeuristic(PriorityQueue<Cell> pq, Set<Point> visited, AIOptions heuristicType, HashMap<Point, Boolean> startLocs) {
        Cell expandedCell = pq.remove();
        int[]xAdditions = new int[]{0,0,-1,1};
        int[]yAdditions = new int[]{1,-1,0,0};
        for (int i = 0; i < 4; i++) {
            int x = expandedCell.getX() + xAdditions[i];
            int y = expandedCell.getY() + yAdditions[i];
            Point point = new Point(x,y);
            if(!visited.contains(point)&&isCellValid(x,y)){
                visited.add(point);
                int costHeuristic = DISTANCE_HEURISTIC;
                if (heuristicType.isUpdateOnWeaponPlacement()){
                    costHeuristic+=myGrid[x][y].getWeaponCoverage();
                }
                calculateShortestDistanceHeuristic(pq, myGrid[x][y], heuristicType.getGetter().apply(expandedCell) + costHeuristic, heuristicType);
                if(startLocs.containsKey(point)){
                    startLocs.put(point,true);
                }
//                if (heuristicType == AIOptions.SHORTEST_PATH) {
//                }
//                else if (heuristicType== AIOptions.SHORTEST_IGNORE_PATH){
//                    calculateShortestDistanceHeuristicIgnorePath(pq, myGrid[x][y], expandedCell.getShortestDistanceHeuristicIgnorePath() + DISTANCE_HEURISTIC);
//                }
//                else if (heuristicType== AIOptions.SHORTEST_PATH_AVOID_WEAPON){
//                    calculateShortestDistanceHeuristicWeapons(pq, myGrid[x][y], expandedCell.getShortestDistanceHeuristicAvoidWeapons() + DISTANCE_HEURISTIC + myGrid[x][y].getWeaponCoverage());
//                }
//                else if (heuristicType==AIOptions.SHORTEST_IGNORE_PATH_AVOID_WEAPON){
//                    calculateShortestDistanceHeuristicIgnorePathWeapons(pq, myGrid[x][y], expandedCell.getShortestDistanceHeuristicAvoidWeaponsIgnorePath() + DISTANCE_HEURISTIC + myGrid[x][y].getWeaponCoverage());
//                }
            }
        }
    }
    private void calculateShortestDistanceHeuristic(PriorityQueue<Cell> pq, Cell inspectedCell, int newHeuristic, AIOptions heuristicType) {
        if (!heuristicType.isIgnorePath()&&!inspectedCell.getMyTerrain().getIfPath()){
            heuristicType.getSetter().accept(inspectedCell, Integer.MAX_VALUE);
            return;
        }
        setShortestDistanceHeuristic(pq, inspectedCell, newHeuristic, heuristicType);
    }

    private void setShortestDistanceHeuristic(PriorityQueue<Cell> pq, Cell inspectedCell, int newHeuristic, AIOptions heuristicType) {
        if (newHeuristic<heuristicType.getGetter().apply(inspectedCell)){
            heuristicType.getSetter().accept(inspectedCell, newHeuristic);
            pq.add(inspectedCell);
        }
    }

    public boolean isCellValid(int x, int y){
        if (x<0|x>=myGrid.length){
            return false;
        }
        return !(y < 0 | y >= myGrid[0].length);
    }
}
