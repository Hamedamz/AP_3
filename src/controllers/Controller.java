package controllers;

import models.GameLogic.Village;
import models.GameLogic.World;
import models.Menu.*;
import viewers.BasicViewer;

public class Controller {

    private static MenuController menuController = new MenuController();
    private static BasicViewer viewer = new BasicViewer();
    private static World world = new World();

    public static void main(String[] args) {
        menuController.openMenu(menuController.getEntranceMenu());
        while (handleMenuInputs());
    }

    private static boolean handleMenuInputs() {
        menuController.printMenu();
        String command = viewer.getInput();
        if (menuController.isMenuItemNumber(command)) {
            MenuItem requestedMenuItem = menuController.getRequestedMenuItem(command);
            if (requestedMenuItem instanceof DynamicMenuItem) {

            } else {
                switch (requestedMenuItem.getCommandType()) {
                    case NEW_GAME:
                        newGame(); // TODO: 5/5/2018
                        menuController.openMenu(menuController.getVillageMenu());
                        break;
                    case LOAD_GAME:
                        loadGame(""); // TODO: 5/5/2018
                        menuController.openMenu(menuController.getVillageMenu());
                        break;
                    case SAVE_GAME:
                        break;
                    case SHOW_BUILDINGS:
                        break;
                    case SHOW_RESOURCES:
                        break;
                    case UPGRADE_BUILDING:
                        break;
                    case AVAILABLE_BUILDINGS:
                        break;
                    case STATUS:
                        break;
                    case LOAD_MAP:
                        break;
                    case OVERALL_INFO:
                        break;
                    case UPGRADE_INFO:
                        break;
                    case CAPACITY_INFO:
                        break;
                    case RESOURCES_INFO:
                        break;
                    case ATTACK_INFO:
                        break;
                    case MAP_INFO:
                        break;
                    case ATTACK_MAP:
                        break;
                    case BACK:
                        menuController.back();
                        break;
                    case OPEN_MENU:
                        menuController.openMenu((Menu)requestedMenuItem);
                        break;
                    case OPEN_DYNAMIC_MENU:
                        break;
                }
            }
        } else {
            return false; // FIXME: 5/5/2018 this is temporary
        }
        return true;
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
