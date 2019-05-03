package Configs.ProjectilePackage.ProjectileBehaviors;

import ActiveConfigs.ActiveLevel;
import ActiveConfigs.ActiveProjectile;
import ActiveConfigs.Cell;
import Configs.Behaviors.Behavior;
import Configs.Configuration;
import Configs.ProjectilePackage.ProjectileConfig;
import Configs.Updatable;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static ActiveConfigs.UtilityClass.pointMaker;

public class Homing extends ProjectileBehavior{
    public static final String DISPLAY_LABEL = "Homing Behavior";
    public transient Configuration myConfiguration;
    @Configure
    private int lockOnRange;

    public Homing(ProjectileConfig pc){
        super(pc);
        myConfiguration = new Configuration(this);
    }
    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public void update(double ms, Updatable parent) {
        ActiveProjectile projectileParent = ((ActiveProjectile)parent);
        boolean team = projectileParent.isTeam();
        ActiveLevel activeLevel= projectileParent.getActiveLevel();
        Set<Point> visited = new HashSet<>();
        Point startPoint = new Point(projectileParent.getMapFeature().getGridXPos(), projectileParent.getMapFeature().getGridYPos());
        visited.add(startPoint);
        LinkedList<Cell> queue = new LinkedList<>();
        queue.addLast(activeLevel.getGridCell(startPoint.x, startPoint.y));
        queue.addLast(null);
        int level = 0;
        bfs:
        while (!queue.isEmpty()) {
            Cell expanded = queue.removeFirst();
            if (expanded == null) {
                level++;
                queue.addLast(null);
                if (level > lockOnRange) {
                    break;
                }
                continue;
            }
            int[] xAdditions = new int[]{0, 0, -10, 10};
            int[] yAdditions = new int[]{10, -10, 0, 0};
            for (int i = 0; i < 4; i++) {
                int x = expanded.getX() + xAdditions[i];
                int y = expanded.getY() + yAdditions[i];
                Point point = new Point(x, y);
                if (activeLevel.isCellValid(x, y) && !visited.contains(point)) {
                    visited.add(point);
                    Cell inspectedCell = activeLevel.getGridCell(x,y);
                    if ((team&&(!inspectedCell.getMyEnemies().isEmpty()))||(!team&&(inspectedCell.getMyWeapon()!=null))){
                        double directionInDegrees = Math.toDegrees(Math.atan2(-(y-startPoint.y),(x-startPoint.x)))+90;
                        projectileParent.getMapFeature().setDisplayDirection(directionInDegrees);
                        break bfs;
                    }
                    queue.addLast(inspectedCell);
                }
            }
        }
    }

    @Override
    public Behavior copy() {
        Homing ret = new Homing(getMyProjectileConfig());
        ret.lockOnRange = lockOnRange;
        return ret;
    }
}
