package controllers;

import models.GameLogic.Village;
import models.GameLogic.World;
import models.Menu.Menu;
import viewers.BasicViewer;

import java.util.HashMap;

public class Controller {

    private static MenuController menuController = new MenuController();
    private static BasicViewer viewer = new BasicViewer();
    private static World world = new World();

    public static void main(String[] args) {
        Menu entranceMenu = menuController.getEntranceMenu();
        Menu villageMenu = menuController.getVillageMenu();
        HashMap<String, Menu> modelBasedMenus = menuController.getModelBasedMenus();
    }

    public static void newGame() {}

    public static void loadGame(String path) {
    }

    public static void saveGame(Village village, String name) {
        JsonInterpreter.saveVillage(village, name);
    }

    public static void turn(int n) {

    }

    public static void showBuildings() {
        //TODO buildings menus should be handled here
    }

    public static void showResources() {

    }

    public static void showInfo() {

    }

    public static void upgrade() {

    }

    public static void back() {

    }

    public static void showMenu() {

    }

    public static void whereAmI() {

    }

    public static void attack() {

    }


}
