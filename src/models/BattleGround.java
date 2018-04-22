package models;

import java.util.ArrayList;
import java.util.Map;

public class BattleGround {
    private Village myVillage;
    private Village enemyVillage;
    private Map<Troop ,Integer> troops;
    private ArrayList<Troop> killedTroops;
    private Bounty bounty;
    private int[][] numberOfTroopsDeployed;

    public BattleGround(Village myVillage, Village enemyVillage) {
        this.myVillage = myVillage;
        this.enemyVillage = enemyVillage;
    }

    private void attackDefensiveBuilding(Troop troop, Building building) {
    }

    private void attackUnit(DefensiveBuilding defensiveBuilding, Troop troop) {
    }

    private Building findTroopTarget(Troop troop) {
        return null;
    }

    private Entity findTroopLocationTarget(Troop troop, Building targetedBuilding)

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
