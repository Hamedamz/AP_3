package controllers;

import models.GameLogic.Entities.Buildings.Barracks;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Camp;
import viewers.utils.*;
import viewers.utils.FancyPopups.InfoPopup;
import viewers.utils.FancyPopups.UpgradePopup;

import static viewers.utils.ButtonActionType.*;

public class BuildingMenuController {
    private static BuildingMenuController buildingMenuController = new BuildingMenuController();

    public static BuildingMenuController getInstance() {
        return buildingMenuController;
    }

    private Building building;
    private BuildingMenu activeMenu;
    private BuildingMenu menu = new BuildingMenu(OPEN_INFO_POPUP, OPEN_UPGRADE_POPUP);
    private BuildingMenu armyMenu = new BuildingMenu(OPEN_INFO_POPUP, OPEN_UPGRADE_POPUP, OPEN_TRAIN_TROOPS_POPUP);

    private BuildingMenuController() {
    }

    public void handleClickOnBuilding(Building building) {
        if (building == this.building) {
            activeMenu.toggle();
        } else {
            setBuilding(building);
            if (activeMenu != null && activeMenu.isShowing()) {
                activeMenu.toggle();
            }
            setActiveMenu(getMenuToOpen(building));
            activeMenu.toggle();
        }
    }

    private BuildingMenu getMenuToOpen(Building building) {
        if (building.getClass().equals(Barracks.class) || building.getClass().equals(Camp.class)) {
            return armyMenu;
        } else {
            return menu;
        }
    }

    private void setActiveMenu(BuildingMenu activeMenu) {
        this.activeMenu = activeMenu;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Building getBuilding() {
        return building;
    }

    public void handleClickOnButton(ButtonActionType type) {
        switch (type) {
            case OPEN_INFO_POPUP:
                InfoPopup.openPopup(building, InfoPopupItems.valueOf(building.getClass().getSimpleName() + "Info"));
                break;
            case OPEN_UPGRADE_POPUP:
                UpgradePopup.openPopup(building);
                break;
            case OPEN_TRAIN_TROOPS_POPUP:
                break;
            case OPEN_BUILD_MENU:
                break;
            case OPEN_ATTACK_MENU:
                break;
        }
    }

    public void toggleActiveMenu() {
        if (activeMenu != null) {
            activeMenu.toggle();
        }
    }
}
