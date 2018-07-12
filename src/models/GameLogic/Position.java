package models.GameLogic;

//import javafx.geometry.Pos;

public class Position {
    public static int CELL_SIZE = 4;

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position newMapPosition(int x, int y) {
        return new Position(x * CELL_SIZE + 1, y * CELL_SIZE + 1);
    }

    /**
     * return the exact x of a position
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * return x of the cell which this position is in
     * @return (x/CELL_SIZE)
     */
    public int getMapX() {
        return x / CELL_SIZE;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getMapY() {
        return y / CELL_SIZE;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double calculateDistance(Position position) {
        return Math.sqrt(
                Math.pow(this.x - position.x, 2) +
                        Math.pow(this.y - position.y, 2)
        );
    }

    public double calculateDistanceFromBuilding(Position buildingPosition, int size) {
        if(size == 1) {
            return calculateDistance(buildingPosition);
        }
        double ans = Double.MAX_VALUE;
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Position pos = Position.newMapPosition(buildingPosition.getMapX() + i, buildingPosition.getMapY() + j);
                ans = Math.min(ans, Math.sqrt(
                        Math.pow(this.x - pos.x, 2) +
                                Math.pow(this.y - pos.y, 2)
                ));
            }
        }
        return  ans;
    }

    public static Position addPositions(Position position1, Position position2) {
        return new Position(position1.x + position2.x, position1.y + position2.y);
    }

    public boolean isInBoundary(GameMap gameMap) {
        return x >= 0 && y >= 0 && x < gameMap.getWidth() && y < gameMap.getHeight();
    }

    public boolean isInMapBoundary(GameMap gameMap) {
        return x >= 0 && y >= 0 && x < gameMap.getMapWidth() && y < gameMap.getMapHeight();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            return ((Position) obj).x == x && ((Position) obj).y == y;
        }
        return false;
    }
}
