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
    private HashMap<String, ArrayList<Troop>> unDeployedTroops;
    private Bounty thisLootedBounty;
    private Bounty lootedBounty;
    private int[][] numberOfTroopsDeployed = new int[30][30];

    public BattleGround(Village myVillage, Map enemyMap) {
        deployedTroops = new ArrayList<>();
        enemyBuildings = new ArrayList<>(enemyMap.getBuildings());
        unDeployedTroops = new HashMap<>();
        this.myVillage = myVillage;
        this.enemyMap = enemyMap;
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

    public HashMap<String, ArrayList<Troop>> getUnDeployedTroops() {
        return unDeployedTroops;
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
        ArrayList<Troop> troops = unDeployedTroops.get(troopType);
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
            Troop deployedTroop = troops.get(troops.size() - 1);
            deployedTroops.add(deployedTroop);
            troops.remove(troops.size()-1);
            deployedTroop.setPosition(position);
        }
        numberOfTroopsDeployed[position.getX()][position.getY()]+=count;

    }

    public boolean isGameFinished() {
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
        for (int i = 0; i < deployedTroops.size(); i++) {
            if (unDeployedTroops.containsKey(deployedTroops.get(i).getClass().getSimpleName())) {
                unDeployedTroops.get(deployedTroops.get(i).getClass().getSimpleName()).add(deployedTroops.get(i));
            }
            else {
                ArrayList<Troop> troops = new ArrayList<>();
                troops.add(deployedTroops.get(i));
                unDeployedTroops.put(deployedTroops.get(i).getClass().getSimpleName(), troops);
            }
        }
        myVillage.spreadTroops(unDeployedTroops);
    }
}
