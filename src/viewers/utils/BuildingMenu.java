package viewers.utils;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import models.GameLogic.Entities.Buildings.Building;

public class BuildingMenu extends Popup {
    private Building building;
    private Pane content;
    private HBox buttonsContainer;

    public BuildingMenu(Building building) {
        this.building = building;
        FancyButton infoButton = new FancyButton(ButtonActionType.OPEN_INFO_POPUP);
        FancyButton upgradeButton = new FancyButton(ButtonActionType.OPEN_UPGRADE_POPUP);
    }
}
