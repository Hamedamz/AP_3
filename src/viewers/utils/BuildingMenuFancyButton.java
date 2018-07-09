package viewers.utils;

import controllers.BuildingMenuController;


public class BuildingMenuFancyButton extends FancyButton {

    public BuildingMenuFancyButton(ButtonActionType type) {
        super(type);
        this.setId("yellow-fancy-button");

        this.setOnMouseClicked(event -> BuildingMenuController.getInstance().handleClickOnButton(type));
    }

    @Override
    void setImage() {
        getIcon().setImage(ImageLibrary.valueOf(getType().toString().replace(" ", "") + "Icon").getImage());
    }


}
