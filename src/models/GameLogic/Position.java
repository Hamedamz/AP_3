package models.GameLogic;

import javafx.geometry.Pos;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position) {
            return ((Position) obj).x == x && ((Position) obj).y == y;
        }
        return false;
    }
}
