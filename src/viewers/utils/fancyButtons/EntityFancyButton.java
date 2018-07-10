package viewers.utils.fancyButtons;

import javafx.geometry.Rectangle2D;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import models.GameLogic.utills.StringUtils;
import viewers.utils.Const;
import viewers.utils.ImageLibrary;

public class EntityFancyButton extends FancyButton {

    private String clazz;
    private ColorAdjust grayScale;

    public EntityFancyButton(ButtonActionType type, String clazz) {
        super(type);
        this.clazz = clazz;
        this.getWrapper().setId("blue-fancy-button");
        this.getLabel().setId("round-fancy-button-label");
        this.getLabel().setTextAlignment(TextAlignment.CENTER);
        grayScale = new ColorAdjust();
        grayScale.setSaturation(0);
        this.setEffect(grayScale);
        setLabel();
        setImage();
    }

    @Override
    void setImage() {
        getIcon().setViewport(new Rectangle2D(0, Const.ENTITY_TILE_HEIGHT - Const.ENTITY_TILE_WIDTH, Const.ENTITY_TILE_WIDTH, Const.ENTITY_TILE_WIDTH));
        getIcon().setClip(new Circle(Const.FANCY_BUTTON_ICON_SIZE / 2 ,Const.FANCY_BUTTON_ICON_SIZE / 2,Const.FANCY_BUTTON_ICON_SIZE / 2));
        getIcon().setImage(ImageLibrary.valueOf(clazz).getImage());
    }

    public void setActive(boolean active) {
        if (active) {
            grayScale.setSaturation(0);
        } else {
            grayScale.setSaturation(-1);
        }
    }

    private void setLabel() {
        getLabel().setText(StringUtils.stringSeparator(clazz));
    }

    public String getClazz() {
        return clazz;
    }
}
