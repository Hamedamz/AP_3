package viewers.utils.fancyButtons;

import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;
import viewers.utils.ButtonActionType;
import viewers.utils.Const;
import viewers.utils.ImageLibrary;

public class EntityFancyButton extends FancyButton {

    private String clazz;

    public EntityFancyButton(ButtonActionType type, String clazz) {
        super(type);
        this.clazz = clazz;
        this.getWrapper().setId("blue-fancy-button");
        this.getLabel().setId("round-fancy-button-label");
        setLabel();
        setImage();
    }

    @Override
    void setImage() {
        getIcon().setViewport(new Rectangle2D(0, Const.ENTITY_TILE_HEIGHT - Const.ENTITY_TILE_WIDTH, Const.ENTITY_TILE_WIDTH, Const.ENTITY_TILE_WIDTH));
        getIcon().setClip(new Circle(Const.FANCY_BUTTON_ICON_SIZE / 2 ,Const.FANCY_BUTTON_ICON_SIZE / 2,Const.FANCY_BUTTON_ICON_SIZE / 2));
        getIcon().setImage(ImageLibrary.valueOf(clazz).getImage());
    }

    private void setLabel() {
        getLabel().setText(clazz);
    }

    public String getClazz() {
        return clazz;
    }
}
