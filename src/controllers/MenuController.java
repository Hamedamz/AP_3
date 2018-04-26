package controllers;

import models.Menu.Menu;
import models.Menu.MenuBuilder;
import models.Menu.MenuItem;
import viewers.MenuViewer;

import static controllers.InputFormats.*;

public class MenuController {
    private MenuViewer menuViewer;

    public MenuController() {
        menuViewer = new MenuViewer();
    }

    public void initializeMenus() {

    }

    public Menu buildMainMenu(Menu parent) {
        Menu menu = MenuBuilder.aMenu()
                .withParent(parent)
                .build();
        return menu;
    }
}
