package viewers.utils.entityHolders;

import controllers.BuildingMenuController;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import models.GameLogic.Builder;
import models.GameLogic.Entities.Buildings.*;
import viewers.utils.*;

public class BuildingHolder extends EntityHolder {
    private Builder builder;
    private ProgressBarItem constructionProgressBar;
    private Glow glow = new Glow(0);
    private ColorAdjust grayScale = new ColorAdjust();

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

        grayScale.setSaturation(0);
        this.setEffect(grayScale);

        imageView.setEffect(glow);
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
            if (((Building) entity).isDestroyed()){
                if (!isDestroyed()) {
                    this.setDestroyed(true);
                    SoundPlayer.play(Sounds.buildingDestroyedSound);
                    grayScale.setSaturation(-1);
                }
                return;
            }
            hitPointsProgressBar.setValues();
        } else if (hitPointsProgressBar.isVisible()){
            hitPointsProgressBar.setVisible(false);
        }
    }

    public Glow getGlow() {
        return glow;
    }
}
