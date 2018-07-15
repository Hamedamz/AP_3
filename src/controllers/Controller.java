package controllers;

import controllers.Exceptions.InvalidInputException;
import controllers.Exceptions.VillageAlreadyExists;
import controllers.enums.CommandType;
import javafx.application.Application;
import models.Account;
import models.GameLogic.*;
import models.GameLogic.Entities.Buildings.Barracks;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.DefensiveBuilding;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.*;
import models.World;
import viewers.menu.*;
import viewers.AppGUI;
import viewers.oldViewers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
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
        JsonHandler.loadConfig();
        Application.launch(AppGUI.class, args);
    }

    private static void handleMenuInputs() throws InvalidInputException, NoFreeBuilderException, InvalidPositionException, NotEnoughResourcesException, CountLimitReachedException, NotAvailableAtThisLevelException, FileNotFoundException, TroopNotFoundException {
//        controller.villageViewer = new VillageViewer(controller.world.getMyVillage()); //TEMP
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
                   // controller.newGame("Guest", "input");
                    controller.menuController.openMenu(controller.menuController.getVillageMenu());
                    break;
                case OPEN_BUILDING_MENU:
                    dynamicMenuItem = (DynamicMenuItem) requestedMenuItem;
                    Entity buildingModel = dynamicMenuItem.getModel();
                    Menu buildingMenu = controller.menuController.getModelBasedMenus().get(buildingModel.getClass().getSimpleName());
                    controller.menuController.openMenu(buildingMenu, buildingModel);
                    break;
                case UPGRADE_BUILDING:
//                    controller.buildingViewer.requestUpgradeConfirmation((Building) model);
                    if (controller.viewer.getConfirmation()) {
//                        controller.upgradeBuilding((Building) model);
                    }
                    break;
                case BUILD_BUILDING:
                    String buildingType = requestedMenuItem.getLabel();
//                    controller.buildBuilding(buildingType);//GUI
                    break;
                case TRAIN_TROOP:
                    String troopType = requestedMenuItem.getLabel();
//                    trainTroop(troopType);
                    break;
                case LOAD_ENEMY_MAP:
                    controller.viewer.requestForInput("enter path:");
                    command = controller.viewer.getInput();
              //      controller.loadEnemyMap(command);
                    // FIXME: 7/10/18 correct the above statement
                    break;
                case LOAD_GAME_FROM_FILE:
                    controller.viewer.requestForInput("enter path:");
                    command = controller.viewer.getInput();
                    try {
                       // controller.loadGameFromFile(command);
                        // FIXME: 7/10/18 correct above
                        controller.menuController.openMenu(controller.menuController.getVillageMenu());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case LOAD_GAME:
                    dynamicMenuItem = (DynamicMenuItem) requestedMenuItem;
                    String villageName = dynamicMenuItem.getLabel();
//                    controller.loadGame(villageName);
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
                    controller.mapViewer.printMapInfo((GameMap) model);
                    break;
                case TARGET:
                    controller.buildingViewer.printTargetInfo((DefensiveBuilding) model);
                    break;
                case ATTACK_MAP:
                    controller.setEnemyMap((GameMap) model);
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
                controller.saveGame();
            } else if (command.matches(TURN_FORMAT)) {
                int n = Integer.parseInt(controller.getArgument(1, command, TURN_FORMAT));
                controller.turn(n);
            } else if (command.matches(RESOURCES.toString())) {
                controller.villageViewer.printResourcesList();
            }
        }
    }

    public void newGame(String name, String password) {
        try {
            controller.getWorld().getMyVillagesNameAndFile().put(new File(JsonHandler.SAVED_MAPS_FOLDER_NAME + "/" + name + ".json"), name);
            controller.world.getGameEngine().resetVillage();
            controller.world.makeNewGame(name, password);
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

    public void loadGame(String name, String password) throws WrongPasswordException, FileNotFoundException {
        Account account;
        account = JsonHandler.loadAccountbyName(name);

        if (account.checkPassword(password)) {
            controller.world.getGameEngine().resetVillage();
            controller.world.setAccount(account);
            controller.world.setMyVillage(account.getMyVillage());
        }
        else {
            throw new WrongPasswordException();
        }
    }

    public void loadGameFromFile(File file) throws FileNotFoundException {
        Account account = JsonHandler.loadAccountFromFile(file);
        world.getMyVillagesNameAndFile().put(file, account.getInfo().getName());
        controller.world.getGameEngine().resetVillage();
        controller.world.setMyVillage(account.getMyVillage());
//        controller.world.setEnemies(getEnemyVillagesFileAndMap());
        controller.viewer.printInformation("game successfully loaded!");
        controller.villageViewer = new VillageViewer(controller.world.getMyVillage());
    }

    public void trainTroop(String troopType, int count, Barracks barracks) throws NotEnoughResourcesException, NotAvailableAtThisLevelException {
        controller.world.getMyVillage().trainTroop(troopType, count, barracks);
    }

    public void saveGame() {
        try {
            JsonHandler.saveAccount(Controller.getController().getWorld().getAccount());
            JsonHandler.saveConfig();
        } catch (NullPointerException e) {
            e.getCause();
        }
    }

    private void turn(int n) {
        for (int i = 0; i < n; i++) {
            controller.world.getGameEngine().update();
        }
    }

    public void upgradeBuilding(Building building) throws UpgradeLimitReachedException, NotEnoughResourcesException {
        Resource upgradeResource = null;

        upgradeResource = building.getUpgradeResource();

        if (upgradeResource.getGold() <= controller.world.getMyVillage().getTotalResourceStock().getGold() && upgradeResource.getElixir() <= controller.world.getMyVillage().getTotalResourceStock().getElixir()) {
            building.upgrade();
            controller.world.getMyVillage().spendResources(upgradeResource);
        }
    }

    public void buildBuilding(String buildingType, int x, int y) throws NoFreeBuilderException, InvalidPositionException, NotEnoughResourcesException, CountLimitReachedException {
        controller.world.getMyVillage().getTownHall().getFreeBuilder();
        controller.world.getMyVillage().build(buildingType, x, y);
        AppGUI.getMyVillageScene().addUnderConstructionBuilding(x, y);
        controller.viewer.printInformation("building process started");
    }

    public void loadEnemyMap(File file) throws FileNotFoundException {
        controller.world.loadEnemyMap(file);
        // TODO: 5/8/2018

    }

    public void setEnemyMap(GameMap enemyMap) {
        controller.world.attackMap(enemyMap);

        // for console
        battleGroundViewer.setBattleGround(controller.world.getBattleGround());
    }

    public void startAttack() {
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
                    int x = Integer.parseInt(controller.getArgument(3, command, PUT_TROOP_FORMAT)) - 1;
                    int y = Integer.parseInt(controller.getArgument(4, command, PUT_TROOP_FORMAT)) - 1;

//                    try {
//                        controller.world.getBattleGround().putTroop(unitType, number, new Position(x, y));
//                    } catch (TroopNotFoundException e) {
//                        controller.viewer.printErrorMessage(e.getMessage());
//                    }

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

            } catch ( InvalidInputException  e) {
                controller.viewer.printErrorMessage(e.getMessage());
            }

        } while (!controller.world.getBattleGround().isGameFinished());
        controller.battleGroundViewer.printAttackFinishedInfo();
    }

    public void setSelectedTroops(HashMap<String, Integer> selectedTroops) throws TroopNotFoundException {
        for (Map.Entry<String, Integer> troop : selectedTroops.entrySet()) {
            String troopType = troop.getKey();
            int troopNumber = troop.getValue();
            if (troopNumber != 0) {
                controller.world.sendTroopToAttack(troopType, troopNumber);
            }
        }
    }

    public void putTroop(String type, int i, Position position) throws TroopNotFoundException, InvalidPositionException, CountLimitReachedException {
        Troop troop = controller.getWorld().getBattleGround().putTroop(type, position);
        AppGUI.getBattleGroundScene().putTroop(troop);
    }



    public static Controller getController() {
        return controller;
    }

    public World getWorld() {
        return world;
    }
}
