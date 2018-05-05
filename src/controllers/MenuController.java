package controllers;

import controllers.Exceptions.InvalidInputException;
import controllers.enums.*;
import models.Menu.*;
import viewers.MenuViewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class MenuController {
    public static final String NUMBER = "\\d+";

    private static MenuViewer menuViewer = new MenuViewer();
    private static Stack<Menu> menuStack = new Stack<>();
    private static Menu entranceMenu;
    private static Menu villageMenu;
    private static HashMap<String, Menu> modelBasedMenus;

    public MenuController() {
        loadEntranceMenu();
        loadVillageMenu();
        loadModelBasedMenus();
    }

    private void loadEntranceMenu() {
        if (entranceMenu == null) {
            entranceMenu = buildEntranceMenu();
        }
    }

    private void loadVillageMenu() {
        if (villageMenu == null) {
            villageMenu = buildVillageMenu();
        }
    }

    private void loadModelBasedMenus() {
        if (modelBasedMenus == null) {
            modelBasedMenus = new HashMap<>();
            modelBasedMenus.put("TownHall", buildTownHallMenu());
            modelBasedMenus.put("Barracks", buildBarracksMenu());
            modelBasedMenus.put("Camp", buildCampMenu());
            modelBasedMenus.put("Mine", buildMinesMenu());
            modelBasedMenus.put("Storage", buildStorageMenu());
            modelBasedMenus.put("DefensiveBuilding", buildDefensiveBuildingsMenu());
            modelBasedMenus.put("Map", buildMapMenu());
        }
    }

    public Menu getEntranceMenu() {
        return entranceMenu;
    }

    public Menu getVillageMenu() {
        return villageMenu;
    }

    public HashMap<String, Menu> getModelBasedMenus() {
        return modelBasedMenus;
    }

    public void openMenu(Menu menu) {
//        updateDynamicMenu(menu);
        menuStack.push(menu);
    }

    public void back() {
        menuStack.pop();
    }

    public void printMenu() {
        menuViewer.printMenu(menuStack.peek());
    }

    public void printMenu(Menu menu) {
        menuViewer.printMenu(menu);
    }

    public boolean isMenuItemNumber(String command) {
        return command.matches(NUMBER);
    }

    MenuItem getRequestedMenuItem(String command) {
        Menu menu = menuStack.peek();
        int maxIndex = menu.getItems().size() + menu.getDynamicItems().size() - 1;
        try {
            int menuItemIndex = Integer.parseInt(command) - 1;
            if (menuItemIndex > maxIndex || menuItemIndex < 0) {
                throw new InvalidInputException("Your input is out of range.");
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
        return null;
    }

    private void updateDynamicMenu(Menu menu) {
        DynamicListType listType = menu.getDynamicListType();
        ArrayList<DynamicMenuItem> list = null;

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

    private ArrayList<DynamicMenuItem> getAvailableMapsList() {
        // enemy maps that can be attacked
        return null;
    }

    private ArrayList<DynamicMenuItem> getTargetList() {
        // unknown
        return null;
    }

    private ArrayList<DynamicMenuItem> getMineList() {
        // unknown
        return null;
    }

    private ArrayList<DynamicMenuItem> getAvailableTroopsList() {
        // troops and number of each
        return null;
    }

    private ArrayList<DynamicMenuItem> getTrainingStatusList() {
        // troop that are being trained with turns left ro be trained
        return null;
    }

    private ArrayList<DynamicMenuItem> getTroopsList() {
        // all troops with labels A and U that show availability
        // if troop can be built the possible number is also shown
        return null;
    }

    private ArrayList<DynamicMenuItem> getAvailableBuildingsList() {
        // buildings than can be built based on resources and aren't built yet
        return null;
    }

    private ArrayList<DynamicMenuItem> getConstructionStatusList() {
        // under construction buildings with turns left to be built
        return null;
    }

    private ArrayList<DynamicMenuItem> getBuildingsList() {
        // all game buildings
        return null;
    }

    private Menu buildEntranceMenu() {
        return MenuBuilder.aMenu()
                .withLabel("main")
                .withItem(new MenuItem(CommandType.NEW_GAME))
                .withItem(new MenuItem(CommandType.LOAD_GAME))
                .build();
    }

    private Menu buildVillageMenu() {
        return MenuBuilder.aMenu()
                .withLabel("village")
                .withItem(new MenuItem(CommandType.BACK))
                .withItem(buildMenuWithDynamicItems("buildings", DynamicListType.BUILDINGS_LIST))
                .withItem(new MenuItem(CommandType.RESOURCES_INFO))
                .withItem(buildAttackMenu())
                .build();
    }

    private Menu buildAttackMenu() {
        return MenuBuilder.aMenu()
                .withLabel("attack")
                .withItem(new MenuItem(CommandType.BACK))
                .withItem(new MenuItem(CommandType.LOAD_MAP))
                .withDynamicList(DynamicListType.AVAILABLE_MAPS_LIST)
                .build();
    }

    private Menu buildMapMenu() {
        return MenuBuilder.aMenu()
                .withLabel("map")
                .withItem(new MenuItem(CommandType.MAP_INFO))
                .withItem(new MenuItem(CommandType.ATTACK_MAP))
                .build();
    }

    private MenuItem buildMenuWithDynamicItems(String label, DynamicListType dynamicListType) {
        return MenuBuilder.aMenu()
                .withLabel(label)
                .withItem(new MenuItem(CommandType.BACK))
                .withDynamicList(dynamicListType)
                .build();
    }

    private Menu buildTypicalBuildingMenu() {
        return MenuBuilder.aMenu()
                .withItem(new MenuItem(CommandType.BACK))
                .withItem(buildInfoMenu())
                .build();
    }

    private Menu buildInfoMenu() {
        return MenuBuilder.aMenu()
                .withLabel("info")
                .withItem(new MenuItem(CommandType.BACK))
                .withItem(new MenuItem(CommandType.OVERALL_INFO))
                .withItem(new MenuItem(CommandType.UPGRADE_INFO))
                .build();
    }

    private Menu buildTownHallMenu() {
        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("town hall")
                .withItem(buildMenuWithDynamicItems("available buildings", DynamicListType.AVAILABLE_BUILDINGS_LIST))
                .withItem(buildMenuWithDynamicItems("status", DynamicListType.CONSTRUCTION_STATUS_LIST))
                .build();
    }

    private Menu buildBarracksMenu() {
        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("barracks")
                .withItem(buildMenuWithDynamicItems("build soldiers", DynamicListType.TROOPS_LIST))
                .withItem(buildMenuWithDynamicItems("status", DynamicListType.TRAINING_STATUS_LIST))
                .build();
    }

    private Menu buildCampMenu() {
        Menu infoMenu = MenuBuilder.aMenuExtending(buildInfoMenu())
                .withItem(new MenuItem(CommandType.CAPACITY_INFO))
                .build();

        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("camp")
                .withItem(infoMenu)
                .withItem(buildMenuWithDynamicItems("soldiers", DynamicListType.AVAILABLE_TROOPS_LIST))
                .build();
    }

    private Menu buildMinesMenu() {
        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("mines")
                .withItem(buildMenuWithDynamicItems("mine", DynamicListType.MINE))
                .build();
    }

    private Menu buildStorageMenu() {
        Menu infoMenu = MenuBuilder.aMenuExtending(buildInfoMenu())
                .withItem(new MenuItem(CommandType.RESOURCES_INFO))
                .build();

        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("storage")
                .withItem(infoMenu)
                .build();
    }

    private Menu buildDefensiveBuildingsMenu() {
        Menu infoMenu = MenuBuilder.aMenuExtending(buildInfoMenu())
                .withItem(new MenuItem(CommandType.ATTACK_INFO))
                .build();

        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("")
                .withItem(infoMenu)
                .withItem(buildMenuWithDynamicItems("target", DynamicListType.TARGET))
                .build();
    }
}