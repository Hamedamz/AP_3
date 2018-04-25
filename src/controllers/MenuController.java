package controllers;

import models.MenuBuilder;
import viewers.MenuViewer;

public class MenuController {
    private MenuViewer menuViewer;

    public MenuController() {
        menuViewer = new MenuViewer();
    }
}
