package models;

import java.util.ArrayList;

public class BattleGround {
    private Map map;
    private ArrayList<Troop> troops;
    private Bounty bounty;

    private void attackDefensiveBuilding(Troop troop, Building building) {
    }

    private void attackUnit(DefensiveBuilding defensiveBuilding, Troop troop) {
    }

    private Building findTroopTarget(Troop troop) {
        return null;
    }

    private Troop findBuildingTarget(Building building) {
        return null;
    }

    private void heal(Troop troop){

    }

    private void isHealerAlive() {

    }

    private void destroyBuilding(Building building) {
        //FIXME this will not be usable if resources reduced linearly
    }

    private void killUnit(Building building) {

    }

    private void move(Troop troop) {

    }


    public void passTurn() {

    }

    public void passNTurns(int n) {

    }
}
