package viewers.utils.fancyButtons;

import javafx.scene.control.Label;

public class TroopsFancyButton extends EntityFancyButton {

    private Label numberBadge = new Label();

    public TroopsFancyButton(ButtonActionType type, String clazz) {
        super(type, clazz);
        this.numberBadge.setId("number-badge");
        this.getWrapper().getChildren().add(numberBadge);
    }

    public void hideLabel() {
        this.getChildren().remove(getLabel());
    }

    public void showLabel() {
        this.getChildren().add(getLabel());
    }

    public void hideNumberBadge() {
        this.numberBadge.setVisible(false);
    }

    public void showNumberBadge() {
        this.numberBadge.setVisible(true);
    }

    public void setNumberBadge(int number) {
        if (number > 0) {
            showNumberBadge();
            setActive(true);
            this.numberBadge.setText(String.valueOf(number));
        } else {
            hideNumberBadge();
            setActive(false);
        }
    }
}
