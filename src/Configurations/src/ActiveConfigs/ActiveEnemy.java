package ActiveConfigs;

import Configs.*;
import Configs.Behaviors.Behavior;
import Configs.EnemyPackage.EnemyBehaviors.AIOptions;
import Configs.EnemyPackage.EnemyConfig;
import Configs.MapPackage.Terrain;
//import Configs.MapPackage.TerrainBehaviors.SpeedModifier;
import Configs.MapPackage.TerrainBehaviors.TerrainBehavior;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import Configs.EnemyPackage.EnemyBehaviors.AIOptions.*;


public class ActiveEnemy extends EnemyConfig implements Updatable, MapFeaturable, Attackable {
    public static final double CONVERSION_TO_SECONDS = .001;
    private MapFeature myMapFeature;
    private Cell[][] activeMapGrid;
    private double distance = 0;
    private ActiveLevel myActiveLevel;
    private double startTime = -Integer.MAX_VALUE;
    private LinkedList<Point> prevLocations = new LinkedList<>();
    private double effectiveSpeed;
    private List<SpeedModifier> speedModifiers = new ArrayList<>();


    enum MovementDirection {
        DOWN(0, 1, 180),
        UP(0, -1, 0),
        LEFT(-1, 0, 90),
        RIGHT(1, 0, 270);

        int x;
        int y;
        int direction;

        MovementDirection(int x, int y, int direction){
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }

        public int getDirection() {
            return direction;
        }
    }


    public ActiveEnemy(EnemyConfig enemyConfig,ActiveLevel activeLevel) {
        super(enemyConfig);
        myActiveLevel = activeLevel;
    }

    public void setMyMapFeature(MapFeature mapFeature) {
        this.myMapFeature = mapFeature;
    }

    @Override
    public void attack(int damage) {
        //TODO: FINISH
    }

    @Override
    public MapFeature getMapFeature() {
        return myMapFeature;
    }


//    public void addInstantiationModifier(InstantiationModifier instantiationModifier){
//        instantiationModifier.apply(this);
//    }
    public void addSpeedModifier(SpeedModifier speedModifier){
        speedModifiers.add(speedModifier);
    }

    @Override
    public void update(double ms, Updatable parent) {
//        System.out.println("HERE");
//        System.out.println(myMapFeature.getMyCells().size());
//        TerrainBehavior[] tbs = myActiveLevel.getGridCell(myMapFeature.getGridXPos(), myMapFeature.getGridYPos()).getMyTerrain().getTerrainBehaviors() ;
//        if (tbs!=null) {
//            ArrayList<TerrainBehavior> behaviorsList = new ArrayList<TerrainBehavior>(Arrays.asList(tbs));
//            for (TerrainBehavior b : behaviorsList) {
//                if (b.getClass() == SpeedModifier.class) {
//                    effectiveSpeed = this.getUnitSpeedPerSecond() * ((SpeedModifier) (b)).getSpeedMultiplier();
//                    break;
//                }
//                effectiveSpeed = this.getUnitSpeedPerSecond();
//            }
//        }
        //get x, y from myMapFeature and do logic using the map within the activeLevel
//        if
        //dont forget to update state to PRESENT or DIED in myMapFeature

        effectiveSpeed = getUnitSpeedPerSecond();
        List<SpeedModifier> speedModifiersToRemove = new ArrayList<>();
        for (SpeedModifier speedModifier: speedModifiers){
            if (ms<speedModifier.getEndTime()){
                effectiveSpeed*=speedModifier.getSpeedModifier();
            }
            else{
                speedModifiersToRemove.add(speedModifier);
            }
        }
        for (SpeedModifier speedModifier: speedModifiersToRemove){
            speedModifiers.remove(speedModifier);
        }

        if (startTime == -Integer.MAX_VALUE){
            startTime = ms;
        }

        double numMovements = getUnitSpeedPerSecond();

        for (int i = 0; i < numMovements; i++) {
            MovementDirection movementDirection = determineMovementDirection(getAiType());
            int newX = myMapFeature.getGridXPos()+movementDirection.getX();
            int newY = myMapFeature.getGridYPos()+movementDirection.getY();
            int heuristicValue = getAiType().getGetter().apply(myActiveLevel.getGridCell(newX,newY));
            if (heuristicValue ==0 ){
                myActiveLevel.incrementEscapedEnemies();
                killMe();
            }
            prevLocations.addFirst(new Point(newX, newY));
            if (prevLocations.size()>5){
                prevLocations.removeLast();
            }
            myMapFeature.setGridPos(newX, newY,movementDirection.getDirection());
        }
    }



    private MovementDirection determineMovementDirection(AIOptions aiTypes){
        return moveShortestDistance(aiTypes.getGetter());
    }

    private MovementDirection moveShortestDistance(Function<Cell, Integer> cellConsumer) {
        int[]xAdditions = new int[]{0,0,-1,1};
        int[]yAdditions = new int[]{1,-1,0,0};
        List<Integer> bestOption = new ArrayList<>();
        bestOption.add(0);
        int bestOptionHeuristic =  Integer.MAX_VALUE;
        for (int k = 0; k < 4; k++) {
            int totalHeuristic = Integer.MAX_VALUE;
            int i = getView().getHeight()/2;
            int j = getView().getWidth()/2;
            int x = myMapFeature.getGridXPos()+xAdditions[k]+getView().getWidth()/2;
            int y = myMapFeature.getGridYPos()+yAdditions[k]+getView().getHeight()/2;

//            for

            int topleft = myMapFeature.getGridXPos()+xAdditions[k];
            int topleftx = myMapFeature.getGridYPos()+yAdditions[k];

            Point newxy = new Point(x,y);
            if (isCellValid(x,y)&& !prevLocations.contains(newxy)){
                int[] checkY = new int[]{0,0,getView().getHeight(), getView().getHeight()};
                int[] checkX = new int[]{0,getView().getWidth(), 0, getView().getWidth()};
                boolean valid = true;
                for (int m=0; m<checkX.length;m++){
//                checkCorners[m] = new Point(checkX[m], checkY[m]);
                    if (!isCellValid(checkX[m], checkY[m])){
                        valid = false;
                    }
                }
                if (valid) {
                    Cell myCell = myActiveLevel.getGridCell(x, y);
                    totalHeuristic = cellConsumer.apply(myCell);
                }
            }
            if (totalHeuristic<bestOptionHeuristic){
                bestOption.clear();
                bestOption.add(k);
                bestOptionHeuristic = totalHeuristic;
            }
            if (totalHeuristic==bestOptionHeuristic){
                bestOption.add(k);
            }
        }
        Random random = new Random();
        return MovementDirection.values()[bestOption.get(random.nextInt(bestOption.size()))];
    }

    private boolean isCellValid(int x, int y){
        if (x<0|x>=myActiveLevel.getMyMapConfig().getGridWidth()){
            return false;
        }
        return !(y < 0 | y >= myActiveLevel.getMyMapConfig().getGridHeight());
    }

    public void killMe(){
        myMapFeature.setDisplayState(DisplayState.DIED);
        myActiveLevel.addGameCash(1*getRewardForKilling());
        myActiveLevel.addGameScore(5*getRewardForKilling());

    }

    @Override
    public ActiveLevel getActiveLevel() {
        return myActiveLevel;
    }
}
