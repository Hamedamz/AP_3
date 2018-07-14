package viewers.utils.fancyButtons;

import javafx.geometry.Pos;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import viewers.utils.ButtonActionType;
import viewers.utils.Const;

public abstract class FancyButton extends VBox {
    private ButtonActionType type;
    private Text label = new Text();
    private ImageView icon = new ImageView();
    private StackPane wrapper = new StackPane(icon);
    private ColorAdjust grayScale;
    private boolean isActive;

    public FancyButton(ButtonActionType type) {
        super(Const.SPACING / 2);
        this.setAlignment(Pos.TOP_CENTER);
        this.type = type;
        this.icon.setFitHeight(Const.FANCY_BUTTON_ICON_SIZE);
        this.icon.setFitWidth(Const.FANCY_BUTTON_ICON_SIZE);
        this.label.setText(type.toString());
        this.wrapper.setMaxWidth(Const.FANCY_BUTTON_ICON_SIZE);

        grayScale = new ColorAdjust();
        grayScale.setSaturation(0);
        StackPane pane = new StackPane(wrapper);
        pane.setEffect(grayScale);
        this.getChildren().addAll(pane, label);

        // TODO: 7/10/2018 Arshia move all button sound to here
//        this.setOnMousePressed(event -> play sound );
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

    public ColorAdjust getGrayScale() {
        return grayScale;
    }

    public void setActive(boolean active) {
        this.isActive = active;
        if (active) {
            grayScale.setSaturation(0);
        } else {
            grayScale.setSaturation(-1);
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void hideLabel() {
        this.getChildren().remove(getLabel());
    }

    public void showLabel() {
        this.getChildren().add(getLabel());
    }
}
