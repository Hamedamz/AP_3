package models;

import controllers.Controller;
import controllers.Exceptions.VillageAlreadyExists;
import controllers.JsonHandler;
import models.GameLogic.*;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Exceptions.TroopNotFoundException;
import models.setting.GameLogicConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class World {
    private Account account;
    private HashMap<File, GameMap> enemyVillagesFileAndMap = new HashMap<>();
    private HashMap<File, String> myVillagesNameAndFile;
    private BattleGround battleGround;
    private GameEngine gameEngine;

    public World() {
        gameEngine = new GameEngine(this);
        myVillagesNameAndFile = new HashMap<>();
    }


    public void setMyVillagesNameAndFile(HashMap<File, String> myVillagesNameAndFile) {
        this.myVillagesNameAndFile = myVillagesNameAndFile;
    }

    public HashMap<File, String> getMyVillagesNameAndFile() {
        return myVillagesNameAndFile;
    }

    public HashMap<File, GameMap> getEnemyVillagesFileAndMap() {
        return enemyVillagesFileAndMap;
    }

    public Village getMyVillage() {
        return account.getMyVillage();
    }

    public void setMyVillage(Village myVillage) { // FIXME: 5/9/2018 soroushVT
        this.account.setMyVillage(myVillage);
        gameEngine.loadNewVillage();
    }

    public void setEnemies(HashMap<File, GameMap> enemies){
        enemyVillagesFileAndMap = enemies;
    }

    public BattleGround getBattleGround() {
        return battleGround;
    }

    public void makeNewGame(String name, String password) throws VillageAlreadyExists{
//        if(myVillage != null) {
//            throw new VillageAlreadyExists();
//        }
        account = new Account(name, password);
        gameEngine.loadNewVillage();
    }

    public void saveGame(Account account) {
        JsonHandler.saveAccount(account);
       // myVillagesNameAndFile.put(name, "savedMaps\\" + name + ".json");
        // TODO: 7/10/18 correct the above statement @HAMEDAMZ
    }


    public void loadEnemyMap(File file) throws java.io.FileNotFoundException {
        ArrayList<Building> buildings = JsonHandler.loadEnemyVillageBuildingsFromFile(file);
        GameMap gameMap = new GameMap(GameLogicConfig.getFromDictionary("VillageWidth"), GameLogicConfig.getFromDictionary("VillageHeight"));
        gameMap.setBuildings(buildings);
        getEnemyVillagesFileAndMap().put(file, gameMap);
    }


    public GameEngine getGameEngine() {
        return gameEngine;
    } // FIXME: 5/8/2018 gameEngine must not be passed

    public void attackMap(GameMap gameMap) {
        battleGround = new BattleGround(getMyVillage(), gameMap);
        gameEngine.loadBattleGround();
    }

    public void sendTroopToAttack(String troopType, int count) throws TroopNotFoundException {
        battleGround.addTroops(troopType, getMyVillage().sendTroopToBattleGround(troopType, count));
    }

    public Account getAccount() {
        return account;
    }



}
