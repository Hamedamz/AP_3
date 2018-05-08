package models.GameLogic;

import controllers.Exceptions.VillageAlreadyExists;
import controllers.JsonInterpreter;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.FileNotFoundException;
import models.GameLogic.Exceptions.TroopNotFoundException;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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


    public void loadEnemyMap(String path) throws java.io.FileNotFoundException {
        ArrayList<Building> buildings = JsonInterpreter.loadEnemyVillageBuildings(path);
        Map map = new Map(GameLogicConfig.getFromDictionary("VillageWidth"), GameLogicConfig.getFromDictionary("VillageHeight"));
        map.setBuildings(buildings);
        enemyVillagesPathAndMap.put(path, map);
    }


    public GameEngine getGameEngine() {
        return gameEngine;
    } // FIXME: 5/8/2018 gameEngine must not be passed

    public void attackMap(Map map) {
        battleGround = new BattleGround(myVillage, map);
    }

    public void sendTroopToAttack(String troopType, int count) throws TroopNotFoundException {
        battleGround.addTroops(troopType, myVillage.sendTroopToBattleGround(troopType, count));
    }
}
