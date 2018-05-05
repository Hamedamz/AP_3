package models.GameLogic;

import interfaces.Destroyable;
import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.*;
import models.GameLogic.Exceptions.CountLimitReachedException;
import models.GameLogic.enums.MoveType;

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
        for (int i = 0; i < enemyBuildings.size(); i++) {
            if (enemyBuildings.get(i).getClass().getName().equals("GoldStorage")) {
            }
        }
    }

    public Set<Troop> getTroops() {
        return troops;
    }

    public Map getMap() {
        return map;
    }


    private Entity findTroopLocationTarget(Troop troop, Building targetedBuilding) {
        return null;
        // TODO: 5/5/2018  
    }

    // FIXME: 5/5/2018 move to battle ground
    public Troop findBuildingTarget(Building building) {
        int x = building.getPosition().getX();
        int y = building.getPosition().getY();
        if (building instanceof DefensiveBuilding) {
            for (int i = -((DefensiveBuilding) building).getRange(); i <((DefensiveBuilding) building).getRange(); i++) {
                x += i;
                for (int j = -((DefensiveBuilding) building).getRange(); j <= ((DefensiveBuilding) building).getRange(); j++) {
                    y += j;
                    if (x >= 30)
                        x = 29;
                    if (y >= 30)
                        y = 29;
                    if (x < 0)
                        x = 0;
                    if (y < 0)
                        y = 0;
                    if (building.getPosition().calculateDistance(new Position(x, y)) > ((DefensiveBuilding) building).getRange()) {
                        continue;
                    }
                    for (Iterator<Troop> it = troops.iterator(); it.hasNext(); ) {
                        Troop troop = it.next();
                        if (troop.getPosition().getX() == x && troop.getPosition().getY() == y)
                            return troop;
                    }
                }
            }
        }
        return null;
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

    private void makeTroopsMove() {

    }

    private void makeDefensiveBuildingsMove() {

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
