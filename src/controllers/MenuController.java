package controllers;

import controllers.enums.CommandType;
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
        Menu menu = MenuBuilder.aMenu()
                .withLabel("main menu")
                .withItem(new MenuItem(CommandType.NEW_GAME))
                .withItem(new MenuItem(CommandType.LOAD_GAME))
                .build();
        return menu;
    }

    public Menu buildVillageMenu() {
        Menu menu = MenuBuilder.aMenu()
                .withLabel("village menu")
                .withItem(buildBuildingsMenu())
                .withItem(buildResourcesMenu())
                .build();
        return menu;
    }

    private MenuItem buildResourcesMenu() {
        Menu menu = MenuBuilder.aMenu()
                .withLabel("resources")
                .withItem(new MenuItem(CommandType.RESOURCES_LIST))
                .build();
        return menu;
    }

    private Menu buildBuildingsMenu() {
        Menu menu = MenuBuilder.aMenu()
                .withLabel("buildings")
                .withItem(new MenuItem(CommandType.BUILDINGS_LIST))
                .build();
        return menu;
    }

}
