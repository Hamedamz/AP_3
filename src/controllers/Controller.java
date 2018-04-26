package controllers;

import models.GameLogic.Village;
import models.GameLogic.World;
import viewers.BasicViewer;

public class Controller {

    private static BasicViewer viewer = new BasicViewer();
    private static World world = new World();

    public static void main(String[] args) {
        String input = viewer.getInput();
        //unknown end command(?)
        while (true) {
            if (input.equals(InputFormats.NEWGAME_FORMAT)){
                newGame();
            }
            else if (input.equals(InputFormats.LOAD_PATH_FORMAT)) {
                String path = input.split("\\s")[1];
                try {
                    loadGame(path);
                }
                catch (Exception e) {
                }
            }
            else if (input.equals(InputFormats.SAVE_FORMAT)) {
                String path = input.split("\\s")[1];
                String name = input.split("\\s")[2];
                try {
                    saveGame(world.getMyVillage(), name);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (input.equals(InputFormats.TURN_FORMAT)) {
                int turns = Integer.parseInt(input.split("\\s")[1]);
                turn(turns);
            }
            else if (input.equals(InputFormats.SHOW_BUILDINGS_FORMAT)) {
                showBuildings();
            }
            else if (input.equals(InputFormats.SHOW_RESOURCES_FORMAT)) {
                showResources();
            }
            else if (input.equals(InputFormats.SHOW_MENU_FORMAT)) {
                viewer.printMessage(OutputFormats.VILLAGE_PRESENCE_FORMAT);
            }
            else if (input.equals(InputFormats.WHERE_AM_I_FORMAT)) {
                whereAmI();
            }
            else if (input.equals(InputFormats.ATTACK_FORMAT)) {
                attack();
            }
        }
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
