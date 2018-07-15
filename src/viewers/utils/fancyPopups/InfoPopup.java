package viewers.utils.fancyPopups;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import models.GameLogic.Entities.Buildings.TownHall;
import models.GameLogic.Entities.Entity;
import viewers.AppGUI;
import viewers.utils.*;

public class InfoPopup extends FancyPopup {

    private ImageView imageView = new ImageView();
    private VBox imagePane = new VBox(imageView);
    private VBox progressBarContainer = new VBox(Const.SPACING);
    private VBox propertyInfoItemContainer = new VBox(Const.SPACING);

    private InfoPopup(Object model) {
        super(model);
        GridPane gridPane = new GridPane();
        gridPane.add(imagePane, 0 , 0, 1, 2);
        gridPane.add(progressBarContainer, 1 , 0);
        gridPane.add(propertyInfoItemContainer, 1 , 1);
        gridPane.setVgap(Const.SPACING * 2);
        setBody(gridPane);
        setProperties();
        setAnimationTimer(this);
    }

    private void setProperties() {
        imagePane.setMinWidth(Const.POPUP_WIDTH / 2);
        imagePane.setAlignment(Pos.CENTER);
        if (model.getClass().equals(TownHall.class)) {
            imageView.setFitWidth(Const.TOWNHALL_TILE_WIDTH / 2);
            imageView.setFitHeight(Const.TOWNHALL_TILE_HEIGHT / 2);
        }
    }

    public static void openPopup(Object model, InfoPopupItems items) {

        InfoPopup infoPopup = new InfoPopup(model).withImage(((Entity) model).getImageView());

        for (ProgressBarType progressBarType : items.getProgressBarTypes()) {
            infoPopup.withProgressBarItem(new ProgressBarItem(progressBarType, model));
        }

        for (PropertyInfoType propertyInfoType : items.getPropertyInfoTypes()) {
            infoPopup.withPropertyInfoItem(new PropertyInfoItem(propertyInfoType, model));
        }
        infoPopup.show(AppGUI.getMyVillageScene());
        infoPopup.requestFocus();
    }

    private void setAnimationTimer(InfoPopup popup) {
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                popup.refresh();
            }
        };
    }

    private void refresh() {
        for (Node  node: progressBarContainer.getChildren()) {
            ProgressBarItem progressBarItem = (ProgressBarItem) node;
            progressBarItem.setValues();
        }
    }

    public InfoPopup withImage(ImageView imageView) {
        this.imageView.setImage(imageView.getImage());
        this.imageView.setViewport(imageView.getViewport());
        return this;
    }

    private InfoPopup withProgressBarItem(ProgressBarItem progressBarItem) {
        progressBarContainer.getChildren().add(progressBarItem);
        return this;
    }

    private InfoPopup withPropertyInfoItem(PropertyInfoItem propertyInfoItem) {
        propertyInfoItemContainer.getChildren().add(propertyInfoItem);
        return this;
    }

}
