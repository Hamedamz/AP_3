package controllers;

import controllers.enums.CommandType;
import controllers.enums.DynamicListType;
import models.GameLogic.Village;
import models.Menu.Menu;
import models.Menu.MenuBuilder;
import models.Menu.MenuItem;
import viewers.MenuViewer;

public class MenuController {
    private MenuViewer menuViewer;

    public MenuController(Village village) {
        menuViewer = new MenuViewer(village);
    }

    public Menu initializeVillageMenus() {
        Menu villageMenu = buildVillageMenu();
        villageMenu.setParent(null);


    }

    public CommandType OpenMenu(Menu menu) {
        menuViewer.printMenu(menu);
    }

    public Menu buildMainMenu() {
        return MenuBuilder.aMenu()
                .withLabel("main menu")
                .withItem(new MenuItem(CommandType.NEW_GAME))
                .withItem(new MenuItem(CommandType.LOAD_GAME))
                .build();
    }

    public Menu buildVillageMenu() {
        return MenuBuilder.aMenu()
                .withLabel("village menu")
                .withItem(new MenuItem(CommandType.BACK))
                .withItem(buildDynamicMenu("buildings", DynamicListType.BUILDINGS_LIST))
                .withItem(buildDynamicMenu("resources", DynamicListType.RESOURCES_LIST))
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

    public Menu buildMapMenu() {
        return MenuBuilder.aMenu()
                .withLabel("map")
                .withItem(new MenuItem(CommandType.MAP_INFO))
                .withItem(new MenuItem(CommandType.ATTACK_MAP))
                .build();
    }

    private MenuItem buildDynamicMenu(String label, DynamicListType dynamicListType) {
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

    public Menu buildTownHallMenu() {
        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("town hall")
                .withItem(buildDynamicMenu("available buildings", DynamicListType.AVAILABLE_BUILDINGS_LIST))
                .withItem(buildDynamicMenu("status", DynamicListType.CONSTRUCTION_STATUS_LIST))
                .build();
    }

    public Menu buildBarracksMenu() {
        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("barracks")
                .withItem(buildDynamicMenu("build soldiers", DynamicListType.TROOPS_LIST))
                .withItem(buildDynamicMenu("status", DynamicListType.TRAINING_STATUS_LIST))
                .build();
    }

    public Menu builCampMenu() {
        Menu infoMenu = MenuBuilder.aMenuExtending(buildInfoMenu())
                .withItem(new MenuItem(CommandType.CAPACITY_INFO))
                .build();

        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("camp")
                .withItem(infoMenu)
                .withItem(buildDynamicMenu("soldiers", DynamicListType.AVAILABLE_TROOPS_LIST))
                .build();
    }

    public Menu buildMinesMenu() {
        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("mines")
                .withItem(buildDynamicMenu("mine", DynamicListType.MINE))
                .build();
    }

    public Menu buildStoragesMenu() {
        Menu infoMenu = MenuBuilder.aMenuExtending(buildInfoMenu())
                .withItem(new MenuItem(CommandType.RESOURCES_INFO))
                .build();

        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("camp")
                .withItem(infoMenu)
                .build();
    }

    public Menu buildDefensiveTowersMenu() {
        Menu infoMenu = MenuBuilder.aMenuExtending(buildInfoMenu())
                .withItem(new MenuItem(CommandType.ATTACK_INFO))
                .build();

        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("camp")
                .withItem(infoMenu)
                .withItem(buildDynamicMenu("target", DynamicListType.TARGET))
                .build();
    }
}