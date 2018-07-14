package models.GameLogic.Entities;

import models.interfaces.Locatable;
import javafx.scene.image.ImageView;
import models.GameLogic.Position;
import viewers.utils.ImageLibrary;

public class Entity implements Locatable {
    protected Position position;
    private int id;
    private ImageView imageView;

    public Entity(){
        setImage();
    }

    public Entity(Position position) {
        setImage();
        this.position = position;
    }

    public Entity(int x, int y) {
        setImage();
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

    public void setImage() {
        try {
            imageView = new ImageView(ImageLibrary.valueOf(this.getClass().getSimpleName()).getImage());
        } catch (IllegalArgumentException e) {
            imageView = new ImageView();
        }
    }
}
