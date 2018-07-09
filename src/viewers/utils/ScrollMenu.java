package viewers.utils;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public abstract class ScrollMenu extends ScrollPane {
    private String[] entities;
    private HBox buttons = new HBox(Const.SPACING);

    public ScrollMenu(String[] entities) {
        this.entities = entities;
        this.setPannable(true);
        this.setMaxWidth(Const.WINDOW_WIDTH - 200);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
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
