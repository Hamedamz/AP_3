package viewers.utils;

import controllers.BuildingMenuController;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import models.GameLogic.Builder;
import models.GameLogic.Entities.Buildings.Building;

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
        this.imageView = building.getImageView();
        this.setMaxSize(imageView.getViewport().getWidth(), imageView.getViewport().getHeight());
        hitPointsProgressBar = new ProgressBarItem(1,1, ProgressBarType.HIT_POINTS);
        constructionProgressBar = new ProgressBarItem(1,1, ProgressBarType.REMAINED_TIME);
        this.getChildren().addAll(imageView, constructionProgressBar, hitPointsProgressBar);
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
            constructionProgressBar.setMax(builder.getTotalConstructionTime());
            constructionProgressBar.setValue(builder.getTotalConstructionTime() - builder.getConstructRemainingTime());
            constructionProgressBar.setVisible(true);
        } else {
            constructionProgressBar.setVisible(false);
        }

        if (building.getHitPoints() < building.getMaxHitPoints()) {
            hitPointsProgressBar.setMax(building.getMaxHitPoints());
            hitPointsProgressBar.setValue(building.getHitPoints());
            hitPointsProgressBar.setVisible(true);
        } else {
            hitPointsProgressBar.setVisible(false);
        }
    }
}
