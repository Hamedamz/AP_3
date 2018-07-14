package viewers.utils.fancyButtons;

import controllers.BuildingMenuController;
import javafx.geometry.Pos;
import viewers.utils.ButtonActionType;
import viewers.utils.ImageLibrary;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;


public class BuildingMenuFancyButton extends FancyButton {

    public BuildingMenuFancyButton(ButtonActionType type) {
        super(type);
        this.setAlignment(Pos.CENTER);
        this.setId("yellow-fancy-button");
        setImage();

        this.setOnMouseClicked(event -> {
            BuildingMenuController.getInstance().handleClickOnButton(type);
            SoundPlayer.play(Sounds.buttonSound);
        });
    }

    @Override
    void setImage() {
        getIcon().setImage(ImageLibrary.valueOf(getType().toString().replace(" ", "") + "Icon").getImage());
    }


}
