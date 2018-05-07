package models.GameLogic;

import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.*;
import models.Setting.GameLogicConfig;

import java.util.*;

public class Village {
    private TownHall townHall;
    private Map map;
    /**
     * listOfBuildingsByName must contain following Classes other than Basic Classes
     * Storage, DefenciveBuildings
     */
    private HashMap<String, ArrayList<Building>> listOfBuildingsByName = new HashMap<>();
    // FIXME: 4/28/2018 when you build a building become sure you put that building in correct catagory at above class

    public Village() {
    }

    public static Village startNewVillage() {
        Village village = new Village();
        village.map = new Map((int) GameLogicConfig.getFromDictionary("VillageWidth"),
                (int) GameLogicConfig.getFromDictionary("VillageHeight"));
        village.townHall = new TownHall(new Position((village.map.getWidth() - 1) / 2, (village.map.getHeight() - 1) / 2), true);
        GoldStorage goldStorage = new GoldStorage(new Position((village.map.getWidth() - 3) / 2, (village.map.getHeight() - 3) / 2), true);
        village.map.addNewBuilding(village.townHall);
        village.map.addNewBuilding(goldStorage);
        village.initiateListOfBuildings();
        village.listOfBuildingsByName.get(TownHall.class.getSimpleName()).add(village.townHall);
        village.listOfBuildingsByName.get(Storage.class.getSimpleName()).add(village.townHall);
        village.listOfBuildingsByName.get(Storage.class.getSimpleName()).add(goldStorage);
        village.spreadResources(new Resource((int) GameLogicConfig.getFromDictionary("VillageInitialGold"), (int) GameLogicConfig.getFromDictionary("VillageInitialElixir")));
        return village;
    }

