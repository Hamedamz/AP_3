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
        buildMainMenu(null);

    }

    public Menu buildMainMenu(Menu parent) {
        Menu menu = MenuBuilder.aMenu()
                .withLabel("main menu")
                .withParent(parent)
                .withItem(new MenuItem(CommandType.NEW_GAME))
                .withItem(new MenuItem(CommandType.LOAD_GAME))
                .build();
        return menu;
    }


}
