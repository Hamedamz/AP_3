package models;

public class Entity {
    protected Position position;

    public Entity() {
    }

    public Entity(int x, int y) {

    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void move(int x, int y) {

    }

    public double calculateDistance(Entity anotherEntity) {

    }

    public double calculateDistance(int x, int y) {

    }
}