    private void initiateListOfBuildings() {
        listOfBuildingsByName.put(TownHall.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(Storage.class.getSimpleName(), new ArrayList<>());

        listOfBuildingsByName.put(ElixirStorage.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(GoldMine.class.getSimpleName(), new ArrayList<>());

        listOfBuildingsByName.put(Camp.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(Camp.class.getSimpleName(), new ArrayList<>());

        listOfBuildingsByName.put(DefensiveBuilding.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(Cannon.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(WizardTower.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(ArcherTower.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(AirDefense.class.getSimpleName(), new ArrayList<>());

    }

    public TownHall getTownHall() {
        return townHall;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Building> getBuildings() {
        return map.getBuildings();
    }


    public Building getBuildingByNumber(String buildingType, int buildingNumber) {
        return null;
        // TODO: 5/5/2018  
    }

    public void build(String buildingType, int x, int y)
            throws NoFreeBuilderException, NotEnoughCapacityException, NotEnoughResourcesException, NotAvailableAtThisLevelException {
        if (map.isOccupied(x, y)) {
            throw new NotEnoughCapacityException();
        }
        Builder builder = townHall.getFreeBuilder();
        spendResources(new Resource((int) GameLogicConfig.getFromDictionary(buildingType + "BuildGold"),
                (int) GameLogicConfig.getFromDictionary(buildingType + "BuildElixir")));
        map.constructBuilding(x, y);
        // TODO: 5/3/2018 make new building here


    }

    private <T extends Building> T startConstruction(String buildingType, int x, int y) throws CountLimitReachedException {
//        switch (buildingType) {
//            case "AirDefence":
//                return new AirDefense(x, y);
//                break;
//            case "ArcherTower" :
//                return new ArcherTower(x, y);
//                break;
//            case "Barracks" :
//                return new Barracks(x, y);
//                break;
//            case "Camp" :
//                return new Camp(x, y);
//                break;
//            case "Cannon" :
//                return new Cannon(x, y);
//                break;
//            case "ElixirMine" :
//                return new ElixirMine(x, y);
//                break;
//            case "ElixirStorage" :
//                return new ElixirStorage(x, y);
//                break;
//            case "GoldMine" :
//                return new GoldMine(x, y);
//                break;
//            case "GoldStorage" :
//                return new GoldStorage(x, y);
//                break;
//            case "TownHall" :
//                throw new CountLimitReachedException();
//                break;
//            case "WizardTower" :
//                return new WizardTower(x, y);
//                break;
//            default:
//                break;
//        }
        return null;
        // TODO: 5/5/2018  
    }

    public void upgrade(String buildingType, int num) throws NotEnoughResourcesException, BuildingNotFoundException, NotAvailableAtThisLevelException {
        ArrayList<Building> buildings = map.getBuildings();
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getClass().getSimpleName().equals(buildingType) && buildings.get(i).getID().getCount() == num) {
                if (buildings.get(i).getLevel() >= townHall.getLevel()) {
                    throw new NotAvailableAtThisLevelException();
                }
                Resource upgradeResource = buildings.get(i).getUpgradeResource();
                if (upgradeResource.getGold() > getTotalResourceStock().getGold() || upgradeResource.getElixir() > getTotalResourceStock().getElixir()) {
                    throw new NotEnoughResourcesException();
                }
                spendResources(upgradeResource);
                buildings.get(i).upgrade();
                return;
            }
        }
        throw new BuildingNotFoundException();

    }

    public <T extends Building> ArrayList<T> findBuildingsWithSameType(Class<T> type) {
        ArrayList<T> res = new ArrayList<>();
        for (Building building : listOfBuildingsByName.get(type.getSimpleName())) {
            res.add((T) building);
        }
        return res;
    }

    public ArrayList<Troop> getTroops() {
        return null;
    }

    public void trainTroop(String troopType, int count, Barracks barracks) //bad smell! it must be in barracks
            throws NotEnoughResourcesException, NotAvailableAtThisLevelException {
        if (barracks.getMaxAvailableTrainingTroop(troopType, getTotalResourceStock()) < count) {
            throw new NotEnoughResourcesException();
        }

        Resource trainCost = new Resource(0, 0);
        for (int i = 0; i < count; i++) {
            barracks.trainNewTroop(troopType);
            trainCost.addToThisResource(TrainingTroop.getTrainingResources(troopType));
        }
        spendResources(trainCost);
    }

    public Camp findCampForNewTroops() {
        ArrayList<Camp> camps = findBuildingsWithSameType(Camp.class);
        camps.sort(new Camp.CampComparator());
        return camps.get(0);
    }

    public void removeTrainingTroop(String troopType, int barracksNum) {

    }

    private void removeTroop(String troopType, int campNum) {

    }

    public boolean isThereAvailableSpaceForResources() {

        for (Building building : findBuildingsWithSameType(Storage.class)) {
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

    public void spendResources(Resource resource) throws NotEnoughResourcesException {
        Resource totalResource = Resource.subtractResources(getTotalResourceStock(), resource);
        if (totalResource.getGold() < 0 || totalResource.getElixir() < 0) {
            throw new NotEnoughResourcesException();
        }
        spreadResources(new Resource(-resource.getGold(), -resource.getElixir()));
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
            int addedGold = 0;
            try {
                addedGold = Math.min(storageNumberI.getCapacity().getGold(), gold / (storagesCount - i - 1));
            } catch (Exception e) {
                e.getMessage();
            }
            gold -= addedGold;
            storageNumberI.setGold(addedGold);
        }
        //CP
        storages.sort(new Storage.ElixirStorageComparator());
        for (int i = 0; i < storagesCount; i++) {
            Storage storageNumberI = storages.get(i);
            int addedElixir = 0;
            try {
                addedElixir = Math.min(storageNumberI.getCapacity().getElixir(), elixir / (storagesCount - i - 1));
            } catch (Exception e) {
                e.getMessage();
            }
            elixir -= addedElixir;
            storageNumberI.setElixir(addedElixir);
        }
    }

    public void addBounty(Bounty bounty) {
        spreadResources(bounty.getResource());
        townHall.addScore(bounty.getScore());
    }

}

