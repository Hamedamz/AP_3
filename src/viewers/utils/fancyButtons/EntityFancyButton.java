package viewers.utils.fancyButtons;

import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import models.GameLogic.utills.StringUtils;
import viewers.utils.ButtonActionType;
import viewers.utils.Const;
import viewers.utils.ImageLibrary;

public class EntityFancyButton extends FancyButton {

    public static final double SIZE = Const.FANCY_BUTTON_ICON_SIZE + 10;
    private String clazz;

    public EntityFancyButton(ButtonActionType type, String clazz) {
        super(type);
        this.clazz = clazz;
        this.getWrapper().setId("blue-fancy-button");
        this.getIcon().setFitHeight(SIZE);
        this.getIcon().setFitWidth(Const.FANCY_BUTTON_ICON_SIZE + 10);
        this.getLabel().setId("round-fancy-button-label");
        this.getLabel().setTextAlignment(TextAlignment.CENTER);
        setLabel();
        setImage();
    }

    @Override
    void setImage() {
        getIcon().setViewport(new Rectangle2D(0, Const.ENTITY_TILE_HEIGHT - Const.ENTITY_TILE_WIDTH, Const.ENTITY_TILE_WIDTH, Const.ENTITY_TILE_WIDTH));
        getIcon().setClip(new Circle(SIZE / 2 ,SIZE / 2,SIZE / 2));
        getIcon().setImage(ImageLibrary.valueOf(clazz).getImage());
    }

    private void setLabel() {
        getLabel().setText(StringUtils.stringSeparator(clazz));
    }

    public String getClazz() {
        return clazz;
    }
}
