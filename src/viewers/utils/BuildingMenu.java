package viewers.utils;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import models.GameLogic.Entities.Buildings.Building;
import viewers.AppGUI;

public class BuildingMenu extends Popup {
    private Building building;
    private Pane content;
    private HBox buttonsContainer = new HBox(Const.SPACING);

    public BuildingMenu(ButtonActionType... actions) {
        for (ButtonActionType action : actions) {
            buttonsContainer.getChildren().add(new FancyButton(action));
        }
        content = new Pane(buttonsContainer);
        this.getContent().addAll(content);
        this.setAnchorY(Const.WINDOW_HEIGHT - 100);
        this.setAnchorX(Const.WINDOW_WIDTH / 2);
    }

    public void toggle() {
        if (isShowing()) {
            hide();
        } else {
            show(AppGUI.getMainStage());
        }
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
