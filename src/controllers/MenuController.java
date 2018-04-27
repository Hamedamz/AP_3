package controllers;

import controllers.enums.CommandType;
import controllers.enums.ModelBasedList;
import models.Menu.Menu;
import models.Menu.MenuBuilder;
import models.Menu.MenuItem;
import viewers.MenuViewer;

public class MenuController {
    private MenuViewer menuViewer;

    public MenuController() {
        menuViewer = new MenuViewer();
    }

    public void initializeMenus() {
        buildMainMenu();

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
                .withItem(buildModelBasedListMenu("buildings", ModelBasedList.BUILDINGS_LIST))
                .withItem(buildModelBasedListMenu("resources", ModelBasedList.RESOURCES_LIST))
                .build();
    }

    private MenuItem buildModelBasedListMenu(String label, ModelBasedList list) {
        return MenuBuilder.aMenu()
                .withLabel(label)
                .withItem(new MenuItem(list))
                .withItem(new MenuItem(CommandType.BACK))
                .build();
    }

    private Menu buildTypicalBuildingMenu() {
        return MenuBuilder.aMenu()
                .withItem(buildInfoMenu())
                .withItem(new MenuItem(CommandType.BACK))
                .build();
    }

    private Menu buildInfoMenu() {
        return MenuBuilder.aMenu()
                .withLabel("info")
                .withItem(new MenuItem(CommandType.OVERAL_INFO))
                .withItem(new MenuItem(CommandType.UPGRADE_INFO))
                .withItem(new MenuItem(CommandType.BACK))
                .build();
    }

    public Menu buildTownHallMenu() {
        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("town hall")
                .withItem(buildModelBasedListMenu("available buildings", ModelBasedList.CONSTRUCTABLE_LIST))
                .withItem(buildModelBasedListMenu("status", ModelBasedList.UNDER_CONSTRUCTION_LIST))
                .build();
    }

    public Menu buildBarracksMenu() {
        return MenuBuilder.aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("barracks")
                .withItem(buildModelBasedListMenu("build soldiers", ModelBasedList.TROOPS_LIST))
                .withItem(buildModelBasedListMenu("status", ModelBasedList.TRAINING_TROOPS_LIST))
                .build();
    }
}
