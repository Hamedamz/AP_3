package viewers.utils.entityHolders;

import controllers.BuildingMenuController;
import javafx.scene.effect.Glow;
import models.GameLogic.Builder;
import models.GameLogic.Entities.Buildings.*;
import viewers.utils.*;

public class BuildingHolder extends EntityHolder {
    private Builder builder;
    private ProgressBarItem constructionProgressBar;
    private Glow glow = new Glow(0);

    public BuildingHolder(Building building) {
        super(building);
        initialize();
    }

    public BuildingHolder(Builder builder) {
        super(builder.getUnderConstructBuilding());
        this.builder = builder;
        initialize();
    }

    @Override
    public void initialize() {
        super.initialize();

        if (entity.getClass().equals(TownHall.class)) {
            imageView.setFitWidth(Const.TOWNHALL_TILE_WIDTH * Const.TILE_SCALE);
            imageView.setFitHeight(Const.TOWNHALL_TILE_HEIGHT * Const.TILE_SCALE);
        }
        if (builder != null) {
            constructionProgressBar = new ProgressBarItem(ProgressBarType.REMAINED_TIME, builder);
            this.getChildren().add(constructionProgressBar);
        }

        imageView.setEffect(glow);
        imageView.setOnMouseEntered(event -> glow.setLevel(0.5));
        imageView.setOnMouseExited(event -> glow.setLevel(0));
        imageView.setOnMouseClicked(event -> {
            if (entity instanceof GoldStorage || entity instanceof GoldMine) {
                SoundPlayer.play(Sounds.goldSound);
            }
            else if (entity instanceof ElixirStorage || entity instanceof ElixirMine) {
                SoundPlayer.play(Sounds.elixirSound);
            }
            else {
                SoundPlayer.play(Sounds.buildingClickSound);
            }
            BuildingMenuController.getInstance().handleClickOnBuilding((Building) entity);
        });
    }

    @Override
    public void refresh() {
        if (((Building) entity).isUnderConstruct()) {
            if (!constructionProgressBar.isVisible()) {
                constructionProgressBar.setVisible(true);
            }
            constructionProgressBar.setValues();
        } else if (constructionProgressBar != null && constructionProgressBar.isVisible()) {
            constructionProgressBar.setVisible(false);
        }

        if (((Building) entity).getHitPoints() < ((Building) entity).getMaxHitPoints()) {
            if (!hitPointsProgressBar.isVisible()) {
                hitPointsProgressBar.setVisible(true);
            }
            hitPointsProgressBar.setValues();
        } else if (hitPointsProgressBar.isVisible()){
            hitPointsProgressBar.setVisible(false);
        }
    }
}
