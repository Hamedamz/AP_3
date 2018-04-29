package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.Village;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class JsonInterpreter {
    private static int currentBuildingNumber = 0;
    private static final String SAVED_MAPS_FOLDER_NAME = "savedMaps";
    private static Gson gson = new GsonBuilder().create();

    public static void saveVillage(Village village, String villageName) {
        try {
            createFolder(SAVED_MAPS_FOLDER_NAME);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        String filePath = SAVED_MAPS_FOLDER_NAME + "\\" + villageName + ".json";
        try (Writer writer = new FileWriter(filePath)){
            gson.toJson(village, writer);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static ArrayList<Building> loadEnemyVillageBuildings(String mapPath) {
        String jsonString = toStringJson(mapPath);
        JsonVillage jsonVillage = gson.fromJson(jsonString, JsonVillage.class);
        return extractBuildings(jsonVillage);
    }

    private static ArrayList<Building> extractBuildings(JsonVillage jsonVillage) {
        ArrayList<Building> buildings = new ArrayList<>();
        ArrayList<JsonBuilding> jsonBuildings = jsonVillage.buildings;
        for (int i = 0; i < jsonBuildings.size(); i++) {
            int buildingType = jsonBuildings.get(i).type;
            switch (buildingType) {
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
                case 12:
                    //addNewWall(jsonBuildings.get(i), buildings);
                    break;
                case 13:
                    //addNewTrap(jsonBuildings.get(i), buildings);
                    break;
                case 14:
                    //addNewGuardianGiant(jsonBuildings.get(i), buildings);
                    break;
            }
        }
        return buildings;
    }

    private static void addNewWizardTower(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        WizardTower wizardTower = new WizardTower(findNumberForBuildings());
        wizardTower.setLevel(jsonBuilding.level);
        wizardTower.setPosition(extractPosition(jsonBuilding));
        int hitPoints = 0;//TODO: load hitPoints from dictionary
        wizardTower.setHitPoints(hitPoints);
        buildings.add(wizardTower);
    }

    private static void addNewAirDefense(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        AirDefense airDefense = new AirDefense(findNumberForBuildings());
        airDefense.setLevel(jsonBuilding.level);
        airDefense.setPosition(extractPosition(jsonBuilding));
        int hitPoints = 0;//TODO: load hitPoints from dictionary
        airDefense.setHitPoints(hitPoints);
        buildings.add(airDefense);
    }

    private static void addNewCannon(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        Cannon cannon = new Cannon(findNumberForBuildings());
        cannon.setLevel(jsonBuilding.level);
        cannon.setPosition(extractPosition(jsonBuilding));
        int hitPoints = 0;//TODO: load hitPoints from dictionary
        cannon.setHitPoints(hitPoints);
        buildings.add(cannon);
    }

    private static void addNewArcherTower(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        ArcherTower archerTower = new ArcherTower(findNumberForBuildings());
        archerTower.setLevel(jsonBuilding.level);
        archerTower.setPosition(extractPosition(jsonBuilding));
        int hitPoints = 0;//TODO: load hitPoints from dictionary
        archerTower.setHitPoints(hitPoints);
        buildings.add(archerTower);
    }

    private static void addNewCamp(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        Camp camp = new Camp(findNumberForBuildings());
        camp.setLevel(jsonBuilding.level);
        camp.setPosition(extractPosition(jsonBuilding));
        int hitPoints = 0;//TODO: load hitPoints from dictionary
        camp.setHitPoints(hitPoints);
        buildings.add(camp);
    }

    private static void addNewBarracks(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        Barracks barracks = new Barracks(findNumberForBuildings());
        barracks.setLevel(jsonBuilding.level);
        barracks.setPosition(extractPosition(jsonBuilding));
        int hitPoints = 0;//TODO: load hitPoints from dictionary
        barracks.setHitPoints(hitPoints);
        buildings.add(barracks);
    }

    private static void addNewTownHall(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        TownHall townHall = new TownHall();
        townHall.setLevel(jsonBuilding.level);
        townHall.setPosition(extractPosition(jsonBuilding));
        int hitPoints = 0;//TODO: load hitPoints from dictionary
        townHall.setHitPoints(hitPoints);
        buildings.add(townHall);
    }

    private static void addNewElixirStorage(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        ElixirStorage elixirStorage = new ElixirStorage(findNumberForBuildings());
        elixirStorage.setLevel(jsonBuilding.level);
        elixirStorage.setPosition(extractPosition(jsonBuilding));
        Resource resource = new Resource(jsonBuilding.amount, 0);
        elixirStorage.setStock(resource);
        int hitPoints = 0;//TODO: load hitPoints from dictionary
        elixirStorage.setHitPoints(hitPoints);
        buildings.add(elixirStorage);
    }

    private static void addNewGoldStorage(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        GoldStorage goldStorage = new GoldStorage(findNumberForBuildings());
        goldStorage.setLevel(jsonBuilding.level);
        goldStorage.setPosition(extractPosition(jsonBuilding));
        Resource resource = new Resource(jsonBuilding.amount, 0);
        goldStorage.setStock(resource);
        int hitPoints = 0;//TODO: load hitPoints from dictionary
        goldStorage.setHitPoints(hitPoints);
        buildings.add(goldStorage);
    }

    private static void addNewElixirMine(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        ElixirMine elixirMine = new ElixirMine(findNumberForBuildings());
        elixirMine.setLevel(jsonBuilding.level);
        elixirMine.setPosition(extractPosition(jsonBuilding));
        int hitPoints = 0;//TODO: load hitPoints from dictionary
        elixirMine.setHitPoints(hitPoints);
        buildings.add(elixirMine);
    }

    private static void addNewGoldMine(JsonBuilding jsonBuilding, ArrayList<Building> buildings) {
        GoldMine goldMine = new GoldMine(findNumberForBuildings());
        goldMine.setLevel(jsonBuilding.level);
        goldMine.setPosition(extractPosition(jsonBuilding));
        int hitPoints = 0;//TODO: load hitPoints from dictionary
        goldMine.setHitPoints(hitPoints);
        buildings.add(goldMine);
    }

    private static int findNumberForBuildings() {
        currentBuildingNumber++;
        return currentBuildingNumber;
    }

    private static String toStringJson(String mapPath) {
        String jsonString = "";
        try {
            FileInputStream input = new FileInputStream(mapPath);
            Scanner scanner = new Scanner(input);
            while (scanner.hasNext()) {
                jsonString = jsonString.concat(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        Position position = new Position(jsonBuilding.x, jsonBuilding.y);
        return position;
    }
}
