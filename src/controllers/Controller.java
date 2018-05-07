package controllers;

import controllers.Exceptions.InvalidInputException;
import controllers.Exceptions.VillageAlreadyExists;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.DefensiveBuilding;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Exceptions.CountLimitReachedException;
import models.GameLogic.Exceptions.InvalidPositionException;
import models.GameLogic.Exceptions.NoFreeBuilderException;
import models.GameLogic.Exceptions.NotEnoughResourcesException;
import models.GameLogic.GameEngine;
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

    private static Controller controller = new Controller();

    private BasicViewer viewer = new BasicViewer();
    private World world = new World();
    private MenuController menuController = new MenuController(world);
    private VillageViewer villageViewer;
    private BuildingViewer buildingViewer = new BuildingViewer();

    public static void main(String[] args) {
        controller.menuController.openMenu(controller.menuController.getEntranceMenu());
        while (controller.menuController.isMenuOpen()) {
            try {
                handleMenuInputs();
            } catch (InvalidInputException | NoFreeBuilderException | NotEnoughResourcesException | InvalidPositionException e) {
                controller.viewer.printErrorMessage(e.getMessage());
            } catch (NumberFormatException e) {
                controller.viewer.printErrorMessage("input is out of range");
            } catch (CountLimitReachedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void handleMenuInputs() throws InvalidInputException, NoFreeBuilderException, InvalidPositionException, NotEnoughResourcesException, CountLimitReachedException {
        controller.villageViewer = new VillageViewer(controller.world.getMyVillage());
        controller.menuController.printMenu();
        String command = controller.viewer.getInput();
        if (controller.menuController.isMenuItemNumber(command)) {
            MenuItem requestedMenuItem = controller.menuController.getRequestedMenuItem(command);
            DynamicMenuItem dynamicMenuItem;
            Entity model = controller.menuController.getActiveMenu().getModel();

            switch (requestedMenuItem.getCommandType()) {
                case NULL:
                    break;
                case NEW_GAME:
                    newGame();
                    controller.menuController.openMenu(controller.menuController.getVillageMenu());
                    break;
                case OPEN_BUILDING_MENU:
                    dynamicMenuItem = (DynamicMenuItem) requestedMenuItem;
                    Entity dynamicItemModel = dynamicMenuItem.getModel();
                    Menu menu = controller.menuController.getModelBasedMenus().get(dynamicItemModel.getClass().getSimpleName());
                    controller.menuController.openMenu(menu, dynamicItemModel);
                    break;
                case UPGRADE_BUILDING:
                    controller.buildingViewer.requestUpgradeConfirmation((Building) model);
                    if (controller.viewer.getConfirmation()) {
                        upgradeBuilding((Building) model);
                    }
                    break;
                case BUILD_BUILDING:
                    String buildingType = requestedMenuItem.getLabel();
                    buildBuilding(buildingType);
                    break;
                case TRAIN_TROOP:
                    break;
                case LOAD_ENEMY_MAP:
                    break;
                case LOAD_GAME_FROM_FILE:
                    controller.viewer.requestForInput("enter path:");
                    command = controller.viewer.getInput();
                    if (loadGameFromFile(command)) {
                        controller.menuController.openMenu(controller.menuController.getVillageMenu());
                    }
                    break;
                case LOAD_GAME:
                    dynamicMenuItem = (DynamicMenuItem) requestedMenuItem;
                    String villageName = dynamicMenuItem.getLabel();
                    loadGame(villageName);
                    controller.menuController.openMenu(controller.menuController.getVillageMenu());
                    break;
                case OPEN_MAP_MENU:
                    break;
                case OVERALL_INFO:
                    controller.buildingViewer.printOverallInfo((Building) model);
                    break;
                case UPGRADE_INFO:
                    controller.buildingViewer.printUpgradeInfo((Building) model);
                    break;
                case CAPACITY_INFO:
                    break;
                case RESOURCES_INFO:
                    controller.villageViewer.printResourcesList();
                    break;
                case SOURCES_INFO:
                    controller.villageViewer.printStorageCapacity((Building) model);
                    break;
                case ATTACK_INFO:
                    controller.buildingViewer.printAttackInfo((DefensiveBuilding) model);
                    break;
                case MAP_INFO:
                    break;
                case TARGET_INFO:
                    break;
                case ATTACK_MAP:
                    break;
                case BACK:
                    controller.menuController.back();
                    break;
                case OPEN_MENU:
                    controller.menuController.openMenu((Menu) requestedMenuItem);
                    break;

                }

        } else {
            if (command.matches(SAVE_GAME.toString())) {
                controller.viewer.requestForInput("enter name for your village:");
                String name = controller.viewer.getInput(); // TODO: 5/6/2018 Exception Handlings
                saveGame(controller.world.getMyVillage(), name);
            } else if (command.matches(TURN_FORMAT)) {
                int n = Integer.parseInt(getArgument(1, command, TURN_FORMAT));
                turn(n);
            } else if (command.matches(RESOURCES.toString())) {
                controller.villageViewer.printResourcesList();
            }
        }
    }

    private static void newGame() {
        try {
            controller.world.makeNewGame();
            controller.villageViewer = new VillageViewer(controller.world.getMyVillage());
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
        String path = controller.world.getVillagesNameAndPath().get(villageName);
        if (path != null) {
            loadGameFromFile(path);
        } else {
            controller.viewer.printErrorMessage("no village with this name!");
        }
    }

    public static boolean loadGameFromFile(String path) {
        try {
            Village village = JsonInterpreter.loadMyVillage(path);
            controller.world.setMyVillage(village);
            controller.viewer.printInformation("game successfully loaded!");
            controller.villageViewer = new VillageViewer(controller.world.getMyVillage());
            return true;
        } catch (FileNotFoundException e) {
            controller.viewer.printErrorMessage("file not found");
            return false;
        }
    }

    public static void saveGame(Village village, String name) {
        controller.world.saveGame(village, name);
    }

    public static void turn(int n) {
        if (n == 0)
            return;
        for (int i = 0; i < n; i++) {
            controller.world.getGameEngine().update();
        }
    }

    public static void upgradeBuilding(Building building) {
        // TODO: 5/6/2018 check if we have enough resources
    }

    private static void buildBuilding(String buildingType) throws InvalidInputException, NoFreeBuilderException, InvalidPositionException, NotEnoughResourcesException, CountLimitReachedException {
        controller.world.getMyVillage().getTownHall().getFreeBuilder();
        controller.buildingViewer.requestBuildConfirmation(buildingType);
        if (!controller.viewer.getConfirmation()) {
            return;
        }
        controller.villageViewer.printMapCells();
        controller.buildingViewer.requestPositionToBuild(buildingType);
        String position = controller.buildingViewer.getPositionToBuild();
        int x = Integer.parseInt(getArgument(1, position, POSITION_FORMAT)) - 1;
        int y = Integer.parseInt(getArgument(2, position, POSITION_FORMAT)) - 1;
        controller.world.getMyVillage().build(buildingType, x, y);
    }

    public static void attack() {

    }


}
