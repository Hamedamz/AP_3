package controllers;

import com.gilecode.yagson.YaGson;
import models.GameLogic.Account;
import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.Village;
import models.Setting.GameLogicConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class JsonInterpreter {
    private static int currentBuildingNumber = 0;
    private static final String SAVED_MAPS_FOLDER_NAME = "savedMaps";
    private static YaGson gson = new YaGson();

    public static void saveVillage(Account account, String villageName) {
        try {
            createFolder(SAVED_MAPS_FOLDER_NAME);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        String filePath = SAVED_MAPS_FOLDER_NAME + "/" + villageName + ".json";
        try (Writer writer = new FileWriter(filePath)){
            gson.toJson(account, writer);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Account loadMyAccount(File mapFile) throws FileNotFoundException {
        String json = toStringJson(mapFile);
        return gson.fromJson(json, Account.class);
    }

    public static ArrayList<Building> loadEnemyVillageBuildings(File mapFile) throws FileNotFoundException{
        String jsonString = toStringJson(mapFile);
        JsonVillage jsonVillage = gson.fromJson(jsonString, JsonVillage.class);
        return extractBuildings(jsonVillage);
    }

    private static ArrayList<Building> extractBuildings(JsonVillage jsonVillage) {
        ArrayList<Building> buildings = new ArrayList<>();
        ArrayList<JsonBuilding> jsonBuildings = jsonVillage.buildings;
        ArrayList<JsonWall> jsonWalls = jsonVillage.walls;
        for (JsonWall wall : jsonWalls) {
            addNewWall(wall, buildings);
        }
        for (int i = 0; i < jsonBuildings.size(); i++) {
            int buildingType = jsonBuildings.get(i).type;
            switch (buildingType) {// FIXME: 5/6/2018 put this json numbers in config Arshia Moghimi
                case 1:
                    addNewGoldMine(jsonBuildings.get(i), buildings);
                    break;
                case 2:
                    addNewElixirMine(jsonBuildings.get(i), buildings);
                    break;
                case 3:
                    addNewGoldStorage(jsonBuildings.get(i), buildings);
                    break;
                case 4:
                    addNewElixirStorage(jsonBuildings.get(i), buildings);
                    break;
                case 5:
                    addNewTownHall(jsonBuildings.get(i), buildings);
                    break;
                case 6:
                    addNewBarracks(jsonBuildings.get(i), buildings);
                    break;
                case 7:
                    addNewCamp(jsonBuildings.get(i), buildings);
                    break;
                case 8:
                    addNewArcherTower(jsonBuildings.get(i), buildings);
                    break;
                case 9:
                    addNewCannon(jsonBuildings.get(i), buildings);
                    break;
                case 10:
                    addNewAirDefense(jsonBuildings.get(i), buildings);
                    break;
                case 11:
                    addNewWizardTower(jsonBuildings.get(i), buildings);
                    break;
                case 13:
                    addNewTrap(jsonBuildings.get(i), buildings);
                    break;
                case 14:
                    addNewGuardianGiant(jsonBuildings.get(i), buildings);
                    break;
            }
        }
        return buildings;
    }

    private static void addNewGuardianGiant(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        GuardianGiant guardianGiant = new GuardianGiant(extractPosition(jsonBuilding), false);
        guardianGiant.setLevel(jsonBuilding.level);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("GuardianGiantHitPoints");
        guardianGiant.setHitPoints(hitPoints);
        buildings.add(guardianGiant);
    }

    private static void addNewTrap(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        Trap trap = new Trap(extractPosition(jsonBuilding), false);
        trap.setLevel(jsonBuilding.level);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("TrapHitPoints");
        trap.setHitPoints(hitPoints);
        buildings.add(trap);
    }

    private static void addNewWall(JsonWall jsonBuilding, ArrayList<Building> buildings) {
        Wall wall = new Wall(Position.newMapPosition(jsonBuilding.x, jsonBuilding.y), false);
        wall.setLevel(jsonBuilding.level);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("WallHitPoints");
        wall.setHitPoints(hitPoints);
        buildings.add(wall);
    }

    private static void addNewWizardTower(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        WizardTower wizardTower = new WizardTower(extractPosition(jsonBuilding), false);
        wizardTower.setLevel(jsonBuilding.level);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("WizardTowerHitPoints");
        wizardTower.setHitPoints(hitPoints);
        buildings.add(wizardTower);
    }

    private static void addNewAirDefense(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        AirDefense airDefense = new AirDefense(extractPosition(jsonBuilding), false);
        airDefense.setLevel(jsonBuilding.level);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("AirDefenseHitPoints");
        airDefense.setHitPoints(hitPoints);
        buildings.add(airDefense);
    }

    private static void addNewCannon(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        Cannon cannon = new Cannon(extractPosition(jsonBuilding), false);
        cannon.setLevel(jsonBuilding.level);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("CannonHitPoints");
        cannon.setHitPoints(hitPoints);
        buildings.add(cannon);
    }

    private static void addNewArcherTower(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        ArcherTower archerTower = new ArcherTower(extractPosition(jsonBuilding), false);
        archerTower.setLevel(jsonBuilding.level);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("ArcherTowerHitPoints");
        archerTower.setHitPoints(hitPoints);
        buildings.add(archerTower);
    }

    private static void addNewCamp(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        Camp camp = new Camp(extractPosition(jsonBuilding), false);
        camp.setLevel(jsonBuilding.level);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("CampHitPoints");
        camp.setHitPoints(hitPoints);
        buildings.add(camp);
    }

    private static void addNewBarracks(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        Barracks barracks = new Barracks(extractPosition(jsonBuilding), false);
        barracks.setLevel(jsonBuilding.level);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("BarracksHitPoints");
        barracks.setHitPoints(hitPoints);
        buildings.add(barracks);
    }

    private static void addNewTownHall(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        TownHall townHall = new TownHall(Position.newMapPosition(jsonBuilding.x, jsonBuilding.y), false);
        townHall.setLevel(jsonBuilding.level);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("TownHallHitPoints") + ((int) GameLogicConfig.getFromDictionary("TownHallUpgradeHitPointsAddition") * jsonBuilding.level);
        townHall.setHitPoints(hitPoints);
        buildings.add(townHall);
    }

    private static void addNewElixirStorage(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        ElixirStorage elixirStorage = new ElixirStorage(extractPosition(jsonBuilding), false);
        elixirStorage.setLevel(jsonBuilding.level);
        int initialElixirCapacity = (int) GameLogicConfig.getFromDictionary("ElixirStorageGoldCapacity");
        int elixirStorageUpgradeCapacityAddition = (int) GameLogicConfig.getFromDictionary("ElixirStorageUpgradeCapacityAddition");
        int elixirCapacity = initialElixirCapacity;
        for (int i = 0; i < jsonBuilding.level; i++) {
            elixirCapacity = elixirCapacity + (elixirCapacity * elixirStorageUpgradeCapacityAddition / 100);
        }
        Resource capacity = new Resource(0, elixirCapacity);
        elixirStorage.setCapacity(capacity);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("ElixirStorageHitPoints");
        elixirStorage.setHitPoints(hitPoints);
        Resource stock = new Resource(0, jsonBuilding.amount);
        elixirStorage.setStock(stock);
        buildings.add(elixirStorage);
    }

    private static void addNewGoldStorage(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        GoldStorage goldStorage = new GoldStorage(extractPosition(jsonBuilding), false);
        goldStorage.setLevel(jsonBuilding.level);
        int initialGoldCapacity = (int) GameLogicConfig.getFromDictionary("GoldStorageGoldCapacity");
        int goldStorageUpgradeCapacityAddition = (int) GameLogicConfig.getFromDictionary("GoldStorageUpgradeCapacityAddition");
        int goldCapacity = initialGoldCapacity;
        for (int i = 0; i < jsonBuilding.level; i++) {
            goldCapacity = goldCapacity + (goldCapacity * goldStorageUpgradeCapacityAddition) / 100;
        }
        Resource capacity = new Resource(goldCapacity, 0);
        goldStorage.setCapacity(capacity);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("GoldStorageHitPoints");
        goldStorage.setHitPoints(hitPoints);
        Resource stock = new Resource(jsonBuilding.amount, 0);
        goldStorage.setStock(stock);
        buildings.add(goldStorage);
    }

    private static void addNewElixirMine(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        ElixirMine elixirMine = new ElixirMine(extractPosition(jsonBuilding), false);
        elixirMine.setLevel(jsonBuilding.level);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("ElixirMineHitPoints");
        elixirMine.setHitPoints(hitPoints);
        buildings.add(elixirMine);
    }

    private static void addNewGoldMine(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        GoldMine goldMine = new GoldMine(extractPosition(jsonBuilding), false);
        goldMine.setLevel(jsonBuilding.level);
        int hitPoints = (int) GameLogicConfig.getFromDictionary("GoldMineHitPoints");
        goldMine.setHitPoints(hitPoints);
        buildings.add(goldMine);
    }

    private static String toStringJson(File mapFile) throws FileNotFoundException {
        String jsonString = "";
        FileInputStream input = new FileInputStream(mapFile);
        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNext()) {
                jsonString = jsonString.concat(scanner.next());
            }
        }
        return jsonString;
    }

    private static void createFolder(String folderName) throws Exception{
        File savePath = new File(folderName);
        if (savePath.exists()) {
            return;
        }
        savePath.mkdirs();
    }

    private static Position extractPosition(JsonBuilding jsonBuilding) {
        return Position.newMapPosition(jsonBuilding.x, jsonBuilding.y);
    }
}
