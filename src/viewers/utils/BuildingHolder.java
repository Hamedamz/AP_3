package viewers.utils;

import controllers.BuildingMenuController;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import models.GameLogic.Builder;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.TownHall;

public class BuildingHolder extends Pane {
    private Builder builder;
    private Building building;
    private ImageView imageView;
    private ProgressBarItem hitPointsProgressBar;
    private ProgressBarItem constructionProgressBar;
    private Glow glow = new Glow(0);

    public BuildingHolder(Building building) {
        this.building = building;
        initialize();
    }

    public BuildingHolder(Builder builder) {
        this.builder = builder;
        this.building = builder.getUnderConstructBuilding();
        initialize();
    }

    private void initialize() {
        imageView = building.getImageView();
        imageView.setFitWidth(Const.BUILDING_TILE_WIDTH * Const.TILE_SCALE);
        imageView.setFitHeight(Const.BUILDING_TILE_HEIGHT * Const.TILE_SCALE);
        if (building.getClass().equals(TownHall.class)) {
            imageView.setFitWidth(Const.TOWNHALL_TILE_WIDTH * Const.TILE_SCALE);
            imageView.setFitHeight(Const.TOWNHALL_TILE_HEIGHT * Const.TILE_SCALE);
        }
        this.setMaxSize(imageView.getViewport().getWidth(), imageView.getViewport().getHeight());
        hitPointsProgressBar = new ProgressBarItem(ProgressBarType.HIT_POINTS, building);
        this.getChildren().addAll(imageView, hitPointsProgressBar);
        if (builder != null) {
            constructionProgressBar = new ProgressBarItem(ProgressBarType.REMAINED_TIME, builder);
            this.getChildren().add(constructionProgressBar);
        }
        this.setId("building-holder");
        imageView.setEffect(glow);
        imageView.setOnMouseEntered(event -> glow.setLevel(0.5));
        imageView.setOnMouseExited(event -> glow.setLevel(0));
        imageView.setOnMouseClicked(event -> {
            BuildingMenuController.getInstance().handleClickOnBuilding(building);
        });
    }

    public void refresh() {
        if (building.isUnderConstruct()) {
            if (!constructionProgressBar.isVisible()) {
                constructionProgressBar.setVisible(true);
            }
            constructionProgressBar.setValues();
        } else if (constructionProgressBar != null && constructionProgressBar.isVisible()) {
            constructionProgressBar.setVisible(false);
        }

        if (building.getHitPoints() < building.getMaxHitPoints()) {
            if (!hitPointsProgressBar.isVisible()) {
                hitPointsProgressBar.setVisible(true);
            }
            hitPointsProgressBar.setValues();
        } else if (hitPointsProgressBar.isVisible()){
            hitPointsProgressBar.setVisible(false);
        }
    }
}
