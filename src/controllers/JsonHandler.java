package controllers;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.ExclusionStrategy;
import com.gilecode.yagson.com.google.gson.FieldAttributes;
import models.Account;
import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.Village;
import models.setting.GameLogicConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class JsonHandler {
    private static int currentBuildingNumber = 0;
    private static final String SAVED_MAPS_FOLDER_NAME = "savedMaps";
    private static YaGsonBuilder builder = new YaGsonBuilder();
    private static YaGson gson;
    static {
        builder.addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getName().contains("imageView");
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        });
        builder.addDeserializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getName().contains("imageView");
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        });
        gson = builder.create();
    }

    public static void saveAccount(Account account, String villageName) {
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

    public static Account loadAccountFromFile(File mapFile) throws FileNotFoundException {
        String json = toStringJson(mapFile);
        return loadAccountFromJson(json);
    }

    public static Account loadAccountFromJson(String json) {
        Account account = gson.fromJson(json, Account.class);
        resetBuildingsView(account);
        resetBuildingsMaxLevel(account);
        return account;
    }

    public static ArrayList<Building> loadEnemyVillageBuildingsFromFile(File mapFile) throws FileNotFoundException{
        Account enemyAccount = loadAccountFromFile(mapFile);
        resetTroopsView(enemyAccount);
        return enemyAccount.getMyVillage().getBuildings();
    }

    public static ArrayList<Building> loadEnemyVillageBuildingsFromJson(String json){
        Account enemyAccount = loadAccountFromJson(json);
        resetTroopsView(enemyAccount);
        return enemyAccount.getMyVillage().getBuildings();
    }


    public static void resetBuildingsView(Account account) {
        for (Building building : account.getMyVillage().getBuildings()) {
            building.setImage();
            building.updateViewPort();
        }
    }

    public static void resetBuildingsMaxLevel(Account account) {
        Building.setMaxLevel(account.getMyVillage().getTownHall().getLevel());
    }

    public static void resetTroopsView(Account account) {
        for(Camp camp : account.getMyVillage().findBuildingsWithSameType(Camp.class)) {
            for (Troop troop : camp.getTroops()) {
                troop.setImage();
            }
        }
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
