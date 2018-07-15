package viewers.utils.entityHolders;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import models.GameLogic.Entities.Entity;
import viewers.utils.Const;
import viewers.utils.ProgressBarItem;
import viewers.utils.ProgressBarType;

public abstract class EntityHolder extends Pane {
    Entity entity;
    ImageView imageView;
    ProgressBarItem hitPointsProgressBar;
    Button button;
    private boolean isDestroyed;

    public EntityHolder(Entity entity) {
        this.entity = entity;
    }

    public void initialize() {
        imageView = entity.getImageView();
        imageView.setFitWidth(Const.ENTITY_TILE_WIDTH * Const.TILE_SCALE);
        imageView.setFitHeight(Const.ENTITY_TILE_HEIGHT * Const.TILE_SCALE);
        this.setMaxSize(Const.ENTITY_TILE_WIDTH * Const.TILE_SCALE, Const.ENTITY_TILE_HEIGHT * Const.TILE_SCALE);
        hitPointsProgressBar = new ProgressBarItem(ProgressBarType.HIT_POINTS, entity);
        button = new Button();
        button.setId("entity-image-holder");
        button.setPadding(new Insets(0));
        button.setGraphic(imageView);
        this.getChildren().addAll(button, hitPointsProgressBar);

    }

    public abstract void refresh();

    public Entity getEntity() {
        return entity;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setDestroyed(boolean destroyed) {
        this.isDestroyed = destroyed;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public Button getButton() {
        return button;
    }
}
