package models.GameLogic.utills;

import models.GameLogic.GameMap;
import models.GameLogic.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

//import static javafx.scene.input.KeyCode.Q;

public class BFS {

    public static ArrayList<Position> getPath(GameMap gameMap, Position origin, Position destination, int range) {
        if(origin.equals(destination)) {
            return new ArrayList<>(Arrays.asList(origin));
        }
        boolean[][] isVisited = new boolean[gameMap.getWidth()][gameMap.getHeight()];
        Position[][] lastPositionInPath = new Position[gameMap.getWidth()][gameMap.getHeight()];
        isVisited[origin.getX()][origin.getY()] = true;
        LinkedList<Position> queue = new LinkedList<>();
        Position position = null;
        queue.addFirst(origin);
        isVisited[origin.getX()][origin.getY()] = true;
        while (!queue.isEmpty()) {
            position = queue.pollFirst();
            int x = position.getX();
            int y = position.getY();
            isVisited[x][y] = true;
            if (position.calculateDistance(destination) <= range + PathFinder.EPSILON) {
                break;
            }
            for(Position dir : PathFinder.DIRECTIONS) {
                Position neighbour = Position.addPositions(position, dir);
                if (neighbour.isInBoundary(gameMap) && !isVisited[neighbour.getX()][neighbour.getY()]) {
                    lastPositionInPath[neighbour.getX()][neighbour.getY()] = position;
                    isVisited[neighbour.getX()][neighbour.getY()] = true;
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
