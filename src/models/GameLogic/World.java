package models.GameLogic;

import controllers.Exceptions.VillageAlreadyExists;
import controllers.JsonInterpreter;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Exceptions.FileNotFoundException;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;
import java.util.HashMap;

public class World {
    private HashMap<String, String> myVillagesNameAndPath;
    private HashMap<String, Map> enemyVillagesPathAndMap;
    private Village myVillage;
    private BattleGround battleGround;
    private GameEngine gameEngine;

    public World() {

        gameEngine = new GameEngine(this);
        myVillagesNameAndPath = new HashMap<>();
        enemyVillagesPathAndMap = new HashMap<>();
    }

    public void loadVillage(String name) {
        try {
            myVillage = JsonInterpreter.loadMyVillage(name);
        } catch (java.io.FileNotFoundException e) {
            e.getMessage();
        }
    }

    public void addNewVillage(String name, String path) throws VillageAlreadyExists {
        if (myVillagesNameAndPath.containsKey(name)) {
            throw new VillageAlreadyExists();
        }
        else {
            myVillagesNameAndPath.put(name, path);
        }
    }

    public HashMap<String, String> getMyVillagesNameAndPath() {
        return myVillagesNameAndPath;
    }

    public HashMap<String, Map> getEnemyVillagesPathAndMap() {
        return enemyVillagesPathAndMap;
    }

    public Village getMyVillage() {
        return myVillage;
    }

    public void setMyVillage(Village myVillage) {
        this.myVillage = myVillage;
    }

    public BattleGround getBattleGround() {
        return battleGround;
    }

    public int getTurn() {
        return 0;
        // TODO: 5/7/2018
    }

    public void makeNewGame() throws VillageAlreadyExists{
//        if(myVillage != null) {
//            throw new VillageAlreadyExists();
//        }
        myVillage = Village.startNewVillage();
        gameEngine.loadNewVillage();
    }

    public void saveGame(Village village, String  name) {
        JsonInterpreter.saveVillage(village, name);
        myVillagesNameAndPath.put(name, "savedMaps\\" + name + ".json");
    }

    private boolean hasBattleEnded() {
        return true;
        // TODO: 5/5/2018
    }


    public void loadMyVillage(String path) throws FileNotFoundException {

    }

    public void loadEnemyMap(String path) throws java.io.FileNotFoundException {
        ArrayList<Building> buildings = JsonInterpreter.loadEnemyVillageBuildings(path);
        Map map = new Map(GameLogicConfig.getFromDictionary("VillageWidth"), GameLogicConfig.getFromDictionary("VillageHeight"));
        map.setBuildings(buildings);
        battleGround.setEnemyMap(map);
        enemyVillagesPathAndMap.put(path, map);
    }


    public boolean attackVillage() throws FileNotFoundException {
        // TODO: 5/5/2018
        return true;
        //maybe this changes
    }

    public void passTurn() {

    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }
}
