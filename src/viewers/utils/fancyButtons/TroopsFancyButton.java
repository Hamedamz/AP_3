package viewers.utils.fancyButtons;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import viewers.utils.ButtonActionType;
import viewers.utils.Const;

public class TroopsFancyButton extends EntityFancyButton {

    private Label numberBadge = new Label();
    private Button incrementButton;
    private Button decrementButton;
    private boolean isButtonsShow = false;

    public TroopsFancyButton(ButtonActionType type, String clazz) {
        super(type, clazz);
        this.getIcon().setFitHeight(Const.FANCY_BUTTON_ICON_SIZE + 10);
        this.getIcon().setFitWidth(Const.FANCY_BUTTON_ICON_SIZE + 10);
        this.numberBadge.setId("number-badge");
        this.getWrapper().getChildren().add(new Pane(numberBadge));
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

    public Button getIncrementButton() {
        return incrementButton;
    }

    public Button getDecrementButton() {
        return decrementButton;
    }

    public void showButtons() {
        if (!isButtonsShow) {
            incrementButton = new Button("+");
            decrementButton = new Button("-");
            incrementButton.setMinWidth(28);
            decrementButton.setMinWidth(28);
            incrementButton.setId("green-round-button");
            decrementButton.setId("red-round-button");
            this.getChildren().add(new HBox(8, decrementButton, incrementButton));
            isButtonsShow = true;
        }
    }

    public int getNumber() {
        return Integer.parseInt(numberBadge.getText());
    }
}
