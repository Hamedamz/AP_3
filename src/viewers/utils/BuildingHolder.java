package viewers.utils;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import models.GameLogic.Builder;
import models.GameLogic.Entities.Buildings.Building;

public class BuildingHolder extends StackPane {
    private Builder builder;
    private Building building;
    private ImageView imageView;
    private ProgressBar progressBar;
    private boolean isUnderConstruction;

    public BuildingHolder(Building building) {
        this.building = building;
        this.imageView = building.getImageView();
        this.isUnderConstruction = false;
        this.setMaxSize(imageView.getViewport().getWidth(), imageView.getViewport().getHeight());
        this.getChildren().addAll(imageView);
    }

    public BuildingHolder(Builder builder) {
        this.builder = builder;
        this.building = builder.getUnderConstructBuilding();
        this.imageView = building.getImageView();
        this.isUnderConstruction = true;
        this.progressBar = new ProgressBar(Const.ENTITY_PROGGRESS_BAR_WIDTH, Const.ENTITY_PROGGRESS_BAR_HEIGHT,
                this.builder.getConstructRemainingTime() / this.builder.getTotalConstructionTime());
        this.setMaxSize(imageView.getViewport().getWidth(), imageView.getViewport().getHeight());
        this.getChildren().addAll(imageView, progressBar);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
