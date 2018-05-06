package controllers;

import controllers.Exceptions.InvalidInputException;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Village;
import models.GameLogic.World;
import models.Menu.*;
import viewers.BasicViewer;
import viewers.BuildingViewer;
import viewers.VillageViewer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static controllers.InputFormats.*;
import static controllers.enums.GeneralCommand.*;

public class Controller {

    private static MenuController menuController = new MenuController();
    private static BasicViewer viewer = new BasicViewer();
    private static World world = new World();
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
                case LOAD_GAME:
                    viewer.requestForInput("enter path:");
                    command = viewer.getInput();
                    if (menuController.isMenuItemNumber(command)) {
                        break;
                    }
                    loadGame(command);
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
                case LOAD_MAP:
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
                    menuController.openMenu((Menu) requestedMenuItem);
                    break;

                }

        } else {
            if (command.matches(SAVE_GAME.toString())) {
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


    private static String getArgument(int i, String command, String format) throws InvalidInputException {
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            return matcher.group(i);
        } else {
            throw new InvalidInputException("invalid input");
        }
    }

    public static void newGame() {
        // TODO: 5/6/2018
    }

    public static void loadGame(String path) {
        // TODO: 5/6/2018
        viewer.printInformation("game successfully loaded!");
    }

    public static void saveGame(Village village, String name) {
        JsonInterpreter.saveVillage(village, name);
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
