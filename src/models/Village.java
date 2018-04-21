package models;

import models.Exceptions.*;

import java.util.*;

public class Village {
    private ArrayList<Building> buildings;
    private Map map;
    private int score;

    public Village() {
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public Map getMap() {
        return map;
    }

    public int getScore() {
        return score;
    }

    public boolean addResources() {
        //it's false when we don't have enough capacity
    }

    public Building getBuildingByNumber(String buildingType, int buildingNumber) {

    }

    public void build(String buildingType, int x, int y)
            throws NoFreeBuilder, NotEnoughCapacity, NotEnoughResources {

    }

    public void upgrade(String buildingType, int num) throws NotEnoughResources, BuildingNotFound {

    }

    public ArrayList<Building> findBuildingsWithSameType(String buildingType) {

    }

    public ArrayList<Troop> getTroops() {

    }

    public void trainTroop(String troopType, int barracksNum)
            throws NotEnoughResources, NotAvailableAtThisLevel, NotEnoughCapacity {

    }

    private void MoveTrainedTroop(int barracksNum, TrainingTroop trainingTroop) {

    }

    public void removeTrainingTroop(String troopType, int barracksNum) {

    }

    private void removeTroop(String troopType, int campNum) {

    }

    public boolean haveWeSpace(String resourceType, int amount) {

    }

    public void addBounty(Bounty bounty) {

    }

    public void passTurn() {

    }
}
