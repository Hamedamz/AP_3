package controllers;

import controllers.Exceptions.InvalidInputException;
import controllers.enums.*;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Entity;
import models.GameLogic.World;
import models.Menu.*;
import viewers.MenuViewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import static controllers.enums.CommandType.*;
import static controllers.enums.DynamicListType.*;

public class MenuController {
    public static final String NUMBER = "\\d+";

    private World world;
    private MenuViewer menuViewer = new MenuViewer();
    private Stack<Menu> menuStack = new Stack<>();
    private Menu entranceMenu;
    private Menu villageMenu;
    private HashMap<String, Menu> modelBasedMenus;

    public MenuController(World world) {
        this.world = world;
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
        updateDynamicMenu(menu);
        if (isMenuOpen()) {
            menu.setModel(getActiveMenu().getModel());
        }
        menuStack.push(menu);
    }

    public void openMenu(Menu menu, Entity model) {
        updateDynamicMenu(menu);
        menu.setModel(model);
        menuStack.push(menu);
    }

    public boolean isMenuOpen() {
        return !menuStack.empty();
    }

    public void back() {
        menuStack.pop();
    }

    public void printMenu() {
        menuViewer.printMenu(getActiveMenu());
    }

    public void printMenu(Menu menu) {
        menuViewer.printMenu(menu);
    }

    public boolean isMenuItemNumber(String command) {
        return command.matches(NUMBER);
    }

    public MenuItem getRequestedMenuItem(String command) throws InvalidInputException {
        Menu menu = getActiveMenu();
        int maxIndex = menu.getItems().size() + menu.getDynamicItems().size() - 1;
        int menuItemIndex = Integer.parseInt(command) - 1;
        if (menuItemIndex > maxIndex || menuItemIndex < 0) {
            throw new InvalidInputException("input is out of range");
        } else {
            if (menuItemIndex < menu.getItems().size()) {
                return menu.getItems().get(menuItemIndex);
            } else {
                return menu.getDynamicItems().get(menuItemIndex - menu.getItems().size());
            }
        }
    }

    public Menu getActiveMenu() {
        return menuStack.peek();
    }

    private void updateDynamicMenu(Menu menu) {
        DynamicListType listType = menu.getDynamicListType();
        ArrayList<DynamicMenuItem> list = null;

        switch (listType) {
            case EMPTY:
                return;
            case BUILDINGS_LIST:
                list = getBuildingsList();
                break;
            case CONSTRUCTION_STATUS_LIST:
                list = getConstructionStatusList();
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
            case VILLAGE_MAPS_LIST:
                list = getVillageMapList();
                break;
            case AVAILABLE_ENEMY_MAPS_LIST:
                list = getAvailableMapsList();
                break;
        }

        menu.updateDynamicItems(list);
    }

