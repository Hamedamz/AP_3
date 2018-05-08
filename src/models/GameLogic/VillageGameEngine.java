package models.GameLogic;

import interfaces.Revivable;
import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Exceptions.BuildingNotFoundException;
import models.GameLogic.Exceptions.NotEnoughCapacityException;

import java.util.ArrayList;

public class VillageGameEngine {
    private Village village;

    public VillageGameEngine() {
    }

    public void loadVillage(Village village) {
        this.village = village;
    }

    public void update() {
        for (Revivable revivable : village.getBuildings()) {
            if (revivable.isDestroyed()) {
                revivable.revive();
            }
        }
        // FIXME: 4/26/2018 add village Giant to revivables

        updateTrainingTroops();

        for (Builder builder : village.getTownHall().getBuilders()) {
            builder.build();
        }
        ArrayList<Building> removeBuildings = new ArrayList<>();
        for (Building building : village.getUnderConstructBuildings()) {
            if (!building.isUnderConstruct()) {
                removeBuildings.add(building);
                village.addNewBuilding(building);
            }
        }
        village.getUnderConstructBuildings().removeAll(removeBuildings);

        Resource addedResource = new Resource(0, 0);
        for (Building building : village.findBuildingsWithSameType(ElixirMine.class)) {
            addedResource.addToThisResource(((Mine) building).produce());
        }

        for (Building building : village.findBuildingsWithSameType(GoldMine.class)) {
            addedResource.addToThisResource(((Mine) building).produce());
        }

        village.spreadResources(addedResource);

    }

    public void updateTrainingTroops() {
        for (Building building : village.findBuildingsWithSameType(Barracks.class)) {
            if(((Barracks) building).getTrainingTroops().size() > 0) {
                TrainingTroop trainingTroop = ((Barracks) building).getTrainingTroops().get(0);
                trainingTroop.update();
                if (trainingTroop.hasEndedTraining()) {
                    try {
                        trainingTroop.moveToCamp(village.findCampForNewTroops());
                        ((Barracks) building).getTrainingTroops().remove(trainingTroop);
                    } catch (NotEnoughCapacityException e) {
                        System.err.println("not enough capacity in camp"); // FIXME: 5/7/2018 no error massage needed after debug
                    } catch (BuildingNotFoundException e) {
                        System.err.println("You have no camps.");
                    }
                }
            }
        }
    }
}
