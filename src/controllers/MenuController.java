package controllers;

import controllers.Exceptions.InvalidInputException;
import controllers.enums.DynamicListType;
import models.GameLogic.Village;
import models.Menu.Menu;
import models.Menu.MenuItem;
import viewers.MenuViewer;

import java.util.ArrayList;

public class MenuController {
    private MenuViewer menuViewer;

    public MenuController(Village village) {
        menuViewer = new MenuViewer(village);
    }

    public Menu initializeVillageMenus() {

    }

    public void OpenMenu(Menu menu) {
        updateDynamicMenu(menu);
        menuViewer.printMenu(menu);
    }

    public MenuItem getUserInput(Menu menu) {
        int maxIndex = menu.getItems().size() + menu.getDynamicItems().size() - 1;
        while (true) {
            try {
                int menuItemIndex = menuViewer.getMenuItemIndex();
                if (menuItemIndex > maxIndex || menuItemIndex < 0) {
                    throw new InvalidInputException("Your input is out of range.")
                } else {
                    if (menuItemIndex < menu.getItems().size()) {
                        return menu.getItems().get(menuItemIndex);
                    } else {
                        return menu.getDynamicItems().get(menuItemIndex - menu.getItems().size());
                    }
                }
            } catch (InvalidInputException e) {
                menuViewer.printErrorMessage(e.getMessage());
            }
        }
    }

    private void updateDynamicMenu(Menu menu) {
        DynamicListType listType = menu.getDynamicListType();
        ArrayList<MenuItem> list = null;

        switch (listType) {
            case EMPTY:
                break;
            case BUILDINGS_LIST:
                list = getBuildingsList();
                break;
            case CONSTRUCTION_STATUS_LIST:
                list = getConstructionStatusList();
                break;
            case AVAILABLE_BUILDINGS_LIST:
                list = getAvailableBuildingsList();
                break;
            case TROOPS_LIST:
                list = getTroopsList();
                break;
            case TRAINING_STATUS_LIST:
                list = getTrainingStatusList();
                break;
            case AVAILABLE_TROOPS_LIST:
                list = getAvailableTroopsList();
                break;
            case MINE:
                list = getMineList(); // UNKNOWN
                break;
            case TARGET:
                list = getTargetList(); // UNKNOWN
                break;
            case AVAILABLE_MAPS_LIST:
                list = getAvailableMapsList();
                break;
        }

        menu.updateDynamicItems(list);
    }

    private ArrayList<MenuItem> getAvailableMapsList() {
        // enemy maps that can be attacked
        return null;
    }

    private ArrayList<MenuItem> getTargetList() {
        // unknown
        return null;
    }

    private ArrayList<MenuItem> getMineList() {
        // unknown
        return null;
    }

    private ArrayList<MenuItem> getAvailableTroopsList() {
        // troops and number of each
        return null;
    }

    private ArrayList<MenuItem> getTrainingStatusList() {
        // troop that are being trained with turns left ro be trained
        return null;
    }

    private ArrayList<MenuItem> getTroopsList() {
        // all troops with labels A and U that show availability
        // if troop can be built the possible number is also shown
        return null;
    }

    private ArrayList<MenuItem> getAvailableBuildingsList() {
        // buildings than can be built based on resources and aren't built yet
        return null;
    }

    private ArrayList<MenuItem> getConstructionStatusList() {
        // under construction buildings with turns left to be built
        return null;
    }

    private ArrayList<MenuItem> getBuildingsList() {
        // all game buildings
        return null;
    }
}