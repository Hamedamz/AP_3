package viewers.utils.fancyPopups;

import controllers.BuildingMenuController;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import models.GameLogic.Entities.Buildings.TownHall;
import models.GameLogic.Entities.Entity;
import viewers.AppGUI;
import viewers.utils.*;

public class InfoPopup extends ModelPopup {

    private ImageView imageView = new ImageView();
    private VBox imagePane = new VBox(imageView);
    private VBox progressBarContainer = new VBox(Const.SPACING);
    private VBox propertyInfoItemContainer = new VBox(Const.SPACING);

    private InfoPopup(Object model) {
        super(model);
        setBody(new HBox(imagePane, new VBox(Const.SPACING * 2, progressBarContainer, propertyInfoItemContainer)));
        setProperties();
    }

    private void setProperties() {
        imagePane.setMinWidth(Const.POPUP_WIDTH / 2);
        imagePane.setAlignment(Pos.CENTER);
        if (model.getClass().equals(TownHall.class)) {
            imageView.setFitWidth(Const.TOWNHALL_TILE_WIDTH / 2);
            imageView.setFitHeight(Const.TOWNHALL_TILE_HEIGHT / 2);
        }

        wrapper.setMinHeight(Const.POPUP_HEIGHT);
    }

    public static void openPopup(Object model, InfoPopupItems items) {

        InfoPopup infoPopup = new InfoPopup(model).withImage(((Entity) model).getImageView());

        for (ProgressBarType progressBarType : items.getProgressBarTypes()) {
            infoPopup.withProgressBarItem(new ProgressBarItem(progressBarType, model));
        }

        for (PropertyInfoType propertyInfoType : items.getPropertyInfoTypes()) {
            infoPopup.withPropertyInfoItem(new PropertyInfoItem(propertyInfoType, model));
        }
        infoPopup.show(AppGUI.getMainStage());
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
