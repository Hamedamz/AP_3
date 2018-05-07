package controllers;

import controllers.Exceptions.InvalidInputException;
import controllers.Exceptions.VillageAlreadyExists;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.DefensiveBuilding;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Village;
import models.GameLogic.World;
import models.Menu.*;
import viewers.BasicViewer;
import viewers.BuildingViewer;
import viewers.VillageViewer;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static controllers.InputFormats.*;
import static controllers.enums.GeneralCommand.*;

public class Controller {

    private static BasicViewer viewer = new BasicViewer();
    private static World world = new World();
    private static MenuController menuController = new MenuController(world);
    private static VillageViewer villageViewer = new VillageViewer(world.getMyVillage());
    private static BuildingViewer buildingViewer = new BuildingViewer();

    public static void main(String[] args) {
        menuController.openMenu(menuController.getEntranceMenu());
        while (menuController.isMenuOpen()) {
            try {
                handleMenuInputs();
            } catch (InvalidInputException e) {
                viewer.printErrorMessage(e.getMessage());
            } catch (NumberFormatException e) {
                viewer.printErrorMessage("input is out of range");
            }
        }
    }

    private static void handleMenuInputs() throws InvalidInputException {
        villageViewer = new VillageViewer(world.getMyVillage());
        menuController.printMenu();
        String command = viewer.getInput();
        if (menuController.isMenuItemNumber(command)) {
            MenuItem requestedMenuItem = menuController.getRequestedMenuItem(command);
            DynamicMenuItem dynamicMenuItem;
            Entity model = menuController.getActiveMenu().getModel();

            switch (requestedMenuItem.getCommandType()) {
                case NEW_GAME:
                    newGame();
                    menuController.openMenu(menuController.getVillageMenu());
                    break;
                case OPEN_BUILDING_MENU:
                    dynamicMenuItem = (DynamicMenuItem) requestedMenuItem;
                    Entity dynamicItemModel = dynamicMenuItem.getModel();
                    Menu menu = menuController.getModelBasedMenus().get(dynamicItemModel.getClass().getSimpleName());
                    menuController.openMenu(menu, dynamicItemModel);
                    break;
                case UPGRADE_BUILDING:
                    buildingViewer.requestUpgradeConfirmation((Building) model);
                    if (viewer.getConfirmation()) {
                        upgradeBuilding((Building) model);
                    }
                    break;
                case BUILD_BUILDING:
                    // TODO: 5/7/2018 check if there is free builder
                    String buildingType = requestedMenuItem.getLabel();
                    buildingViewer.requestBuildConfirmation(buildingType);
                    if (viewer.getConfirmation()) {
                        villageViewer.printMapCells();
                        buildingViewer.requestPositionToBuild(buildingType);
                        String position = buildingViewer.getPositionToBuild();
                        buildBuilding(buildingType, position);
                    }
                    break;
                case LOAD_ENEMY_MAP:
                    break;
                case LOAD_GAME_FROM_FILE:
                    viewer.requestForInput("enter path:");
                    command = viewer.getInput();
                    if (loadGameFromFile(command)) {
                        menuController.openMenu(menuController.getVillageMenu());
                    }
                    break;
                case LOAD_GAME:
                    dynamicMenuItem = (DynamicMenuItem) requestedMenuItem;
                    String villageName = dynamicMenuItem.getLabel();
                    loadGame(villageName);
                    menuController.openMenu(menuController.getVillageMenu());
                    break;
                case OPEN_MAP_MENU:
                    break;
                case OVERALL_INFO:
                    buildingViewer.printOverallInfo((Building) model);
                    break;
                case UPGRADE_INFO:
                    buildingViewer.printUpgradeInfo((Building) model);
                    break;
                case CAPACITY_INFO:
                    break;
                case RESOURCES_INFO:
                    villageViewer.printResourcesList();
                    break;
                case SOURCES_INFO:
                    villageViewer.printStorageCapacity((Building) model);
                    break;
                case ATTACK_INFO:
                    buildingViewer.printAttackInfo((DefensiveBuilding) model);
                    break;
                case MAP_INFO:
                    break;
                case ATTACK_MAP:
                    break;
                case BACK:
                    menuController.back();
                    break;
                case OPEN_MENU:
                    menuController.openMenu((Menu) requestedMenuItem);
                    break;
                }

        } else {
            if (command.matches(SAVE_GAME.toString())) {
                viewer.requestForInput("enter name for your village:");
                String name = viewer.getInput(); // TODO: 5/6/2018 Exception Handlings
                saveGame(world.getMyVillage(), name);
            } else if (command.matches(TURN_FORMAT)) {
                int n = Integer.parseInt(getArgument(1, command, TURN_FORMAT));
                turn(n);
            } else if (command.matches(RESOURCES.toString())) {
                villageViewer.printResourcesList();
            }
        }
    }

    private static void newGame() {
        try {
            world.makeNewGame();
        } catch (VillageAlreadyExists villageAlreadyExists) {
            villageAlreadyExists.getMessage();
        }
    }

    private static String getArgument(int i, String command, String format) throws InvalidInputException {
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            return matcher.group(i);
        } else {
            throw new InvalidInputException("invalid input");
        }
    }

    private static void loadGame(String villageName) {
        String path = world.getVillagesNameAndPath().get(villageName);
        if (path != null) {
            loadGameFromFile(path);
        } else {
            viewer.printErrorMessage("no village with this name!");
        }
    }

    public static boolean loadGameFromFile(String path) {
        try {
            Village village = JsonInterpreter.loadMyVillage(path);
            world.setMyVillage(village);
            viewer.printInformation("game successfully loaded!");
            return true;
        } catch (FileNotFoundException e) {
            viewer.printErrorMessage("file not found");
            return false;
        }
    }

    public static void saveGame(Village village, String name) {
        world.saveGame(village, name);
    }

    public static void turn(int n) {
        if (n == 0)
            return;
        System.out.format("turn %d", n);
        // TODO: 5/6/2018
    }

    public static void upgradeBuilding(Building building) {
        // TODO: 5/6/2018 check if we have enough resources
    }

    private static void buildBuilding(String buildingType, String position) throws InvalidInputException {
        int x = Integer.parseInt(getArgument(1, position, POSITION_FORMAT));
        int y = Integer.parseInt(getArgument(2, position, POSITION_FORMAT));
    }

    public static void attack() {

    }


}
