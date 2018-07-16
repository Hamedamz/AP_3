package controllers;

import models.GameLogic.Entities.Buildings.Barracks;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Camp;
import viewers.AppGUI;
import viewers.utils.*;
import viewers.utils.fancyPopups.ArmyStatusPopup;
import viewers.utils.fancyPopups.InfoPopup;
import viewers.utils.fancyPopups.TrainTroopsPopup;
import viewers.utils.fancyPopups.UpgradePopup;
import viewers.utils.ButtonActionType;

import java.util.Arrays;
import java.util.List;

import static viewers.utils.ButtonActionType.*;

public class BuildingMenuController {
    private static BuildingMenuController buildingMenuController = new BuildingMenuController();

    public static BuildingMenuController getInstance() {
        return buildingMenuController;
    }

    private Building building;
    private BuildingMenu activeMenu;
    private BuildingMenu menu;
    private BuildingMenu barracksMenu;
    private BuildingMenu campMenu;

    private BuildingMenuController() {
        menu = new BuildingMenu(OPEN_INFO_POPUP, OPEN_UPGRADE_POPUP);
        barracksMenu = new BuildingMenu(OPEN_INFO_POPUP, OPEN_UPGRADE_POPUP, OPEN_TRAIN_TROOPS_POPUP);
        campMenu = new BuildingMenu(OPEN_INFO_POPUP, OPEN_ARMY_STATUS_POPUP);
    }

    public void handleClickOnBuilding(Building building) {
        ShopScrollMenu.getInstance().hide();
        if (building == this.building && activeMenu != null) {
            activeMenu.toggle();
        } else {
            setBuilding(building);
            if (activeMenu != null && activeMenu.isVisible()) {
                activeMenu.toggle();
            }
            setActiveMenu(getMenuToOpen(building));
            activeMenu.toggle();
        }
        if (activeMenu != null) {
            activeMenu.setTitle(building.getClass().getSimpleName() + " " + building.getID().getCount());
        }
    }

    private BuildingMenu getMenuToOpen(Building building) {
        if (building.getClass().equals(Barracks.class)) {
            return barracksMenu;
        } else if(building.getClass().equals(Camp.class)) {
            return campMenu;
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
                TrainTroopsPopup.openPopup(building);
                break;
            case OPEN_ARMY_STATUS_POPUP:
                ArmyStatusPopup.openPopup(building);
                break;
            case OPEN_BUILD_MENU:
                break;
            case OPEN_ATTACK_MENU:
                break;
            case NONE:
                break;
        }
    }

    public void toggleActiveMenu() {
        if (activeMenu != null) {
            activeMenu.toggle();
        }
    }

    public void hideActiveMenu() {
        if (activeMenu != null){
            activeMenu.hide();
        }
    }

    public List<BuildingMenu> getMenus() {
        return Arrays.asList(menu, barracksMenu, campMenu);
    }
}
