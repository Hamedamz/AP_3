package models.GameLogic;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import static javafx.scene.input.KeyCode.Q;

public class BFS {

    public static final Double EBSILON;
    public static Position[] DIRECTIONS;

    static {
        EBSILON = 1e-6;
        Position DIR_UP = new Position(0, -1);
        Position DIR_RIGHT = new Position(1, 0);
        Position DIR_DOWN = new Position(0, 1);
        Position DIR_LEFT = new Position(-1, 0);
        DIRECTIONS = new Position[]{DIR_UP, DIR_RIGHT, DIR_DOWN, DIR_LEFT};
    }

    public static ArrayList<Position> getPath(Map map, Position origin, Position destination, int range) {
        boolean[][] isVisited = new boolean[map.getWidth()][map.getHeight()];
        Position[][] lastPositionInPath = new Position[map.getWidth()][map.getHeight()];
        isVisited[origin.getX()][origin.getY()] = true;
        LinkedList<Position> queue = new LinkedList<>();
        Position position = null;
        queue.addFirst(origin);
        while (!queue.isEmpty()) {
            position = queue.pollFirst();
            int x = position.getX();
            int y = position.getY();
            isVisited[x][y] = true;
            if (position.calculateDistance(destination) <= range + EBSILON) {
                break;
            }
            for(Position dir : DIRECTIONS) {
                Position neighbour = Position.addPostions(position, dir);
                if (neighbour.isInBoundary(map) && !isVisited[neighbour.getX()][neighbour.getY()]) {
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
