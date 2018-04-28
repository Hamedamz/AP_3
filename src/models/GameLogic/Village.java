package models.GameLogic;

import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.*;

import java.util.*;

public class Village {
    private TownHall townHall;
    private Map map;
    private HashMap<String, ArrayList<Entity>> listOfBuildingsByName = new HashMap<>();
    // FIXME: 4/28/2018 when you build a building become sure you put that building in correct catagory at above class

    public Village() {
    }

    public TownHall getTownHall() {
        return townHall;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Building> getBuildings(){
        return map.getBuildings();
    }



    public Building getBuildingByNumber(String buildingType, int buildingNumber) {

    }

    public void build(String buildingType, int x, int y)
            throws NoFreeBuilderException, NotEnoughCapacityException, NotEnoughResourcesException {

    }

    public void upgrade(String buildingType, int num) throws NotEnoughResourcesException, BuildingNotFoundException {

    }

    public <T extends Building> ArrayList<T> findBuildingsWithSameType(Class<T> type) {

        ArrayList<T> res = new ArrayList<>();
        for (Building building : map.getBuildings()) {
//            if(building.getClass().getName().equals(nameOfClass))
                if (type.isInstance(building))
                    res.add((T)building);
        }

        return res;
    }

    public ArrayList<Troop> getTroops() {
        return null;
    }

    public void trainTroop(String troopType, int barracksNum)
            throws NotEnoughResourcesException, NotAvailableAtThisLevelException, NotEnoughCapacityException {

    }

    public Camp findCampForNewTroops(int space) {
        ArrayList<Camp> camps = findBuildingsWithSameType(Camp.class);
        for(Building building :  camps) {
            Camp camp = (Camp) building;
            //fixme
        }
    }

    public void removeTrainingTroop(String troopType, int barracksNum) {

    }

    private void removeTroop(String troopType, int campNum) {

    }

    public boolean haveWeSpaceForResources() {

        for(Building building : findBuildingsWithSameType("Storage")) {
            if (!((Storage) building).isStorageFull()) {
                return true;
            }
        }
        return false;
    }

    public void addResources(Resource resource) {

    }

    public void addBounty(Bounty bounty) {
        addResources(bounty.getResource());
        townHall.addScore(bounty.getScore());
    }

}
