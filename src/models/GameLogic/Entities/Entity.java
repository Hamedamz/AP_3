package models.GameLogic.Entities;

import interfaces.Locatable;
import javafx.scene.image.ImageView;
import models.GameLogic.Position;

public class Entity implements Locatable {
    protected Position position;
    private int id;
    private ImageView imageView;

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

    public ImageView getImageView() {
        return imageView;
    }
}
