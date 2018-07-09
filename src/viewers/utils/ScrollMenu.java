package viewers.utils;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public abstract class ScrollMenu extends ScrollPane {
    private String[] entities;
    private HBox buttons = new HBox(Const.SPACING * 2);

    public ScrollMenu(String[] entities) {
        this.entities = entities;
        this.setPannable(true);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
        this.setMaxWidth(Const.SCROLL_MENU_WIDTH);
        this.setLayoutX(Const.WINDOW_WIDTH / 2 - Const.SCROLL_MENU_WIDTH / 2);
        this.setLayoutY(Const.WINDOW_HEIGHT - Const.SCROLL_MENU_HEIGHT);
        this.setId("scroll-menu");

        this.setContent(buttons);
    }

    abstract void build();

    public String[] getEntities() {
        return entities;
    }

    public HBox getButtons() {
        return buttons;
    }
}
