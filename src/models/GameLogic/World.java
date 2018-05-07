package models.GameLogic;

import controllers.Exceptions.VillageAlreadyExists;
import controllers.JsonInterpreter;
import models.GameLogic.Exceptions.FileNotFoundException;

import java.util.HashMap;

public class World {
    private HashMap<String, String> villagesNameAndPath;
    private Village myVillage;
    private BattleGround battleGround;
    private GameEngine gameEngine;

    public World() {
        gameEngine = new GameEngine(this);
        villagesNameAndPath = new HashMap<>();
    }

    public void loadVillage(String name) {
        try {
            myVillage = JsonInterpreter.loadMyVillage(name);
        } catch (java.io.FileNotFoundException e) {
            e.getMessage();
        }
    }

    public void addNewVillage(String name, String path) throws VillageAlreadyExists {
        if (villagesNameAndPath.containsKey(name)) {
            throw new VillageAlreadyExists();
        }
        else {
            villagesNameAndPath.put(name, path);
        }
    }

    public HashMap<String, String> getVillagesNameAndPath() {
        return villagesNameAndPath;
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
        villagesNameAndPath.put(name, "savedMaps\\" + name + ".json");
    }

    private boolean hasBattleEnded() {
        return true;
        // TODO: 5/5/2018
    }


    public void loadMyVillage(String path) throws FileNotFoundException {

    }

    public void loadEnemyVillage(String path) throws FileNotFoundException {

    }


    public boolean attackVillage(String path) throws FileNotFoundException {
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
