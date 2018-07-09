package models.SinglePlayer;

import controllers.Exceptions.VillageAlreadyExists;
import controllers.JsonInterpreter;
import models.GameLogic.*;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Exceptions.TroopNotFoundException;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;
import java.util.HashMap;

public class SinglePlayerWorld {
    private Account account;
    private HashMap<String, String> myVillagesNameAndPath;
    private BattleGround battleGround;
    private GameEngine gameEngine;

    public SinglePlayerWorld() {
        gameEngine = new GameEngine(this);
        myVillagesNameAndPath = new HashMap<>();
    }


    public HashMap<String, String> getMyVillagesNameAndPath() {
        return myVillagesNameAndPath;
    }

    public HashMap<String, GameMap> getEnemyVillagesPathAndMap() {
        return account.getEnemyVillagesPathAndMap();
    }

    public Village getMyVillage() {
        return account.getMyVillage();
    }

    public void setMyVillage(Village myVillage) { // FIXME: 5/9/2018 soroushVT
        this.account.setMyVillage(myVillage);
        gameEngine.loadNewVillage();
    }

    public BattleGround getBattleGround() {
        return battleGround;
    }

    public void makeNewGame() throws VillageAlreadyExists{
//        if(myVillage != null) {
//            throw new VillageAlreadyExists();
//        }
        account = new Account();
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
        getEnemyVillagesPathAndMap().put(path, gameMap);
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
}