    private ArrayList<DynamicMenuItem> getVillageMapList() {
        ArrayList<DynamicMenuItem> villageMapList = new ArrayList<>();
        for (String villageName : world.getVillagesNameAndPath().keySet()) {
            villageMapList.add(new DynamicMenuItem(LOAD_GAME, villageName));
        }
        return villageMapList;
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

    private ArrayList<DynamicMenuItem> getConstructionStatusList() {
        // under construction buildings with turns left to be built
        return null;
    }

    private ArrayList<DynamicMenuItem> getBuildingsList() {
        ArrayList<Building> buildings = world.getMyVillage().getMap().getBuildings();
        ArrayList<DynamicMenuItem> buildingsList = new ArrayList<>();
        for (Building building : buildings) {
            buildingsList.add(new DynamicMenuItem(OPEN_BUILDING_MENU, building));
        }
        return buildingsList;
    }

    private Menu buildEntranceMenu() {
        return MenuBuilder.aMenu()
                .withLabel("main")
                .withItem(new MenuItem(NEW_GAME))
                .withItem(buildLoadGameMenu())
                .build();
    }

    private Menu buildLoadGameMenu() {
        return MenuBuilder.aMenu()
                .withLabel("load game")
                .withItem(new MenuItem(BACK))
                .withItem(new MenuItem(LOAD_GAME_FROM_FILE))
                .withDynamicList(VILLAGE_MAPS_LIST)
                .build();
    }

    private Menu buildVillageMenu() {
        return MenuBuilder.aMenu()
                .withLabel("village")
                .withItem(new MenuItem(BACK))
                .withItem(buildMenuWithDynamicItems("buildings", BUILDINGS_LIST))
                .withItem(new MenuItem(RESOURCES_INFO))
                .withItem(buildAttackMenu())
                .build();
    }

    private Menu buildAttackMenu() {
        return MenuBuilder.aMenu()
                .withLabel("attack")
                .withItem(new MenuItem(BACK))
                .withItem(new MenuItem(LOAD_ENEMY_MAP))
                .withDynamicList(AVAILABLE_ENEMY_MAPS_LIST)
                .build();
    }

    private Menu buildMapMenu() {
        return MenuBuilder.aMenu()
                .withLabel("map")
                .withItem(new MenuItem(MAP_INFO))
                .withItem(new MenuItem(ATTACK_MAP))
                .build();
    }

    private Menu buildMenuWithDynamicItems(String label, DynamicListType dynamicListType) {
        return MenuBuilder.aMenu()
                .withLabel(label)
                .withItem(new MenuItem(BACK))
                .withDynamicList(dynamicListType)
                .build();
    }

    private Menu buildTypicalBuildingMenu() {
        return MenuBuilder.aMenu()
                .withItem(new MenuItem(BACK))
                .withItem(buildInfoMenu())
                .withItem(new MenuItem(UPGRADE_BUILDING))
                .build();
    }

    private Menu buildInfoMenu() {
        return MenuBuilder.aMenu()
                .withLabel("info")
                .withItem(new MenuItem(BACK))
                .withItem(new MenuItem(OVERALL_INFO))
                .withItem(new MenuItem(UPGRADE_INFO))
                .build();
    }

    private Menu buildTownHallMenu() {
        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("town hall")
                .withItem(buildAvailableBuildingsMenu())
                .withItem(buildMenuWithDynamicItems("status", CONSTRUCTION_STATUS_LIST))
                .build();
    }

    private Menu buildAvailableBuildingsMenu() {
        return MenuBuilder.aMenu()
                .withLabel("available buildings")
                .withItem(new MenuItem(BACK))
                .withItem(new MenuItem(BUILD_BUILDING, "AirDefense"))
                .withItem(new MenuItem(BUILD_BUILDING, "ArcherTower"))
                .withItem(new MenuItem(BUILD_BUILDING, "Barracks"))
                .withItem(new MenuItem(BUILD_BUILDING, "Camp"))
                .withItem(new MenuItem(BUILD_BUILDING, "Cannon"))
                .withItem(new MenuItem(BUILD_BUILDING, "ElixirMine"))
                .withItem(new MenuItem(BUILD_BUILDING, "ElixirStorage"))
                .withItem(new MenuItem(BUILD_BUILDING, "GoldMine"))
                .withItem(new MenuItem(BUILD_BUILDING, "GoldStorage"))
                .withItem(new MenuItem(BUILD_BUILDING, "WizardTower"))
                .build();
    }

    private Menu buildBarracksMenu() {
        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("barracks")
                .withItem(buildMenuWithDynamicItems("build soldiers", TROOPS_LIST))
                .withItem(buildMenuWithDynamicItems("status", TRAINING_STATUS_LIST))
                .build();
    }

    private Menu buildCampMenu() {
        Menu infoMenu = MenuBuilder.aMenuExtending(buildInfoMenu())
                .withItem(new MenuItem(CAPACITY_INFO))
                .build();

        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("camp")
                .withItem(infoMenu)
                .withItem(buildMenuWithDynamicItems("soldiers", AVAILABLE_TROOPS_LIST))
                .build();
    }

    private Menu buildMinesMenu() {
        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("mines")
                .withItem(buildMenuWithDynamicItems("mine", MINE))
                .build();
    }

    private Menu buildStorageMenu() {
        Menu infoMenu = MenuBuilder.aMenuExtending(buildInfoMenu())
                .withItem(new MenuItem(SOURCES_INFO))
                .build();

        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("storage")
                .withItem(infoMenu)
                .build();
    }

    private Menu buildDefensiveBuildingsMenu() {
        Menu infoMenu = MenuBuilder.aMenuExtending(buildInfoMenu())
                .withItem(new MenuItem(ATTACK_INFO))
                .build();

        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("")
                .withItem(infoMenu)
                .withItem(buildMenuWithDynamicItems("target", TARGET))
                .build();
    }
}