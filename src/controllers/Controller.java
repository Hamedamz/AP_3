package controllers;

import controllers.Exceptions.InvalidInputException;
import controllers.Exceptions.VillageAlreadyExists;
import controllers.enums.CommandType;
import models.GameLogic.*;
import models.GameLogic.Entities.Buildings.Barracks;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.DefensiveBuilding;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Exceptions.*;
import models.Menu.*;
import viewers.*;

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
    private MapViewer mapViewer = new MapViewer();
    private BattleGroundViewer battleGroundViewer = new BattleGroundViewer();

    public static void main(String[] args) {
        controller.menuController.openMenu(controller.menuController.getEntranceMenu());
        while (controller.menuController.isMenuOpen()) {
            try {
                handleMenuInputs();
            } catch (InvalidInputException | NoFreeBuilderException |
                    NotEnoughResourcesException | InvalidPositionException |
                    NotAvailableAtThisLevelException | CountLimitReachedException |
                    TroopNotFoundException e) {
                controller.viewer.printErrorMessage(e.getMessage());
            } catch (NumberFormatException e) {
                controller.viewer.printErrorMessage("input is out of range");
            } catch (FileNotFoundException e) {
                controller.viewer.printErrorMessage("file not found!");
            }
        }
    }

    private static void handleMenuInputs() throws InvalidInputException, NoFreeBuilderException, InvalidPositionException, NotEnoughResourcesException, CountLimitReachedException, NotAvailableAtThisLevelException, FileNotFoundException, TroopNotFoundException {
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
                    controller.newGame();
                    controller.menuController.openMenu(controller.menuController.getVillageMenu());
                    break;
                case OPEN_BUILDING_MENU:
                    dynamicMenuItem = (DynamicMenuItem) requestedMenuItem;
                    Entity buildingModel = dynamicMenuItem.getModel();
                    Menu buildingMenu = controller.menuController.getModelBasedMenus().get(buildingModel.getClass().getSimpleName());
                    controller.menuController.openMenu(buildingMenu, buildingModel);
                    break;
                case UPGRADE_BUILDING:
                    controller.buildingViewer.requestUpgradeConfirmation((Building) model);
                    if (controller.viewer.getConfirmation()) {
                        controller.upgradeBuilding((Building) model);
                    }
                    break;
                case BUILD_BUILDING:
                    String buildingType = requestedMenuItem.getLabel();
                    controller.buildBuilding(buildingType);
                    break;
                case TRAIN_TROOP:
                    String troopType = requestedMenuItem.getLabel();
                    trainTroop(troopType);
                    break;
                case LOAD_ENEMY_MAP:
                    controller.viewer.requestForInput("enter path:");
                    command = controller.viewer.getInput();
                    controller.loadEnemyMap(command);
                    break;
                case LOAD_GAME_FROM_FILE:
                    controller.viewer.requestForInput("enter path:");
                    command = controller.viewer.getInput();
                    try {
                        controller.loadGameFromFile(command);
                        controller.menuController.openMenu(controller.menuController.getVillageMenu());
                    } catch (FileNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case LOAD_GAME:
                    dynamicMenuItem = (DynamicMenuItem) requestedMenuItem;
                    String villageName = dynamicMenuItem.getLabel();
                    controller.loadGame(villageName);
                    controller.menuController.openMenu(controller.menuController.getVillageMenu());
                    break;
                case OPEN_MAP_MENU:
                    dynamicMenuItem = (DynamicMenuItem) requestedMenuItem;
                    Entity mapModel = dynamicMenuItem.getModel();
                    Menu mapMenu = controller.menuController.getModelBasedMenus().get(mapModel.getClass().getSimpleName());
                    controller.menuController.openMenu(mapMenu, mapModel);
                    break;
                case OVERALL_INFO:
                    controller.buildingViewer.printOverallInfo((Building) model);
                    break;
                case UPGRADE_INFO:
                    controller.buildingViewer.printUpgradeInfo((Building) model);
                    break;
                case CAPACITY_INFO:
                    controller.villageViewer.printCampCapacity();
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
                    controller.mapViewer.printMapInfo((Map) model);
                    break;
                case TARGET:
                    controller.buildingViewer.printTargetInfo((DefensiveBuilding) model);
                    break;
                case ATTACK_MAP:
                    controller.initializeAttack((Map) model);
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
                controller.saveGame(controller.world.getMyVillage(), name);
            } else if (command.matches(TURN_FORMAT)) {
                int n = Integer.parseInt(controller.getArgument(1, command, TURN_FORMAT));
                controller.turn(n);
            } else if (command.matches(RESOURCES.toString())) {
                controller.villageViewer.printResourcesList();
            }
        }
    }

    private void newGame() {
        try {
            controller.world.makeNewGame();
            controller.villageViewer = new VillageViewer(controller.world.getMyVillage());
        } catch (VillageAlreadyExists villageAlreadyExists) {
            villageAlreadyExists.getMessage();
        }
    }

    private String getArgument(int i, String command, String format) throws InvalidInputException {
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            return matcher.group(i);
        } else {
            throw new InvalidInputException("invalid input");
        }
    }

    private void loadGame(String villageName) throws FileNotFoundException {
        String path = controller.world.getMyVillagesNameAndPath().get(villageName);
        if (path != null) {
            loadGameFromFile(path);
        } else {
            controller.viewer.printErrorMessage("no village with this name!");
        }
    }

    private void loadEnemyMap(String path) throws FileNotFoundException {
        controller.world.loadEnemyMap(path);
        // TODO: 5/8/2018

    }

    private void loadGameFromFile(String path) throws FileNotFoundException {
        Village village = JsonInterpreter.loadMyVillage(path);
        controller.world.setMyVillage(village);
        controller.viewer.printInformation("game successfully loaded!");
        controller.villageViewer = new VillageViewer(controller.world.getMyVillage());
        return;
    }

    private static void trainTroop(String troopType) throws NotEnoughResourcesException, NotAvailableAtThisLevelException {
        controller.viewer.requestForInput("How many " + troopType + "s do you want to train?");
        int count = Integer.parseInt(controller.viewer.getInput());
        Barracks barracks = (Barracks) controller.menuController.getActiveMenu().getModel();
        controller.world.getMyVillage().trainTroop(troopType, count, barracks);
    }

    private void saveGame(Village village, String name) {
        controller.world.saveGame(village, name);
    }

    private void turn(int n) {
        for (int i = 0; i < n; i++) {
            controller.world.getGameEngine().update();
        }
    }

    private void upgradeBuilding(Building building) {
        Resource upgradeResource = null;
        try {
            upgradeResource = building.getUpgradeResource();
        } catch (UpgradeLimitReachedException e) {
            System.out.format("%s is already at the maximum level", building.getClass().getSimpleName());
            return;
        }
        if (upgradeResource.getGold() <= controller.world.getMyVillage().getTotalResourceStock().getGold() && upgradeResource.getElixir() <= controller.world.getMyVillage().getTotalResourceStock().getElixir()) {
            try {
                building.upgrade();
            } catch (UpgradeLimitReachedException e) {
                controller.viewer.printErrorMessage(e.getMessage());
            }
        }
    }

    private void buildBuilding(String buildingType) throws InvalidInputException, NoFreeBuilderException, InvalidPositionException, NotEnoughResourcesException, CountLimitReachedException {
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
        controller.viewer.printInformation("building process started");
    }

    private void initializeAttack(Map map) throws InvalidInputException, CountLimitReachedException, InvalidPositionException {
        controller.world.attackMap(map);
        controller.battleGroundViewer.setBattleGround(controller.world.getBattleGround());
        startSelectingTroops();
        controller.startAttack();
    }

    private void startAttack() {
        String command;
        do {
            command = controller.viewer.getInput();
            try {
                if (command.matches(STATUS_RESOURCES_FORMAT)) {
                    controller.battleGroundViewer.printStatusResources();

                } else if (command.matches(STATUS_UNIT_FORMAT)) {
                    String unitType = controller.getArgument(1, command, STATUS_UNIT_FORMAT);
                    if (CommandType.isTypeValid(unitType, "troop")) {
                        controller.battleGroundViewer.printStatusUnit(unitType);
                    } else throw new InvalidInputException("no such a unit!");

                } else if (command.matches(STATUS_UNITS_FORMAT)) {
                    controller.battleGroundViewer.printStatusUnit();

                } else if (command.matches(STATUS_TOWER_FORMAT)) {
                    String towerType = controller.getArgument(1, command, STATUS_TOWER_FORMAT);
                    if (CommandType.isTypeValid(towerType, "tower")) {
                        controller.battleGroundViewer.printStatusTower(towerType);
                    } else throw new InvalidInputException("no such a tower!");

                } else if (command.matches(STATUS_TOWERS_FORMAT)) {
                    controller.battleGroundViewer.printStatusTower();

                } else if (command.matches(STATUS_ALL_FORMAT)) {
                    controller.battleGroundViewer.printStatusAll();

                } else if (command.matches(PUT_TROOP_FORMAT)) {
                    String unitType = controller.getArgument(1, command, PUT_TROOP_FORMAT);
                    int number = Integer.parseInt(controller.getArgument(2, command, PUT_TROOP_FORMAT));
                    int x = Integer.parseInt(controller.getArgument(3, command, PUT_TROOP_FORMAT));
                    int y = Integer.parseInt(controller.getArgument(4, command, PUT_TROOP_FORMAT));
                    try {
                        controller.world.getBattleGround().putTroop(unitType, number, new Position(x, y));
                    } catch (TroopNotFoundException e) {
                        controller.viewer.printErrorMessage(e.getMessage());
                    }

                } else if (command.matches(GO_NEXT_TURN_FORMAT)) {
                    controller.turn(1);

                } else if (command.matches(TURN_FORMAT)) {
                    int n = Integer.parseInt(controller.getArgument(1, command, TURN_FORMAT));
                    controller.turn(n);

                } else if(command.matches(QUIT_ATTACK_FORMAT)) {
                    controller.world.getBattleGround().endBattle();
                    break;
                } else
                    throw new InvalidInputException("invalid input");

            } catch (CountLimitReachedException | InvalidInputException | InvalidPositionException e) {
                controller.viewer.printErrorMessage(e.getMessage());
            }

        } while (controller.world.getBattleGround().isGameFinished());
        controller.battleGroundViewer.printAttackFinishedInfo();
    }

    private void startSelectingTroops() {
        controller.viewer.requestForInput(START_SELECT_FORMAT);
        String command = controller.viewer.getInput();
        while (!command.matches(END_SELECT_FORMAT)) {
            String troopType = null;
            try {
                troopType = getArgument(1, command, SELECT_TROOP_FORMAT);
                int number = Integer.parseInt(getArgument(2, command, SELECT_TROOP_FORMAT));
                    controller.world.sendTroopToAttack(troopType, number);

            } catch (InvalidInputException | TroopNotFoundException e) {
                controller.viewer.printErrorMessage(e.getMessage());
            }
            command = controller.viewer.getInput();
        }
    }
}
