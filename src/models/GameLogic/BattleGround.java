package models.GameLogic;

import interfaces.Destroyable;
import interfaces.Locatable;
import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.*;
import models.GameLogic.Exceptions.CountLimitReachedException;
import models.GameLogic.enums.MoveType;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class BattleGround {
    private int timeRemaining;
    private Village myVillage;
    private Map map;
    private Set<Troop> troops;
    private Bounty availableBounty; //fixme set this at constructor

    public void setNumberOfTroopsDeployed(int[][] numberOfTroopsDeployed) {
        this.numberOfTroopsDeployed = numberOfTroopsDeployed;
    }

    public int[][] getNumberOfTroopsDeployed() {
        return numberOfTroopsDeployed;
    }

    private int[][] numberOfTroopsDeployed = new int[30][30];

    public BattleGround(Village myVillage, ArrayList<Building> enemyBuildings, Set<Troop> troops) {
        this.myVillage = myVillage;
        this.troops = troops;
        availableBounty = new Bounty(0, new Resource(0, 0));
        this.timeRemaining = (int) GameLogicConfig.getFromDictionary("WarTime");
    }

    public void addBounty(Bounty bounty) {
        this.availableBounty.addToThisBounty(bounty);
        myVillage.addBounty(bounty);
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

    public Set<Troop> getTroops() {
        return troops;
    }

    public Map getMap() {
        return map;
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
        for (Defender defender : map.getBuildings()) {
            if(!defender.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

}
