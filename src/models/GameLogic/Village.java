package models.GameLogic;

import models.GameLogic.Exceptions.*;
import sun.awt.geom.AreaOp;

import java.util.*;

public class Village {
    private TownHall townHall;
    private Map map;
    private int score;

    public Village() {
    }

    public TownHall getTownHall() {
        return townHall;
    }

    public Map getMap() {
        return map;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Building> getBuildings(){
        return map.getBuildings();
    }

    public boolean haveWeSpaceForResources() {

    }

    public void addResources() {

    }

    public Building getBuildingByNumber(String buildingType, int buildingNumber) {

    }

    public void build(String buildingType, int x, int y)
            throws NoFreeBuilderException, NotEnoughCapacityException, NotEnoughResourcesException {

    }

    public void upgrade(String buildingType, int num) throws NotEnoughResourcesException, BuildingNotFoundException {

    }

    public ArrayList<Building> findBuildingsWithSameType(String buildingType) {

    }

    public ArrayList<Troop> getTroops() {

    }

    public void trainTroop(String troopType, int barracksNum)
            throws NotEnoughResourcesException, NotAvailableAtThisLevelException, NotEnoughCapacityException {

    }

    public Camp findCampForNewTroops(int space) {
        ArrayList<Building> camps = findBuildingsWithSameType("Camp");
        for(Building building :  camps) {
            Camp camp = (Camp) building;
            //fixme
        }
    }

    public void removeTrainingTroop(String troopType, int barracksNum) {

    }

    private void removeTroop(String troopType, int campNum) {

    }

    public boolean haveWeSpace(String resourceType, int amount) {

    }

    public void addBounty(Bounty bounty) {

    }

}
