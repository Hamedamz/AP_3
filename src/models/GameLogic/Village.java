package models.GameLogic;

import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.*;
import models.GameLogic.utills.IDGenerator;
import models.setting.GameLogicConfig;

import java.util.*;

public class Village {
    private TownHall townHall;
    private GameMap gameMap;
    private ArrayList<Building> underConstructBuildings;

    /**
     * listOfBuildingsByName must contain following Classes other than Basic Classes
     * Storage, DefenciveBuildings
     */
    private transient HashMap<String, ArrayList<Building>> listOfBuildingsByName;
    // FIXME: 4/28/2018 when you build a building become sure you put that building in correct catagory at above class

    public Village() {
        underConstructBuildings = new ArrayList<>();
        listOfBuildingsByName = new HashMap<>();
        IDGenerator.resetIDGenerator();
    }

    public static Village startNewVillage() {
        Village village = new Village();
        village.gameMap = new GameMap((int) GameLogicConfig.getFromDictionary("VillageWidth"),
                (int) GameLogicConfig.getFromDictionary("VillageHeight"));
        village.townHall = new TownHall(Position.newMapPosition((village.gameMap.getMapWidth() - 1) / 2, (village.gameMap.getMapHeight() - 1) / 2), true);
        GoldStorage goldStorage = new GoldStorage(Position.newMapPosition((village.gameMap.getMapWidth() - 3) / 2, (village.gameMap.getMapHeight() - 3) / 2), true);
        village.townHall.finishConstruct();
        goldStorage.finishConstruct();
        village.gameMap.addNewBuilding(village.townHall);
        village.gameMap.addNewBuilding(goldStorage);
        village.initiateListOfBuildings();
        village.listOfBuildingsByName.get(TownHall.class.getSimpleName()).add(village.townHall);
        village.listOfBuildingsByName.get(Storage.class.getSimpleName()).add(village.townHall);
        village.listOfBuildingsByName.get(Storage.class.getSimpleName()).add(goldStorage);
        village.spreadResources(new Resource((int) GameLogicConfig.getFromDictionary("VillageInitialGold"), (int) GameLogicConfig.getFromDictionary("VillageInitialElixir")));
        return village;
    }

