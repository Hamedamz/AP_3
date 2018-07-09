package viewers.utils;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class FancyButton extends VBox {
    private ButtonActionType type;
    private Text label = new Text();
    private ImageView icon = new ImageView();
    private StackPane wrapper = new StackPane(icon);

    public FancyButton(ButtonActionType type) {
        super(Const.SPACING / 2);
        this.setAlignment(Pos.CENTER);
        this.type = type;
        this.icon.setFitHeight(Const.FANCY_BUTTON_ICON_SIZE);
        this.icon.setFitWidth(Const.FANCY_BUTTON_ICON_SIZE);
        this.label.setText(type.toString());
        setImage();

        this.getChildren().addAll(wrapper, label);
    }

    abstract void setImage();

    public ButtonActionType getType() {
        return type;
    }

    public Text getLabel() {
        return label;
    }

    public ImageView getIcon() {
        return icon;
    }

    public Pane getWrapper() {
        return wrapper;
    }
}
