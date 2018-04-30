package models.GameLogic;

import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.*;

import java.util.*;

public class Village {
    private TownHall townHall;
    private Map map;
    /**
     * listOfBuildingsByName must contain following Classes other than Basic Classes
     * Storage, DefenciveBuildings
     */
    private HashMap< String, ArrayList<Building> > listOfBuildingsByName = new HashMap<>();
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
        for (Building building : listOfBuildingsByName.get(type.getName())) {
            res.add((T) building);
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

    public boolean isThereAvailableSpaceForResources() {

        for(Building building : findBuildingsWithSameType(Storage.class)) {
            if (!((Storage) building).isStorageFull()) {
                return true;
            }
        }
        return false;
    }

    public Resource getTotalResourceStock() {
        Resource result = new Resource(0, 0);
        for (Storage storage : findBuildingsWithSameType(Storage.class)) {
            result.addToThisResource(storage.getStock());
        }
        return result;
    }

    public Resource getTotalResourceCapacity() {
        Resource result = new Resource(0, 0);
        for (Storage storage : findBuildingsWithSameType(Storage.class)) {
            result.addToThisResource(storage.getCapacity());
        }
        return result;
    }

    public void spreadResources() {
        spreadResources(new Resource(0, 0));
    }

    public void spreadResources(Resource resource) {
        Resource totalResource = Resource.addResources(getTotalResourceStock(), resource, getTotalResourceCapacity());
        int gold = totalResource.getGold();
        int elixir = totalResource.getElixir();
        ArrayList<Storage> storages = findBuildingsWithSameType(Storage.class);
        storages.sort(new Storage.GoldStorageComparator());
        int storagesCount = storages.size();
        for (int i = 0; i < storagesCount; i++) {
            Storage storageNumberI = storages.get(i);
            int addedGold = Math.min(storageNumberI.getCapacity().getGold(), gold / (storagesCount - i - 1));
            gold -= addedGold;
            storageNumberI.setGold(addedGold);
        }
        //CP
        storages.sort(new Storage.ElixirStorageComparator());
        for (int i = 0; i < storagesCount; i++) {
            Storage storageNumberI = storages.get(i);
            int addedElixir = Math.min(storageNumberI.getCapacity().getElixir(), elixir / (storagesCount - i - 1));
            elixir -= addedElixir;
            storageNumberI.setElixir(addedElixir);
        }
    }

    public void addBounty(Bounty bounty) {
        spreadResources(bounty.getResource());
        townHall.addScore(bounty.getScore());
    }

}

