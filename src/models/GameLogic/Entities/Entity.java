package models.GameLogic.Entities;

import interfaces.Locatable;
import models.GameLogic.Position;

public class Entity implements Locatable {
    protected Position position;
    private int id;

    public Entity(){
    }

    public Entity(Position position) {
        this.position = position;
    }

    public Entity(int x, int y) {

    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double calculateDistance(Entity anotherEntity) {
        return Math.sqrt(
                Math.pow(position.getX() - anotherEntity.position.getX(), 2) +
                Math.pow(position.getY() - anotherEntity.position.getY(), 2)
        );
    }



}
