package models.GameLogic;

import controllers.Exceptions.VillageAlreadyExists;
import controllers.JsonInterpreter;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Exceptions.TroopNotFoundException;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;
import java.util.HashMap;

public class World {
    private HashMap<String, String> myVillagesNameAndPath;
    private HashMap<String, GameMap> enemyVillagesPathAndMap;
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

    public HashMap<String, GameMap> getEnemyVillagesPathAndMap() {
        return enemyVillagesPathAndMap;
    }

    public Village getMyVillage() {
        return myVillage;
    }

    public void setMyVillage(Village myVillage) { // FIXME: 5/9/2018 soroushVT
        this.myVillage = myVillage;
        gameEngine.loadNewVillage();
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
        GameMap gameMap = new GameMap(GameLogicConfig.getFromDictionary("VillageWidth"), GameLogicConfig.getFromDictionary("VillageHeight"));
        gameMap.setBuildings(buildings);
        enemyVillagesPathAndMap.put(path, gameMap);
    }


    public GameEngine getGameEngine() {
        return gameEngine;
    } // FIXME: 5/8/2018 gameEngine must not be passed

    public void attackMap(GameMap gameMap) {
        battleGround = new BattleGround(myVillage, gameMap);
        gameEngine.loadBattleGround();
    }

    public void sendTroopToAttack(String troopType, int count) throws TroopNotFoundException {
        battleGround.addTroops(troopType, myVillage.sendTroopToBattleGround(troopType, count));
    }
}
