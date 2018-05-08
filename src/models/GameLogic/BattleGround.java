package models.GameLogic;

import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.*;
import models.GameLogic.Exceptions.CountLimitReachedException;
import models.GameLogic.Exceptions.InvalidPositionException;
import models.GameLogic.Exceptions.TroopNotFoundException;
import models.Setting.GameLogicConfig;
import java.util.ArrayList;
import java.util.HashMap;

public class BattleGround {

    public static final int MAX_UNITS_IN_POSITION = 5;

    private int timeRemaining;
    private Village myVillage;
    private Map enemyMap;
    private ArrayList<Building> enemyBuildings;
    private ArrayList<Troop> deployedTroops;
    private HashMap<String, ArrayList<Troop>> allTroops;
    private Bounty thisLootedBounty;
    private Bounty lootedBounty;
    private int[][] numberOfTroopsDeployed = new int[30][30];

    public BattleGround(Village myVillage, Map enemyMap) {
        deployedTroops = new ArrayList<>();
        enemyBuildings = new ArrayList<>(enemyMap.getBuildings());
        allTroops = new HashMap<>();
        this.myVillage = myVillage;
        this.enemyMap = enemyMap;
        lootedBounty = new Bounty(0, new Resource(0, 0));
        thisLootedBounty = new Bounty(0, new Resource(0, 0));
        this.timeRemaining = (int) GameLogicConfig.getFromDictionary("WarTime");
        initiateAllTroops();
    }

    private void initiateAllTroops() {
        for (String troopType : GameLogicConfig.TROOPS) {
            allTroops.put(troopType, new ArrayList<>());
        }
    }

    public void setNumberOfTroopsDeployed(int[][] numberOfTroopsDeployed) {
        this.numberOfTroopsDeployed = numberOfTroopsDeployed;
    }

    public int[][] getNumberOfTroopsDeployed() {
        return numberOfTroopsDeployed;
    }

    public void addTroops(String troopType, ArrayList<Troop> troops) {
        if(allTroops.keySet().contains(troopType)) {
            allTroops.get(troopType).addAll(troops);
        } else {
            allTroops.put(troopType, troops);
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
        for (Building building : enemyMap.getBuildings()) {
            if(!building.isDestroyed()) {
                availableResources.addToThisResource(building.getBounty().getResource());
            }
        }
        return availableResources;
    }

    public void reset() {
        thisLootedBounty = new Bounty(0, new Resource(0, 0));
        setNumberOfTroopsDeployed(new int[30][30]);

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

    public HashMap<String, ArrayList<Troop>> getAllTroops() {
        return allTroops;
    }

    public Map getEnemyMap() {
        return enemyMap;
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

    //private void heal(Troop troop){ }

    //private void isHealerAlive() { }
    //TODO check position correctness -> not occupied

    public void putTroop(String troopType, int count, Position position)
            throws CountLimitReachedException, InvalidPositionException, TroopNotFoundException {
        ArrayList<Troop> troops = allTroops.get(troopType);
        if(troops.size() < count) {
            throw new TroopNotFoundException();
        }
        if (!(position.getX() == 0 || position.getX() == enemyMap.getWidth() - 1 ||
                position.getY() == 0 || position.getY() == enemyMap.getHeight() - 1)) {
            throw new InvalidPositionException();
        }
        if (numberOfTroopsDeployed[position.getX()][position.getY()] + count > MAX_UNITS_IN_POSITION) {
            throw new CountLimitReachedException();
        }
        for (int i = 0; i < count; i++) {
            deployedTroops.add(troops.get(i));
            troops.get(i).setPosition(position);
            numberOfTroopsDeployed[position.getX()][position.getY()]+=count;
        }

    }

    public boolean isGameFinished() {
        if (timeRemaining <= 0) {
            return true;
        }
        boolean flag = false;
        for (String troopType : allTroops.keySet()) {
            if(allTroops.get(troopType).size() > 0) {
                flag = true;
                break;
            }
        }
        if(!flag) {
            return true;
        }
        if (!myVillage.isThereAvailableSpaceForResources()) {
            return true;
        }
        for (Defender defender : enemyMap.getBuildings()) {
            if(!defender.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    public void endBattle() {
        myVillage.spreadTroops(allTroops);
    }
}
