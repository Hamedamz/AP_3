package viewers.utils;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FancyButton extends Pane {
    private ButtonActionType type;
    private Label label = new Label();
    private ImageView icon = new ImageView();

    public FancyButton(ButtonActionType type) {
        this.type = type;
        this.label.setText(type.toString());
        setImage();

        this.getChildren().addAll(new Rectangle(50, 50, Color.WHITE), new VBox(icon, label));
    }

    private void setImage() {
        icon.setImage(ImageLibrary.valueOf(type.toString().replace(" ", "") + "Icon").getImage());
    }
}
