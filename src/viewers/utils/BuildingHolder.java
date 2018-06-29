package viewers.utils;

import javafx.scene.control.ProgressBar;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import models.GameLogic.Builder;
import models.GameLogic.Entities.Buildings.Building;

public class BuildingHolder extends StackPane {
    private Builder builder;
    private Building building;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Glow glow = new Glow(0);
    private boolean isUnderConstruction;

    public BuildingHolder(Building building) {
        this.building = building;
        this.imageView = building.getImageView();
        this.isUnderConstruction = false;
        this.setMaxSize(imageView.getViewport().getWidth(), imageView.getViewport().getHeight());
        this.getChildren().addAll(imageView);
        initialize();
    }

    public BuildingHolder(Builder builder) {
        this.builder = builder;
        this.building = builder.getUnderConstructBuilding();
        this.imageView = building.getImageView();
        this.isUnderConstruction = true;
        this.progressBar.setProgress(this.builder.getConstructRemainingTime() / this.builder.getTotalConstructionTime());
        this.setMaxSize(imageView.getViewport().getWidth(), imageView.getViewport().getHeight());
        this.getChildren().addAll(imageView, progressBar);
        initialize();
    }

    private void initialize() {
        this.setId("building-holder");
        progressBar = new ProgressBar();
        progressBar.setId("building-progress-bar");
        progressBar.setPrefWidth(Const.BUILDING_TILE_WIDTH);
        progressBar.setPrefHeight(6);
        imageView.setEffect(glow);
        imageView.setOnMouseEntered(event -> glow.setLevel(0.5));
        imageView.setOnMouseExited(event -> glow.setLevel(0));
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
