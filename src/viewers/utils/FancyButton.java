package viewers.utils;

import controllers.BuildingMenuController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class FancyButton extends VBox {
    private ButtonActionType type;
    private Label label = new Label();
    private ImageView icon = new ImageView();

    public FancyButton(ButtonActionType type) {
        this.setId("fancy-button");
        this.setAlignment(Pos.CENTER);
        this.type = type;
        this.label.setText(type.toString());
        this.icon.setFitHeight(Const.FANCY_BUTTON_ICON_SIZE);
        this.icon.setFitWidth(Const.FANCY_BUTTON_ICON_SIZE);
        setImage();

        this.getChildren().addAll(icon, label);
        this.setOnMouseClicked(event -> BuildingMenuController.getInstance().handleClickOnButton(type));
    }

    private void setImage() {
        icon.setImage(ImageLibrary.valueOf(type.toString().replace(" ", "") + "Icon").getImage());
    }
}
