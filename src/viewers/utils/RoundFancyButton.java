package viewers.utils;

public class RoundFancyButton extends FancyButton {
    public RoundFancyButton(ButtonActionType type, String color) {
        super(type);
        this.getWrapper().setId("red-fancy-button");
        this.getLabel().setId("round-fancy-button-label");
    }

    @Override
    void setImage() {
        getIcon().setImage(ImageLibrary.valueOf(getType().toString().replace(" ", "") + "Icon").getImage());
    }
}
