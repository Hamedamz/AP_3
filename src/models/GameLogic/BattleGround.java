package models.GameLogic;

import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.*;

import java.util.ArrayList;
import java.util.Set;

public class BattleGround {
    private Village myVillage;
    private ArrayList<Building> enemyBuildings;
    private Set<Troop> troops;
    private Bounty availableBounty; //fixme set this at constructor
    private int[][] numberOfTroopsDeployed;

    public BattleGround(Village myVillage, ArrayList<Building> enemyBuildings, Set<Troop> troops) {
        this.myVillage = myVillage;
        this.enemyBuildings = enemyBuildings;
        this.troops = troops;
        for (int i = 0; i < enemyBuildings.size(); i++) {
            if (enemyBuildings.get(i).getClass().getName().equals("GoldStorage") || )
        }

    }

    private void attackDefensiveBuilding(Troop troop, Building building) {
    }

    private void attackUnit(DefensiveBuilding defensiveBuilding, Troop troop) {
    }

    private Building findTroopTarget(Troop troop) {
        return null;
    }

    private Entity findTroopLocationTarget(Troop troop, Building targetedBuilding) {
    }

    private Troop findBuildingTarget(Building building) {
        return null;
    }

    //private void heal(Troop troop){ }

    //private void isHealerAlive() { }


    public boolean putTroop(Troop troop, int x, int y) {

    }

    private void destroyBuilding(Building building) {

    }

    private void killTroop(Troop troop) {

    }

    private void move(Troop troop) {

    }

    private void makeTroopsMove() {

    }

    private void makeDefensiveBuildingsMove() {

    }

    public boolean isAttackFinished() {

    }

}
