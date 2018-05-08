package models.GameLogic;

import java.util.ArrayList;

public class BFS {
    public static ArrayList<Position> getPath(Map map, Position origin, Position destination, int range) {
        ArrayList<Position> positions = new ArrayList<>();
        boolean[][] isOccupied = map.getIsOccupied().clone();
        for (int i = 0; i < map.getWidth(); i++) {
            isOccupied[0][i] = false;
            isOccupied[i][0] = false;
            isOccupied[i][map.getHeight() - 1] = false;
            isOccupied[map.getWidth() - 1][i] = false;
        }
        boolean[][] visited = new boolean[map.getWidth()][map.getHeight()];
        visited[origin.getX()][origin.getY()] = true;
        ArrayList<Position> queue = new ArrayList<>();
        Position position;
        queue.add(origin);
        while (!queue.isEmpty()) {
            position = queue.get(0);
            int x = position.getX();
            int y = position.getY();
            visited[x][y] = true;
            if (Math.abs(position.calculateDistance(destination) - range) < 0.01) {
                positions.add(position);
                break;
            }
            try {
                if (!visited[x + 1][y] && !isOccupied[x + 1][y]) {
                    queue.add(new Position(x + 1, y));
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            try {
                if (!visited[x][y + 1] && !isOccupied[x][y + 1]) {
                    queue.add(new Position(x, y + 1));
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            try {
                if (!visited[x - 1][y] && !isOccupied[x - 1][y]) {
                    queue.add(new Position(x - 1, y));
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            try {
                if (!visited[x][y - 1] && !isOccupied[x][y - 1]) {
                    queue.add(new Position(x, y - 1));
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            positions.add(queue.get(0));
            queue.remove(0);
        }
        return positions;
    }
}
