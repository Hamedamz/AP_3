package models.GameLogic;

import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.*;
import models.GameLogic.Exceptions.CountLimitReachedException;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;

public class BattleGround {
    private int timeRemaining;
    private Village myVillage;
    private Map enemyMap;
    private ArrayList<Building> enemyBuildings;
    private ArrayList<Troop> troops;
    private Bounty thisLootedBounty; //fixme set this at constructor
    private Bounty lootedBounty;
    private int[][] numberOfTroopsDeployed = new int[30][30];

    public BattleGround(Village myVillage, Map enemyMap) {
        troops = new ArrayList<>();
        enemyBuildings = new ArrayList<>(enemyMap.getBuildings());
        this.myVillage = myVillage;
        this.enemyMap = enemyMap;
        lootedBounty = new Bounty(0, new Resource(0, 0));
        thisLootedBounty = new Bounty(0, new Resource(0, 0));
        this.timeRemaining = (int) GameLogicConfig.getFromDictionary("WarTime");
    }

    public void setNumberOfTroopsDeployed(int[][] numberOfTroopsDeployed) {
        this.numberOfTroopsDeployed = numberOfTroopsDeployed;
    }

    public int[][] getNumberOfTroopsDeployed() {
        return numberOfTroopsDeployed;
    }

    public void addTroops(ArrayList<Troop> troops) {
        this.troops.addAll(troops);
    }

    public void addBounty(Bounty bounty) {
        this.thisLootedBounty.addToThisBounty(bounty);
        this.lootedBounty.addToThisBounty(bounty);
        myVillage.addBounty(bounty);
    }

    public Resource getLootedResources() {
        return lootedBounty.getResource();
    }

    public Resource getRemainingResources() {
        Resource availableResources = new Resource(0, 0);
        for (Building building : enemyMap.getBuildings()) {
            if(!building.isDestroyed()) {
                availableResources.addToThisResource(lootedBounty.getResource());
            }
        }
        return availableResources;
    }

    public void reset() {
        thisLootedBounty = new Bounty(0, new Resource(0, 0));
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

    public  ArrayList<Entity> getAttackerEntitiesInPosition(Position pos) {
        ArrayList<Entity> result = new ArrayList<>();
        for (Troop troop : troops) {
            if (troop.getPosition().equals(pos)) {
                result.add(troop);
            }
        }
        return result;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

//    public Map getEnemyMap() {
//        return enemyMap;
//    }
//

    public ArrayList<Building> getEnemyBuildings() {
        return enemyBuildings;
    }

    public ArrayList<Defender> getEnemyDefenders(){
        return new ArrayList<>(enemyBuildings);
    }

    public Village getVillage() {
        return myVillage;
    }

    private Entity findTroopLocationTarget(Troop troop, Building targetedBuilding) {
        return null;
        // TODO: 5/5/2018
    }

    //private void heal(Troop troop){ }

    //private void isHealerAlive() { }
    //TODO check position correctness -> not occupied

    public void putTroop(Troop troop, Position position) throws CountLimitReachedException {
        if (numberOfTroopsDeployed[position.getX()][position.getY()] >= 5) {
            throw new CountLimitReachedException();
        }
        troop.setPosition(position);
        numberOfTroopsDeployed[position.getX()][position.getY()]++;
    }
    private void move(Troop troop) {

    }

    public boolean isGameFinished() {
        if (timeRemaining <= 0) {
            return true;
        }
        if (troops.size() == 0) {
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
        myVillage.spreadTroops(troops);
    }
}
