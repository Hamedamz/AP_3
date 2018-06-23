package models.GameLogic.utills;

import models.GameLogic.EnemyMap;
import models.GameLogic.Position;

import java.util.ArrayList;
import java.util.LinkedList;

//import static javafx.scene.input.KeyCode.Q;

public class BFS {

    public static final Double EPSILON;
    public static Position[] DIRECTIONS;

    static {
        EPSILON = 1e-6;
        Position DIR_UP = new Position(0, -1);
        Position DIR_RIGHT = new Position(1, 0);
        Position DIR_DOWN = new Position(0, 1);
        Position DIR_LEFT = new Position(-1, 0);
        DIRECTIONS = new Position[]{DIR_UP, DIR_RIGHT, DIR_DOWN, DIR_LEFT};
    }

    public static ArrayList<Position> getPath(EnemyMap enemyMap, Position origin, Position destination, int range) {
        boolean[][] isVisited = new boolean[enemyMap.getWidth()][enemyMap.getHeight()];
        Position[][] lastPositionInPath = new Position[enemyMap.getWidth()][enemyMap.getHeight()];
        isVisited[origin.getX()][origin.getY()] = true;
        LinkedList<Position> queue = new LinkedList<>();
        Position position = null;
        queue.addFirst(origin);
        while (!queue.isEmpty()) {
            position = queue.pollFirst();
            int x = position.getX();
            int y = position.getY();
            isVisited[x][y] = true;
            if (position.calculateDistance(destination) <= range + EPSILON) {
                break;
            }
            for(Position dir : DIRECTIONS) {
                Position neighbour = Position.addPostions(position, dir);
                if (neighbour.isInBoundary(enemyMap) && !isVisited[neighbour.getX()][neighbour.getY()]) {
                    lastPositionInPath[neighbour.getX()][neighbour.getY()] = position;
                    queue.addLast(neighbour);
                }
            }

        }
        LinkedList<Position> path = new LinkedList<>();
        while (!position.equals(origin)) {
            path.addFirst(position);
            position = lastPositionInPath[position.getX()][position.getY()];
        }
        path.addFirst(position);
        return new ArrayList<>(path);
    }
}
