package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BattleGround {
    private Village myVillage;
    private ArrayList<Building> enemyBuildings;
    private Set<Troop> troops;
    private Bounty availableBounty; //fixme set this at constructor
    private int[][] numberOfTroopsDeployed;

    public BattleGround(Village myVillage, ArrayList<Building> enemyBuildings) {
        this.myVillage = myVillage;
        this.enemyBuildings = enemyBuildings;
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

    private void destroyBuilding(Building building) {

    }

    private void killTroop(Troop troop) {

    }

    private void move(Troop troop) {

    }

    public boolean putTroop(Troop troop, int x, int y) {

    }

    private void makeTroopsMove() {

    }

    private void makeDefenciveBuildingsMove() {

    }

    public boolean hasGameFinished() {

    }

    public void update() {

    }

}
