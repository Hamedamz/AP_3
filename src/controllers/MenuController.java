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
                .withItem(buildBuildingsMenu())
                .withItem(buildResourcesMenu())
                .build();
    }

    private MenuItem buildResourcesMenu() {
        return MenuBuilder.aMenu()
                .withLabel("resources")
                .withItem(new MenuItem(ModelBasedList.RESOURCES_LIST))
                .build();
    }

    private Menu buildBuildingsMenu() {
        return MenuBuilder.aMenu()
                .withLabel("buildings")
                .withItem(new MenuItem(ModelBasedList.BUILDINGS_LIST))
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
                .withItem(buildAvailableBuildingsMenu())
                .withItem(buildStatusMenu())
                .withItem(new MenuItem(CommandType.BACK))
                .build();
    }

    private Menu buildStatusMenu() {
        return MenuBuilder.aMenu()
                .withLabel("status")
                .withItem(new MenuItem(ModelBasedList.UNDER_CONSTRUCTION_LIST))
                .withItem(new MenuItem(CommandType.BACK))
                .build();
    }

    private Menu buildAvailableBuildingsMenu() {
        return MenuBuilder.aMenu()
                .withLabel("available buildings")
                .withItem(new MenuItem(ModelBasedList.CONSTRUCTABLE_LIST))
                .withItem(new MenuItem(CommandType.BACK))
                .build();
    }
}
