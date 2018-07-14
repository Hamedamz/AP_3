package viewers.utils.riverMenu;

import javafx.scene.Node;
import javafx.scene.control.Button;
import viewers.utils.ButtonActionType;

public class RiverButton extends Button {
    private int index;
    private ButtonActionType type;

    public RiverButton(ButtonActionType type, int index) {
        super(type.toString());
        this.type = type;
        this.index = index;
        this.setId("river-button");
        this.setMaxWidth(Double.MAX_VALUE);
        setEvents();
    }

    public RiverButton(ButtonActionType type, Node graphic, int index) {
        super(type.toString(), graphic);
        this.type = type;
        this.index = index;
        this.setId("river-button");
        this.setMaxWidth(Double.MAX_VALUE);
        setEvents();
    }

    private void setEvents() {
        this.setOnAction(event -> {
            RiverMenu.getInstance().openMenu(type);
        });
    }
}
