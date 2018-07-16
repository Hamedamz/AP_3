package models.GameLogic;

import models.Account;
import models.AccountInfo;
import models.interfaces.Effector;
import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.*;
import models.GameLogic.Exceptions.CountLimitReachedException;
import models.GameLogic.Exceptions.InvalidPositionException;
import models.GameLogic.Exceptions.TroopNotFoundException;
import models.setting.GameLogicConfig;
import viewers.AppGUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BattleGround {

    public static final int MAX_UNITS_IN_POSITION = 5;

    private int timeRemaining;
    private Village myVillage;
    private GameMap enemyGameMap;
    private ArrayList<Building> enemyBuildings;
    private ArrayList<Troop> deployedTroops;
    private HashMap<String, ArrayList<Troop>> unDeployedTroops;
    private AccountInfo enemyAccountInfo;

    private Bounty thisLootedBounty;

    private Bounty lootedBounty;
    private int[][] numberOfTroopsDeployed;
    private boolean isGameFinished;

    private boolean isWallDestroyed;
    public BattleGround(Village myVillage, GameMap enemyGameMap) {
        enemyAccountInfo = AppGUI.getController().getWorld().getEnemyAccount(enemyGameMap).getInfo();
        numberOfTroopsDeployed = new int[enemyGameMap.getMapWidth()][enemyGameMap.getMapWidth()];
        deployedTroops = new ArrayList<>();
        enemyBuildings = new ArrayList<>(enemyGameMap.getBuildings());
        unDeployedTroops = new HashMap<>();
        this.myVillage = myVillage;
        this.enemyGameMap = enemyGameMap;
        lootedBounty = new Bounty(0, new Resource(0, 0));
        thisLootedBounty = new Bounty(0, new Resource(0, 0));
        this.timeRemaining = (int) GameLogicConfig.getFromDictionary("WarTime");
        initiateAllTroops();
    }

    private void initiateAllTroops() {
        for (String troopType : GameLogicConfig.TROOPS) {
            unDeployedTroops.put(troopType, new ArrayList<>());
        }
    }

    public void setNumberOfTroopsDeployed(int[][] numberOfTroopsDeployed) {
        this.numberOfTroopsDeployed = numberOfTroopsDeployed;
    }

    public int[][] getNumberOfTroopsDeployed() {
        return numberOfTroopsDeployed;
    }

    public void addTroops(String troopType, ArrayList<Troop> troops) {
        if(unDeployedTroops.keySet().contains(troopType)) {
            unDeployedTroops.get(troopType).addAll(troops);
        } else {
            unDeployedTroops.put(troopType, troops);
        }
    }

    public void addBounty(Bounty bounty) {
        this.thisLootedBounty.addToThisBounty(bounty);
        this.lootedBounty.addToThisBounty(bounty);
        myVillage.addBounty(bounty);
    }

    public Bounty getLootedBounty() {
        return lootedBounty;
    }

    public Resource getRemainingResources() {
        Resource availableResources = new Resource(0, 0);
        for (Building building : enemyGameMap.getBuildings()) {
            if(!building.isDestroyed()) {
                availableResources.addToThisResource(building.getBounty().getResource());
            }
        }
        return availableResources;
    }

    public synchronized void reset() {
        thisLootedBounty = new Bounty(0, new Resource(0, 0));
        setNumberOfTroopsDeployed(new int[enemyGameMap.getMapWidth()][enemyGameMap.getMapHeight()]);

    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public Resource getMyVillageAvailableResourceSpace() {
        return myVillage.getTotalResourceCapacity();
    }

    public  ArrayList<Entity> getAttackerInPosition(Position pos) {
        ArrayList<Entity> result = new ArrayList<>();
        for (Troop troop : deployedTroops) {
            if (troop.getPosition().equals(pos)) {
                result.add(troop);
            }
        }
        return result;
    }

    public ArrayList<Troop> getDeployedTroops() {
        return deployedTroops;
    }

    public HashMap<String, ArrayList<Troop>> getUnDeployedTroops() {
        return unDeployedTroops;
    }

    public GameMap getEnemyGameMap() {
        return enemyGameMap;
    }


    public ArrayList<Building> getEnemyBuildings() {
        return enemyBuildings;
    }

    public ArrayList<Defender> getEnemyDefenders(){
        return new ArrayList<>(enemyBuildings);
    }

    public Village getVillage() {
        return myVillage;
    }


    //TODO check position correctness -> not occupied
    public synchronized Troop putTroop(String troopType, Position position)
            throws CountLimitReachedException, InvalidPositionException, TroopNotFoundException {
        ArrayList<Troop> troops = unDeployedTroops.get(troopType);
        if(troops == null || troops.size() < 1) {
            throw new TroopNotFoundException();
        }
        if (!(position.getMapX() == 0 || position.getMapX() == enemyGameMap.getMapWidth() - 1 ||
                position.getMapY() == 0 || position.getMapY() == enemyGameMap.getMapHeight() - 1)) {
            throw new InvalidPositionException();
        }
        if (numberOfTroopsDeployed[position.getMapX()][position.getMapY()] + 1 > MAX_UNITS_IN_POSITION) {
            throw new CountLimitReachedException();
        }

        Troop deployedTroop = troops.get(troops.size() - 1);
        deployedTroops.add(deployedTroop);
        troops.remove(troops.size()-1);
        deployedTroop.setPosition(position);

        numberOfTroopsDeployed[position.getMapX()][position.getMapY()] ++;

        deployedTroop.setMovementPath(new ArrayList<>(Arrays.asList(deployedTroop.getPosition())));

        return deployedTroop;
    }

    public int getUnDeployedTroopsNumberByType(String type) {
        if (getUnDeployedTroops().get(type) != null) {
            return getUnDeployedTroops().get(type).size();
        }
        return 0;
    }

    public synchronized boolean isGameFinished() {
        if(isGameFinished) {
            return true;
        }
        if (timeRemaining <= 0) {
            return true;
        }
        boolean flag = false;
        for (String troopType : unDeployedTroops.keySet()) {
            if(unDeployedTroops.get(troopType).size() > 0) {
                flag = true;
                break;
            }
        }
        if(!flag && deployedTroops.size() == 0) {
            return true;
        }
//        if (!myVillage.isThereAvailableSpaceForResources()) {
//            return true;
//        }
        for (Defender defender : enemyGameMap.getBuildings()) {
            if(!defender.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    public synchronized void endBattle() {
        isGameFinished = true;
        for (Troop troop : deployedTroops) {
            if (unDeployedTroops.containsKey(troop.getClass().getSimpleName())) {
                unDeployedTroops.get(troop.getClass().getSimpleName()).add(troop);
            }
            else {
                ArrayList<Troop> troops = new ArrayList<>();
                troops.add(troop);
                unDeployedTroops.put(troop.getClass().getSimpleName(), troops);
            }
        }
        for(String name : unDeployedTroops.keySet()) {
            for(Troop troop : unDeployedTroops.get(name)) {
                troop.removeTarget();
            }
        }
        for(Defender defender : getEnemyDefenders()) {
            if(defender instanceof Effector) {
                ((Effector) defender).removeTarget();
                if(defender instanceof GuardianGiant) {
                    ((GuardianGiant) defender).resetPosition();
                }
            }
        }
        myVillage.spreadTroops(unDeployedTroops);
    }

    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
    }

    public boolean isWallDestroyed() {
        return isWallDestroyed;
    }

    public void setWallDestroyed(boolean wallDestroyed) {
        isWallDestroyed = wallDestroyed;
    }
}
