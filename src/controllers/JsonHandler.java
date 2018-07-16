package controllers;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.ExclusionStrategy;
import com.gilecode.yagson.com.google.gson.FieldAttributes;
import models.Account;
import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.GameMap;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.Village;
import models.World;
import models.setting.GameLogicConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class JsonHandler {
    private static int currentBuildingNumber = 0;
    private static final String SAVED_CONFIGS_PATH = "maps/configs/config.json";
    private static final String SAVED_CONFIGS_FOLDER_NAME = "maps/configs/";
    public static final String SAVED_MAPS_FOLDER_NAME = "maps/";
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

    public static void saveAccount(Account account) {
        try {
            createFolder(SAVED_MAPS_FOLDER_NAME);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        String accountFilePath = SAVED_MAPS_FOLDER_NAME + "/" + account.getInfo().getName() + ".json";
        try{
            Writer writer = new FileWriter(accountFilePath);
            gson.toJson(account, writer);
            writer.flush();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            Controller.getController().getWorld().getMyVillagesNameAndFile().put(new File(SAVED_MAPS_FOLDER_NAME + "/" + account.getUserName() + ".json"), account.getInfo().getName());
        }
    }

    public static void saveConfig() {
        try {
            createFolder(SAVED_CONFIGS_FOLDER_NAME);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            Writer writer = new FileWriter(SAVED_CONFIGS_PATH);
            gson.toJson(Controller.getController().getWorld().getMyVillagesNameAndFile(), writer);
            writer.flush();
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

    public static Account loadAccountbyName(String name) throws FileNotFoundException {
        String jsonAccount = toStringJson(new File(SAVED_MAPS_FOLDER_NAME + name + ".json"));
        return loadAccountFromJson(jsonAccount);
    }

    public static void loadConfig() {
        String jsonConfig = null;
        try {
            jsonConfig = toStringJson(new File(SAVED_CONFIGS_PATH));
        } catch (FileNotFoundException e) {
            e.getCause();
        }
        if (jsonConfig != null) {
            Controller.getController().getWorld().setMyVillagesNameAndFile(gson.fromJson(jsonConfig, HashMap.class));
            return;
        }
        Controller.getController().getWorld().setMyVillagesNameAndFile(new HashMap<>());
    }

    public static GameMap loadEnemyGameMapFromFile(File mapFile) throws FileNotFoundException{
        Account enemyAccount = loadAccountFromFile(mapFile);
        resetTroopsView(enemyAccount);
        return enemyAccount.getMyVillage().getGameMap();
    }



    public static String villageToJson(Village village) {
        return gson.toJson(village);
    }

    public static Village jsonToVillage(String jsonVillage) {
        Village village = gson.fromJson(jsonVillage, Village.class);
        resetBuildingsView(village);
        resetBuildingsMaxLevel(village);
        resetTroopsView(village);
        return village;
    }

    public static void resetBuildingsView(Village village) {
        for (Building building : village.getBuildings()) {
            building.setImage();
            building.updateViewPort();
        }
        for (Building building : village.getUnderConstructBuildings()) {
            building.setImage();
            building.updateViewPort();
        }
    }


    public static void resetBuildingsView(Account account) {
        resetBuildingsView(account.getMyVillage());
    }

    private static void resetBuildingsMaxLevel(Village village) {
        Building.setMaxLevel(village.getTownHall().getLevel());
    }

    public static void resetBuildingsMaxLevel(Account account) {
        Building.setMaxLevel(account.getMyVillage().getTownHall().getLevel());
    }

    public static void resetTroopsView(Village village) {
        for(Camp camp : village.findBuildingsWithSameType(Camp.class)) {
            for (Troop troop : camp.getTroops()) {
                troop.setImage();
            }
        }
    }

    public static void resetTroopsView(Account account) {
        resetBuildingsView(account.getMyVillage());
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
