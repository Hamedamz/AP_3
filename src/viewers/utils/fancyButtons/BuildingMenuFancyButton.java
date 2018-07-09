package viewers.utils.fancyButtons;

import controllers.BuildingMenuController;
import viewers.utils.ButtonActionType;
import viewers.utils.ImageLibrary;


public class BuildingMenuFancyButton extends FancyButton {

    public BuildingMenuFancyButton(ButtonActionType type) {
        super(type);
        this.setId("yellow-fancy-button");
        setImage();

        this.setOnMouseClicked(event -> BuildingMenuController.getInstance().handleClickOnButton(type));
    }

    @Override
    void setImage() {
        getIcon().setImage(ImageLibrary.valueOf(getType().toString().replace(" ", "") + "Icon").getImage());
    }


}
