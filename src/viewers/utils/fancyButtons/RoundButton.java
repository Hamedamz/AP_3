package viewers.utils.fancyButtons;

import javafx.scene.control.Button;

public class RoundButton extends Button {
    public RoundButton(String text, String color) {
        super(text);
        setId(color + "-round-button");
    }
}