    private void initiateListOfBuildings() {
        //building switch case
        listOfBuildingsByName.put(TownHall.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(Storage.class.getSimpleName(), new ArrayList<>());

        listOfBuildingsByName.put(GoldStorage.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(ElixirStorage.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(GoldMine.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(ElixirMine.class.getSimpleName(), new ArrayList<>());

        listOfBuildingsByName.put(Camp.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(Barracks.class.getSimpleName(), new ArrayList<>());

        listOfBuildingsByName.put(DefensiveBuilding.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(Cannon.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(WizardTower.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(ArcherTower.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(AirDefense.class.getSimpleName(), new ArrayList<>());
        listOfBuildingsByName.put(Trap.class.getSimpleName(), new ArrayList<>());

        listOfBuildingsByName.put(Wall.class.getSimpleName(), new ArrayList<>());

        listOfBuildingsByName.put(GuardianGiant.class.getSimpleName(), new ArrayList<>());
    }

    public TownHall getTownHall() {
        return townHall;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    //building managing

    public ArrayList<Building> getUnderConstructBuildings() {
        return underConstructBuildings;
    }

    public ArrayList<Building> getBuildings() {
        return gameMap.getBuildings();
    }

    public int getBuildingCount(String buildingType) {
        int count = 0;
        for (Building building : gameMap.getBuildings()) {
            if (building.getClass().getSimpleName().equals(buildingType)) {
                count++;
            }
        }
        for (Building building : underConstructBuildings) {
            if (building.getClass().getSimpleName().equals(buildingType)) {
                count++;
            }
        }
        return count;
    }

    public Building getBuildingByNumber(String buildingType, int buildingNumber) {
        return null;
        // TODO: 5/5/2018  
    }

    public void build(String buildingType, int x, int y)
            throws NoFreeBuilderException, InvalidPositionException, NotEnoughResourcesException, CountLimitReachedException {
        if (GameLogicConfig.getFromDictionary(buildingType + "BuildLimit") <= getBuildingCount(buildingType) ) {
            throw new CountLimitReachedException();
        }

        if (Position.newMapPosition(x, y).isInMapBoundary(gameMap) && gameMap.isOccupied(x, y)) {
            throw new InvalidPositionException();
        }
        Builder builder = townHall.getFreeBuilder();

        spendResources(new Resource((int) GameLogicConfig.getFromDictionary(buildingType + "BuildGold"),
                (int) GameLogicConfig.getFromDictionary(buildingType + "BuildElixir")));
        gameMap.constructBuilding(x, y);
        Building newBuilding = Building.getNewBuilding(buildingType, x, y);
        builder.startBuilding(newBuilding);
        underConstructBuildings.add(newBuilding);
    }

    public void addNewBuilding(Building building) {
        //building switch case
        if (building instanceof DefensiveBuilding) {
            listOfBuildingsByName.get(DefensiveBuilding.class.getSimpleName()).add(building);
        }
        if (building instanceof Barracks) {
            listOfBuildingsByName.get(Barracks.class.getSimpleName()).add(building);
        }
        if (building instanceof Camp) {
            listOfBuildingsByName.get(Camp.class.getSimpleName()).add(building);
        }
        if (building instanceof TownHall) {
            listOfBuildingsByName.get(TownHall.class.getSimpleName()).add(building);
        }
        if (building instanceof Storage) {
            listOfBuildingsByName.get(Storage.class.getSimpleName()).add(building);
        }
        if (building instanceof GoldStorage) {
            listOfBuildingsByName.get(GoldStorage.class.getSimpleName()).add(building);
        }
        if (building instanceof ElixirStorage) {
            listOfBuildingsByName.get(ElixirStorage.class.getSimpleName()).add(building);
        }
        if (building instanceof GoldMine) {
            listOfBuildingsByName.get(GoldMine.class.getSimpleName()).add(building);
        }
        if (building instanceof ElixirMine) {
            listOfBuildingsByName.get(ElixirMine.class.getSimpleName()).add(building);
        }
        if (building instanceof Cannon) {
            listOfBuildingsByName.get(Cannon.class.getSimpleName()).add(building);
        }
        if (building instanceof ArcherTower) {
            listOfBuildingsByName.get(ArcherTower.class.getSimpleName()).add(building);
        }
        if (building instanceof AirDefense) {
            listOfBuildingsByName.get(AirDefense.class.getSimpleName()).add(building);
        }
        if (building instanceof WizardTower) {
            listOfBuildingsByName.get(WizardTower.class.getSimpleName()).add(building);
        }
        if(building instanceof Wall) {
            listOfBuildingsByName.get(Wall.class.getSimpleName()).add(building);
        }
        if(building instanceof Trap) {
            listOfBuildingsByName.get(Trap.class.getSimpleName()).add(building);
        }
        if(building instanceof GuardianGiant) {
            listOfBuildingsByName.get(GuardianGiant.class.getSimpleName()).add(building);
        }


        gameMap.getBuildings().add(building);
    }

    public void upgrade(String buildingType, int num) throws NotEnoughResourcesException, BuildingNotFoundException, NotAvailableAtThisLevelException, UpgradeLimitReachedException {
        ArrayList<Building> buildings = gameMap.getBuildings();
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getClass().getSimpleName().equals(buildingType) && buildings.get(i).getID().getCount() == num) {
                if (buildings.get(i).getLevel() >= townHall.getLevel()) {
                    throw new NotAvailableAtThisLevelException();
                }
                Resource upgradeResource = buildings.get(i).getUpgradeResource();
                if (upgradeResource.getGold() > getTotalResourceStock().getGold() || upgradeResource.getElixir() > getTotalResourceStock().getElixir()) {
                    throw new NotEnoughResourcesException();
                }
                buildings.get(i).upgrade();
                spendResources(upgradeResource);
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

    //Troop managing:

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

    public Camp findCampForNewTroops() throws BuildingNotFoundException {
        ArrayList<Camp> camps = findBuildingsWithSameType(Camp.class);
        if (camps.isEmpty()) {
            throw new BuildingNotFoundException();
        }
        camps.sort(new Camp.CampComparator());
        return camps.get(0);
    }

    public ArrayList<Troop> sendTroopToBattleGround(String troopType, int count) throws TroopNotFoundException{
        ArrayList<Troop> result = new ArrayList<>();
        HashMap<Troop, Camp> allTroops = new HashMap<>();
        try {
            outer:
            for(Camp camp : findBuildingsWithSameType(Camp.class)) {
                for (Troop troop : camp.getTroops()) {
                    if (troop.getClass().getSimpleName().equals(troopType)) {
                        allTroops.put(troop, camp);
                        count --;
                        if(count == 0) {
                            break outer;
                        }

                    }
                }
            }
            if (count != 0) {
                throw new TroopNotFoundException();
            }
        } catch (NullPointerException e) {
            throw new TroopNotFoundException();
        }

        for (Troop troop : allTroops.keySet()) {
            allTroops.get(troop).getTroops().remove(troop);
            result.add(troop);
        }

        return result;
    }

    public void removeTrainingTroop(String troopType, int barracksNum) {
    }

    private void removeTroop(String troopType, int campNum) {
    }

    //resource Managing
    public boolean isThereAvailableSpaceForResources() {

        for (Building building : findBuildingsWithSameType(Storage.class)) {
            if (!((Storage) building).isStorageFull()) {
                return true;
            }
        }
        return false;
    }

    public int getTotalCampTroops() {
        int result = 0;
        for (Camp camp : findBuildingsWithSameType(Camp.class)) {
            result += camp.getTroops().size();
        }
        return result;
    }

    public int getTotalCampCapacity() {
        int result = 0;
        for (Camp camp : findBuildingsWithSameType(Camp.class)) {
            result += camp.getSize();
        }
        return result;
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
                addedGold = Math.min(storageNumberI.getCapacity().getGold(), gold / (storagesCount - i));
            } catch (ArithmeticException e) {
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
                addedElixir = Math.min(storageNumberI.getCapacity().getElixir(), elixir / (storagesCount - i));
            } catch (ArithmeticException e) {

            }
            elixir -= addedElixir;
            storageNumberI.setElixir(addedElixir);
        }
    }

    public void addBounty(Bounty bounty) {
        spreadResources(bounty.getResource());
        townHall.addScore(bounty.getScore());
    }

    public void spreadTroops(HashMap<String ,ArrayList<Troop>> allTroops) {
        ArrayList<Camp> camps = findBuildingsWithSameType(Camp.class);
        int campCounter = 0;
        for(String troopType: allTroops.keySet()) {
            ArrayList<Troop> troops = allTroops.get(troopType);
            for (Troop troop : troops) {
                try {
                    findCampForNewTroops().addTroop(troop);
                } catch (NotEnoughCapacityException e) {
                    System.err.println("there is no place for returning troops");
                    break;
                } catch (BuildingNotFoundException e) {
                }
            }
        }


    }
}

